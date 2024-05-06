package org.example.multiplayer;

import java.awt.Point;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.example.packets.PlayerPacket; 

public class MPConnection {
    private final String host;
    private final int port;
    private final int rgb;
    private Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;
    private ObjectInputStream objIn = null;
    private ObjectOutputStream objOut = null;

    public MPConnection(String host, int port, int rgb){
        this.host = host;
        this.port = port;
        this.rgb = rgb;
        try{
            this.socket = new Socket(host, port);
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
            this.objIn = new ObjectInputStream(in);
            this.objOut = new ObjectOutputStream(out);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
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

    public void initListener(){
        new Thread(() -> {
            while (true){
                try{
                    Object o = objIn.readObject();
                    if (o instanceof PlayerPacket pp){
                        System.out.println("Player packet received: " + pp);
                    }
                    // TODO: Handle more inputs
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
