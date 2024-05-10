package org.example.multiplayer;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.HashMap;

import org.example.Wall;
import org.example.packets.admin.StartGamePacket;
import org.example.packets.client.EnemyPacket;
import org.example.packets.client.GamePacket;
import org.example.packets.client.PlayerPacket;
import org.example.packets.client.WallPacket;

public class MPConnection {
	
    private final int rgb;
    private final Socket socket;
    private final HashMap<Integer, PlayerPacket> players = new HashMap<>();
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;
    private final PlayerPacket selfPlayer;
    private final MPPlayer player;
    private final MPGame game;

    public static MPConnection newConnection(String host, int port, int rgb, MPGame game){
        try{
            return new MPConnection(host, port, rgb, game);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            e.printStackTrace();
            return null;
        }
    }

    public MPConnection(String host, int port, int rgb, MPGame game) throws IOException{
        this.rgb = rgb;
        this.game = game;
        try{
            this.socket = new Socket(host, port);
            this.objIn = new ObjectInputStream(socket.getInputStream());
            this.objOut = new ObjectOutputStream(socket.getOutputStream());
        }catch(UnknownHostException e){
            System.out.println("Failed to connect to server: Unknown host");
            throw e;
        } catch (IOException e){
            System.out.println("Failed to connect to server: IO error");
            throw e;
        }
        this.selfPlayer = getSelfPlayer(rgb);
        this.player = new MPPlayer(rgb, true, game, new Point(selfPlayer.getX(), selfPlayer.getY()));



        GamePacket gp = waitForGameToStart(rgb);
        game.setCoinSeed(gp.getCoinSeed());
        game.setCoinAmount(gp.getStartingCoins());
        game.setEnemySeed(gp.getEnemyMoveSeed());
        game.setEnemyAmount(gp.getEnemyCuantity());
        for (WallPacket wp : gp.getWalls()){
            game.addWall(new Wall(wp.getX(), wp.getY(), wp.getWidth(), wp.getHeight()));
        }
        for (PlayerPacket pp : gp.getPlayers()){
            if (!pp.equals(selfPlayer))
                game.addPlayer(new MPPlayer(pp.getColor(), false, game, new Point(pp.getX(), pp.getY())));
        }

        initListener();
    }

    public MPPlayer getSelfPlayer(){
        return player;
    }

    public void sendPlayerMove(Point p){
        PlayerPacket packet = new PlayerPacket(p.x, p.y, rgb);
        try{
            objOut.writeObject(packet);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void sendEnemyMove(Point p, boolean axis, boolean direction){
        EnemyPacket packet = new EnemyPacket(p.x, p.y, axis, direction);
        try{
            objOut.writeObject(packet);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public GamePacket waitForGameToStart(int rgb){
        Object o;
        try{
            while(true){
                o = objIn.readObject();
                if (o instanceof StartGamePacket sgp){
                    if (sgp.getRgb() == rgb){
                        System.out.println("Game starting");
                        break;
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        try{
            while (true) {
                o = objIn.readObject();
                if (o instanceof GamePacket gp) {
                    return gp;
                }
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private PlayerPacket getSelfPlayer(Integer color){
        try{
            objOut.writeObject(color);
            Object o = objIn.readObject();
            if (o instanceof PlayerPacket pp){
                return pp;
            }
            return null;
        }catch(Exception e){
            return null;
        }
    }

    public void initListener(){
        new Thread(() -> {
            while (true){
                try{
                    Object o = objIn.readObject();
                    if (o instanceof PlayerPacket pp){
                        if (!pp.equals(selfPlayer))
                            players.put(pp.getColor(), pp);
                        // TODO: Update game state with player packets
                    }
                    // TODO: Recive enemy packets and interprete them
                }catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }
                try{
                    Thread.sleep(50);
                } catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }).start();
    }
    
}
