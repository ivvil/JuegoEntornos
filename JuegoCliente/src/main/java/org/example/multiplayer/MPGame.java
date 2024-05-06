package org.example.multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JPanel;

import org.example.Wall;

public class MPGame extends JPanel{
    private final String host;
    private final int port;
    private final int rgb;
    private final Vector<Wall> walls;
    private final MPPlayer player;


    public MPGame(String host, int port, int rgb){
        this.host = host;
        this.port = port;
        this.rgb = rgb;
        this.walls = new Vector<>();
        this.player = new MPPlayer(rgb, true, this);
    }

    public Vector<Wall> getWalls(){
        return walls;
    }

    public void start(){
        
    }

    public MPPlayer getPlayer(){
        return player;
    }

}
