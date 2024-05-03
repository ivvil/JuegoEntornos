package org.example.multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MPGame {
    private final String host;
    private final int port;
    private final int rgb;
    private final Socket socket;
    private final InputStream input;
    private final OutputStream output;


    public MPGame(String host, int port, int rgb){
        this.host = host;
        this.port = port;
        this.rgb = rgb;
        try {
            this.socket = new Socket(host, port);
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(){
        new Thread(() -> {
            try {
                while (true){
                    byte[] buffer = new byte[1024];
                    int read = input.read(buffer);
                    if (read == -1){
                        break;
                    }
                    String message = new String(buffer, 0, read);
                    System.out.println(message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
