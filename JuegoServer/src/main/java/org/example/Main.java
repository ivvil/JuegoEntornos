package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.example.packets.PlayerPacket;

import static org.example.Logger.*;

public class Main {
    private static final int port = 6942;
    private static ServerSocket serverSocket = null;
    private static ThreadPool pool = null;
    private static Vector<Socket> clients = new Vector<>();


    public static void main(String[] args) {
        int maxWorkers = Runtime.getRuntime().availableProcessors();

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

    private static void poocessRequest(Socket socket) throws IOException {
        clients.add(socket);
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        // TODO: Send initial data to the client
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

            }
        }catch (Exception e){
            error("Coudnt process request from:  " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " -> " + e.getMessage());
        }
        
        socket.close();
    }
}