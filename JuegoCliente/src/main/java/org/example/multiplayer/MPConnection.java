package org.example.multiplayer;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.example.packets.admin.StartGamePacket;
import org.example.packets.client.EnemyPacket;
import org.example.packets.client.PlayerPacket;

public class MPConnection {
    private final int rgb;
    private final Socket socket;
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;

    public static MPConnection newConnection(String host, int port, int rgb){
        try{
            return new MPConnection(host, port, rgb);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            e.printStackTrace();
            return null;
        }
    }

    public MPConnection(String host, int port, int rgb) throws UnknownHostException, IOException{
        this.rgb = rgb;
        try{
            this.socket = new Socket(host, port);
            this.objIn = new ObjectInputStream(socket.getInputStream());
            this.objOut = new ObjectOutputStream(socket.getOutputStream());
        }catch(UnknownHostException e){
            throw e;
        } catch (IOException e){
            throw e;
        }
        initListener();
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

    public void waitForGameToStart(int rgb){
        Object o = null;
        try{
            while(true){
                o = objIn.readObject();
                if (o instanceof StartGamePacket sgp){
                    if (sgp.getRgb() == rgb){
                        System.out.println("Game starting");
                        break;
                    }
                }else{
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void initListener(){
        new Thread(() -> {
            while (true){
                try{
                    Object o = objIn.readObject();
                    if (o instanceof PlayerPacket pp){
                        System.out.println("Player packet received: " + pp);
                        // TODO: Create the instances for the players
                    }
                    // TODO: Recive enemy packets and interprete the,
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
