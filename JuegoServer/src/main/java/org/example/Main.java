package org.example;

import static org.example.Logger.error;
import static org.example.Logger.info;
import static org.example.Logger.warning;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.example.packets.Packet;
import org.example.packets.admin.IAmAnAdminPacket;
import org.example.packets.admin.StartGamePacket;
import org.example.packets.client.EnemyPacket;
import org.example.packets.client.GamePacket;
import org.example.packets.client.PlayerPacket;
import org.example.packets.client.WallPacket;

public class Main {
    private static final HashMap<Socket, Integer> clients = new HashMap<>();
    private static final HashMap<Integer, PlayerPacket> players = new HashMap<>();
    private static final int numEnemis = 10;
    private static final int initialNumCoins = 10;
    private static final int port = 6942;
    private static ServerSocket serverSocket = null;
    private static ThreadPool pool = null;
    private static GamePacket gamePacket = null;
    private static Socket admin = null;
    private static boolean isGameStarted = false;
    private static boolean isAdminConnected = false;
    private static int maxPlayers;


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
        maxPlayers = workers - 1;
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
        sendPacketToEveryClient(gamePacket);
        for (Socket s : clients.keySet()){
            pool.execute(() -> {
                while (true){
                    try{
                        ObjectInputStream objIn = new ObjectInputStream(s.getInputStream());
                        Object o = objIn.readObject();
                        if (o instanceof PlayerPacket pp){
                            sendPacketToEveryClientExcept(pp, pp.getColor());
                        }else if (o instanceof EnemyPacket ep){
                            sendPacketToEveryClientExcept(ep, clients.get(s));
                        }else {
                            warning("Unknown packet received from client: " + o);
                        }
                    } catch (IOException e){
                        error("Error while reading object: " + e.getMessage());
                    } catch (ClassNotFoundException e){
                        error("Error while reading object: " + e.getMessage());
                    }
                }
            });
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
                    if (players.size() >= maxPlayers) {
                        // TODO: Send to the client that the server is full
                        warning("A client tried to connect but the server is full: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                        return;
                    }
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
                    info("Player connected: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                    if (isAdminConnected){
                        admin.getOutputStream().write(players.size());
                    }
                } else if (o instanceof StartGamePacket) {
                    if (isAdminConnected && socket == admin) {
                        info("Game started");
                        startGame();
                    }else {
                        warning("A client tried to start the game without being the admin" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                    }
                } else if (o instanceof IAmAnAdminPacket) {
                    if (isAdminConnected){
                        warning("An admin tried to connect while another admin is already connected: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                        return;
                    }
                    info("Admin connected to the server");
                    admin = socket;
                    isAdminConnected = true;
                    String data = players.size() + "|" + pool.getPoolSize();
                    objOut.writeObject(data);
                }
            } catch (ClassNotFoundException e){
                error("Coudn't read object: " + e.getMessage());
            }
        }catch (IOException e){
            error("Error while creating output stream: " + e.getMessage());
        }
    }

    private static void sendPacketToEveryClient(Packet p){
        for (Socket s : clients.keySet()){
            try{
                ObjectOutputStream objOut = new ObjectOutputStream(s.getOutputStream());
                objOut.writeObject(p);
            } catch (IOException e){
                error("Error while sending packet to client: " + e.getMessage());
            }
        }
    }

    private static void sendPacketToEveryClientExcept(Packet p, int rgb){
        for (Socket s : clients.keySet()){
            if (clients.get(s) != rgb) {
                try {
                    ObjectOutputStream objOut = new ObjectOutputStream(s.getOutputStream());
                    objOut.writeObject(p);
                } catch (IOException e) {
                    error("Error while sending packet to client: " + e.getMessage());
                }
            }
        }
    }
}
