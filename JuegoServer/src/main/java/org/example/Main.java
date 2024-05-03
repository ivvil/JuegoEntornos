package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

public class Main {
    private static final int port = 6942;
    private static ServerSocket serverSocket = null;
    private static InputStream in = null;
    private static OutputStream out = null;


    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.getInetAddress();
            System.out.println("Server address: " + serverSocket.getInetAddress());

            System.out.println("Server started!");

            while (true) {
                System.out.println("Waiting for a client...");
                var socket = serverSocket.accept();
                System.out.println("Client connected!");
                in = socket.getInputStream();
                out = socket.getOutputStream();
                byte[] buffer = new byte[1024];
                int read = in.read(buffer);
                String message = new String(buffer, 0, read);

                System.out.println("Client message: " + message);
                out.write("Hello, client!".getBytes());
                socket.close();
                if (message.equals("exit")) {
                    System.out.println("Server stopped!");
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}