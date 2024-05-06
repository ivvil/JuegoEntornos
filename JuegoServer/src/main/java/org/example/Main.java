package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.example.packets.EnemyPacket;
import org.example.packets.GamePacket;
import org.example.packets.PlayerPacket;
import org.example.packets.WallPacket;

import static org.example.Logger.*;

public class Main {
    private static final int port = 6942;
    private static ServerSocket serverSocket = null;
    private static ThreadPool pool = null;
    private static Vector<Socket> clients = new Vector<>();
    private static Vector<PlayerPacket> players = new Vector<>();
    private static GamePacket gamePacket = null;
    private static int numEnemis = 10;


    public static void main(String[] args) {
        int maxWorkers = Runtime.getRuntime().availableProcessors();
        gamePacket = new GamePacket(new WallPacket[]{
            // TODO: Add walls describing the level
        }, (int) (Math.random() * 1000));

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

            while (true) {
                
                Socket socket = serverSocket.accept();
                info("Client connected: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                pool.execute(() -> {
                    onClientConnect(socket);
                    try{
                        poocessRequest(socket);
                    } catch (IOException e){
                        error("Error while processing request: " + e.getMessage());
                    }
                });
                
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startGame(){
        for(Socket s : clients){
            try{
                ObjectOutputStream objOut = new ObjectOutputStream(s.getOutputStream());
                objOut.writeObject(gamePacket);
            } catch (IOException e){
                error("Error while sending game packet to client: " + e.getMessage());
            }
        }
    }

    public static void onClientConnect(Socket socket){
        clients.add(socket);
        try{
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            PlayerPacket[] pp = new PlayerPacket[1];
            Object o = null;
            try {
                 o = objIn.readObject();
                if (o instanceof PlayerPacket){
                    pp[0] = (PlayerPacket) o;
                    gamePacket.addPlayer(pp[0]);
                    info("Player packet received: " + pp[0]);
                }else{
                    error("Invalid packet received: " + o + " " + o.getClass());
                }
            } catch (ClassNotFoundException e){
                error("Coudn't read object: " + e.getMessage());
            }

            objOut.writeObject(gamePacket);
            for (Socket s : clients){
                if (s != socket){
                    pool.execute(() -> {
                        try{
                            objOut.writeObject(pp[0]);
                        } catch (IOException e){
                            error("Error while sending packet to client: " + e.getMessage() + " " + s.getInetAddress().getHostAddress() + ":" + s.getPort());
                        }
                    });
                }
            }

        }catch (IOException e){
            error("Error while creating output stream: " + e.getMessage());
        }

    }

    private static void poocessRequest(Socket socket) throws IOException {
        clients.add(socket);
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        
        try {
            Object o = objIn.readObject();
            if (o instanceof PlayerPacket pp){
                info("Player packet received: " + pp);
                for (Socket s : clients){
                    if (s != socket){
                        pool.execute(() -> {
                            try{
                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                out.writeObject(pp);
                            } catch (IOException e){
                                error("Error while sending packet to client: " + e.getMessage());
                            }
                        });
                    }
                }

            }else if(o instanceof EnemyPacket ep){
                info("Enemy packet received: " + ep);
                for (Socket s : clients){
                    if (s != socket){
                        pool.execute(() -> {
                            try{
                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                out.writeObject(ep);
                            } catch (IOException e){
                                error("Error while sending packet to client: " + e.getMessage());
                            }
                        });
                    }
                }
            }
        }catch (Exception e){
            error("Coudnt process request from:  " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " -> " + e.getMessage());
        }
        
        socket.close();
    }
}