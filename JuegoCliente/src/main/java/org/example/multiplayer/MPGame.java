package org.example.multiplayer;

import org.example.Wall;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class MPGame extends JPanel {
    private final int currentPlayerRGB;
    private final Vector<Wall> walls;
    private final Vector<MPEnemy> enemys;
    private final MPPlayer player;
    private final Vector<MPPlayer> onlinePlayers;
    private final MPConnection connection;
    private final JFrame frame;
    private int enemySeed;
    private int enemyAmount;
    private int coinSeed;
    private int coinAmount;


    public MPGame(String host, int port, int rgb, JFrame frame) {
        this.frame = frame;
        this.currentPlayerRGB = rgb;
        this.walls = new Vector<>();
        this.onlinePlayers = new Vector<>();
        this.enemys = new Vector<>();
        this.connection = MPConnection.newConnection(host, port, rgb, this);
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Error connecting to server\nCheck the console for details", "Error", JOptionPane.ERROR_MESSAGE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            player = null;
            return;
        }
        this.player = connection.getSelfPlayer();

        retriveGameInfo();
    }

    public void retriveGameInfo() {

    }

    public int getPlayerRGB() {
        return currentPlayerRGB;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Vector<MPPlayer> getOnlinePlayers() {
        return onlinePlayers;
    }

    public MPConnection getConnection() {
        return connection;
    }

    public Vector<Wall> getWalls() {
        return walls;
    }

    public void start() {

    }

    public MPPlayer getPlayer() {
        return player;
    }

    public boolean checkColisionWithEnemy(Rectangle r, MPEnemy self) {
        for (MPEnemy e : enemys) {
            if (!e.equals(self))
                if (e.getBounds().intersects(r))
                    return true;
        }
        return checkColision(r);
    }

    public boolean checkColision(Rectangle r) {
        if (r.getX() < 0 || r.getX() > getWidth() - r.getWidth())
            return true;
        if (r.getY() < 0 || r.getY() > getHeight() - r.getHeight())
            return true;

        for (Wall w : walls) {
            if (w.getBounds().intersects(r))
                return true;
        }
        return false;
    }

    public void addEnemy(MPEnemy e) {
        enemys.add(e);
    }

    public void addPlayer(MPPlayer p) {
        onlinePlayers.add(p);
    }

    public void setEnemySeed(int seed) {
        enemySeed = seed;
    }

    public void setEnemyAmount(int amount) {
        enemyAmount = amount;
    }

    public void setCoinSeed(int seed) {
        coinSeed = seed;
    }

    public void setCoinAmount(int amount) {
        coinAmount = amount;
    }

    public void addWall(Wall w) {
        walls.add(w);
    }

    public void moveEnemy(org.example.packets.client.EnemyPacket ep) {

    }
}
