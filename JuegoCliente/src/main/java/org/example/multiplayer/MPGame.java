package org.example.multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import org.example.Wall;

public class MPGame {
    private final String host;
    private final int port;
    private final int rgb;
    private final Vector<Wall> walls;


    public MPGame(String host, int port, int rgb){
        this.host = host;
        this.port = port;
        this.rgb = rgb;
        this.walls = new Vector<>();
    }

    public Vector<Wall> getWalls(){
        return walls;
    }

    public void start(){
        
    }

}
