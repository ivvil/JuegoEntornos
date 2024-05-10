package org.example;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.example.packets.admin.StartGamePacket;
import org.example.packets.client.GamePacket;
import org.example.packets.client.PlayerPacket;
import org.example.packets.client.WallPacket;

import static org.example.Logger.*;

public class Main {
    private static final HashMap<Socket, Integer> clients = new HashMap<>();
    private static final HashMap<Integer, PlayerPacket> players = new HashMap<>();
    private static final int numEnemis = 10;
    private static final int initialNumCoins = 10;
    private static final int port = 6942;
    private static ServerSocket serverSocket = null;
    private static ThreadPool pool = null;
    private static GamePacket gamePacket = null;
    private static boolean isGameStarted = false;


    public static void main(String[] args) {
        Logger.setDebug(true);
        int maxWorkers = Runtime.getRuntime().availableProcessors();
        gamePacket = new GamePacket(new WallPacket[]{
            
            // TODO: Add walls describing the level

        }, (int) (Math.random() * 1000), (int) (Math.random() * 1000), numEnemis, initialNumCoins);

        int workers = Integer.parseInt(JOptionPane.showInputDialog("How many workers do you want to use?"));
        if (workers > maxWorkers) {
            error("The number of workers is too high, using the maximum available: " + maxWorkers);
            workers = maxWorkers;
        }
        pool = new ThreadPool(workers);
        try {
            serverSocket = new ServerSocket(port);
        
            InetAddress a = serverSocket.getInetAddress();
            info("Server started at " + a.getHostAddress() + ":" + port);
            info("Server local host at " + InetAddress.getLocalHost().getHostAddress() + ":" + port);

            while (true) {
                Socket socket = serverSocket.accept();
                info("Client connected: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                pool.execute(() -> {
                    onClientConnect(socket);
                });
                
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Color genRandomColor(){
        return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public static void startGame(){
        isGameStarted = true;
        for(Socket s : clients.keySet()){
            try{
                ObjectOutputStream objOut = new ObjectOutputStream(s.getOutputStream());
                objOut.writeObject(gamePacket);
                // TODO: Start listening for player packets
            } catch (IOException e){
                error("Error while sending game packet to client: " + e.getMessage());
            }
        }
    }

    private static boolean isPosFree(int x, int y){
        for (WallPacket w : gamePacket.getWalls()){
            Rectangle r = new Rectangle(w.getX(), w.getY(), w.getWidth(), w.getHeight());
            if (r.intersects(x, y, 50, 50)){
                return false;
            }
        }
        return true;
    }

    public static void onClientConnect(Socket socket){
        if (isGameStarted){
            warning("Client tried to connect after the game started");
            warning("The connection will be thrown");
            return;
        }
        try{
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            PlayerPacket[] pp = new PlayerPacket[1];
            Object o;
            try {
                o = objIn.readObject();
                if (o instanceof Integer i) {
                    int playerColor = i;
                    while (players.containsKey(playerColor)) {
                        playerColor = genRandomColor().getRGB();
                    }
                    PlayerPacket newPlayer = new PlayerPacket((int) (Math.random() * 1850), (int) (Math.random() * 1010), playerColor);
                    while (!isPosFree(newPlayer.getX(), newPlayer.getY())) {
                        newPlayer = new PlayerPacket((int) (Math.random() * 1850), (int) (Math.random() * 1010), playerColor);
                    }
                    pp[0] = newPlayer;
                    objOut.writeObject(pp[0]);
                    gamePacket.addPlayer(pp[0]);
                    clients.put(socket, playerColor);
                    players.put(playerColor, pp[0]);
                } else if (o instanceof StartGamePacket) {
                    info("Game started");
                    startGame();
                }
            } catch (ClassNotFoundException e){
                error("Coudn't read object: " + e.getMessage());
                return;
            }

        }catch (IOException e){
            error("Error while creating output stream: " + e.getMessage());
        }

    }


    // // Use this when the game is started to process the cleint sended packets
    // private static void poocessRequest(Socket socket) throws IOException {
    //     ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
    //     ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        
    //     try {
    //         Object o = objIn.readObject();
    //         if (o instanceof PlayerPacket pp){
    //             info("Player packet received: " + pp);
    //             for (Socket s : clients.keySet()){
    //                 if (s != socket){
    //                     pool.execute(() -> {
    //                         try{
    //                             ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
    //                             out.writeObject(pp);
    //                         } catch (IOException e){
    //                             error("Error while sending packet to client: " + e.getMessage());
    //                         }
    //                     });
    //                 }
    //             }

    //         }else if(o instanceof EnemyPacket ep){
    //             info("Enemy packet received: " + ep);
    //             for (Socket s : clients.keySet()){
    //                 if (s != socket){
    //                     pool.execute(() -> {
    //                         try{
    //                             ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
    //                             out.writeObject(ep);
    //                         } catch (IOException e){
    //                             error("Error while sending packet to client: " + e.getMessage());
    //                         }
    //                     });
    //                 }
    //             }
    //         }
    //     }catch (Exception e){
    //         error("Coudnt process request from:  " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " -> " + e.getMessage());
    //     }
        
    //     socket.close();
    // }
}
