package org.example;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class Game extends JPanel {

    private final int numCoins = 10;
    private final int numEnemys = 30;
    private final Player player;
    private final Vector<Coin> coins;
    private final Vector<Wall> walls;
    private final Vector<Enemy> enemys;
    private final Game gp = this;
    private final JLabel coinsCounter;
    private final JLabel playerHealth;
    private final JFrame jf;
    private final JFrame mf;
    private final int playerSize = 50;
    private int coinsCount = 0;

    public Game(int width, int height, JFrame frame, JFrame menuFrame) {
        this.jf = frame;
        this.mf = menuFrame;
        setSize(width, height);
        setLayout(new BorderLayout());
        coinsCounter = new JLabel("Coins: " + coinsCount);
        add(coinsCounter);
        coinsCounter.setBounds((width / 2), 0, width, 50);

        // Define player                        vscode -> MARK: player
        this.player = new Player(this);
        add(player);
        player.setBounds((width / 2) - playerSize, (height / 2) - playerSize, playerSize, playerSize);

        playerHealth = new JLabel("Health: " + player.getHealth());
        add(playerHealth);
        playerHealth.setBounds((width / 2), 50, width, 50);

        // Define walls                         vscode -> MARK: walls  
        walls = new Vector<>();
        Wall w1 = new Wall(40, 900, 800, 10);
        add(w1);
        walls.add(w1);
        enemys = new Vector<>();
        for (int i = 0; i < numEnemys; i++) {
            enemys.add(new Enemy(this));
            add(enemys.get(i));
        }
        for (Enemy i : enemys) {
            i.startMove();
        }
        
        // Define coins                         vscode -> MARK: coins
        coins = new Vector<>();
        for (int i = 0; i < numCoins; i++) {
            coins.add(new Coin(this));
            add(coins.get(i));
        }

        JLabel dummy = new JLabel(" ");
        add(dummy, BorderLayout.CENTER);
        EventLoop it = new EventLoop();
        it.start();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean checkColisionWithEnemy(Rectangle r, Enemy self) {
        for (Enemy e : enemys) {
            if (!e.equals(self))
                if (e.getBounds().intersects(r))
                    return true;

        }
        return false;
    }

    public boolean checkColision(Rectangle r) {
        for (Wall w : walls) {
            if (w.isColiding(r)) {
                return true;
            }
        }
        double x = r.getX();
        double y = r.getY();
        if (x < 0 || x > getWidth() - r.getWidth())
            return true;
        if (y < 0 || y > getHeight() - r.getHeight())
            return true;

        return false;
    }

    protected JFrame getFrame() {
        return jf;
    }

    public Vector<Wall> getWalls() {
        return walls;
    }

    private void hideWindow() {
        jf.setVisible(false);
    }

    class EventLoop extends Thread {
        @Override
        public void run() {
            while (true) {
                Rectangle playerHitBox = player.getBounds();
                for (int i = 0; i < coins.size(); i++) {
                    if (coins.get(i).getHitBox().intersects(playerHitBox)) {
                        gp.remove(coins.get(i));
                        coinsCounter.setText("Coins: " + ++coinsCount);
                        coins.remove(coins.get(i));
                        gp.repaint();
                    }
                }
                if (coinsCount >= 10) {
                    gp.hideWindow();
                    JOptionPane.showMessageDialog(null, "You Win!");
                    gp.getFrame().dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
                    mf.setVisible(true);
                    return;
                }

                playerHealth.setText("Health: " + player.getHealth());

                if (player.getHealth() <= 0) {
                    gp.hideWindow();
                    JOptionPane.showMessageDialog(null, "You Lose!");
                    gp.jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
                    mf.setVisible(true);
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
