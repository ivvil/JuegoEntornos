package org.example.multiplayer;

import org.example.Coin;
import org.example.Wall;
import org.example.highscore.Score;
import org.example.packets.client.EnemyPacket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Vector;

public class MPGame extends JPanel {
    private final int currentPlayerRGB;
    private final Vector<Wall> walls;
    private final Vector<MPEnemy> enemys;
    private final MPPlayer player;
    private final Vector<MPPlayer> onlinePlayers;
    private final Vector<Coin> coins;
    private final MPConnection connection;
    private final JFrame frame;
    private final JLabel dummy = new JLabel(" ");
    private int enemySeed;
    private int enemyAmount;
    private int coinSeed;
    private int coinAmount;
    private Random coinRandom;


    public MPGame(String host, int port, int rgb, JFrame frame) {
        this.frame = frame;
        this.currentPlayerRGB = rgb;
        this.walls = new Vector<>();
        this.onlinePlayers = new Vector<>();
        this.enemys = new Vector<>();
        this.coins = new Vector<>();
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

    public int getEnemyMovmentSeed() {
        return enemySeed;
    }

    public int getCoinSpawnSeed(){
        return coinSeed;
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

    public void syncStartGame() {
        new EventLoop().start();
        for (MPEnemy e : enemys) {
            e.startMove();
        }
        player.startMove();
    }

    public void startGame() {
        for (int i = 0; i < enemyAmount; i++){
            enemys.add(new MPEnemy(this, getEnemyMovmentSeed(), i));
        }
        coinRandom = new Random(coinSeed);
        for (int i = 0; i < coinAmount; i++){
            Rectangle pos;
            do {
                pos = new Rectangle(
                        coinRandom.nextInt(getWidth() - 80),
                        coinRandom.nextInt(getHeight() - 80),
                        20,
                        20
                );
            } while (checkColision(pos));
            Coin c = new Coin(pos);
            add(c);
            c.setBounds(pos);
            coins.add(c);
        }
    }

	public void stopGame() {
		
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

    public void moveEnemy(EnemyPacket ep) {
        for (MPEnemy e : enemys) {
            if (e.getEnemyId() == ep.getId()) {
                e.setLocation(ep.getX(), ep.getY());
                return;
            }
        }

    }

    class EventLoop extends Thread {
        @Override
        public void run() {
            new Thread(() -> {
                while (true){
                    if (coinRandom.nextFloat() > 0.8){
                        Rectangle pos;
                        do {
                            pos = new Rectangle(
                                coinRandom.nextInt(frame.getWidth() - 80),
                                coinRandom.nextInt(frame.getHeight() - 80),
                                20,
                                20
                            );
                        } while (checkColision(pos));
                        Coin c = new Coin(pos);
                        coins.add(c);
                        remove(dummy);
                        add(c);
                        c.setBounds(pos);
                        add(dummy, BorderLayout.CENTER);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            while (true) {
                for (int i = 0; i < coins.size(); i++) {
                    if (coins.get(i).getBounds().intersects(player.getBounds())) {
                        remove(coins.get(i));
                        //coinsCounter.setText("Coins: " + ++coinsCount); // TODO: Implement
                        coins.remove(coins.get(i));
                    }
                }

                // playerHealth.setText("Health: " + player.getHealth()); // TODO: Implement


                if (player.getHealth() <= 0) {
                    // gp.hideWindow(); // TODO: Implement
                    JOptionPane.showMessageDialog(null, "\tYou died!" /*+ "\nCoins collected: " + coinsCount*/, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    Score s = new Score();
                    s.setName(player.getName());
                    // s.setScore(coinsCount); // TODO: IMPLEMENT
                    s.sendScore();
                    // gp.jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
                    // mf.setVisible(true);
                    return;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
