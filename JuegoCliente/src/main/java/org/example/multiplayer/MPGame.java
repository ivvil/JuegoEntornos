package org.example.multiplayer;

import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.example.Wall;

public class MPGame extends JPanel{
    private final int rgb;
    private final Vector<Wall> walls;
    private final Vector<MPEnemy> enemys;
    private final MPPlayer player;
    private final Vector<MPPlayer> onlinePlayers;
    private final MPConnection connection;
    private final JFrame frame;


    public MPGame(String host, int port, int rgb, JFrame frame){
        this.frame = frame;
        this.rgb = rgb;
        this.connection = MPConnection.newConnection(host, port, rgb);
        this.walls = new Vector<>();
        this.onlinePlayers = new Vector<>();
        this.enemys = new Vector<>();
        this.player = new MPPlayer(rgb, true, this);

        if (connection == null){
            JOptionPane.showMessageDialog(null, "Error connecting to server\nCheck the console for details", "Error", JOptionPane.ERROR_MESSAGE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            return;
        }

        retriveGameInfo();
    }

    public MPConnection getConnection(){
        return connection;
    }

    public Vector<Wall> getWalls(){
        return walls;
    }

    public void start(){
        
    }

    public MPPlayer getPlayer(){
        return player;
    }

    public void retriveGameInfo(){

    }

    public boolean checkColision(Rectangle r){
        if (r.getX() < 0 || r.getX() > getWidth() - r.getWidth())
            return true;
        if (r.getY() < 0 || r.getY() > getHeight() - r.getHeight())
            return true;
        
        for (Wall w : walls){
            if (w.getBounds().intersects(r))
                return true;
        }
        return false;
    }

    public boolean checkColisionWithEnemy(Rectangle r, MPEnemy self){
        for (MPEnemy e : enemys){
            if (!e.equals(self))
                if (e.getBounds().intersects(r))
                    return true;
        }
        return checkColision(r);
    }

}
