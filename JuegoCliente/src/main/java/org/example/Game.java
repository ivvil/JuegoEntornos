package org.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.example.highscore.HighScoreTable;
import org.example.highscore.Score;

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
    private final Random coinSeed;
    private final Random enemySeed;
    private int coinsCount = 0;
    private final JLabel dummy;

    public Game(int width, int height, JFrame frame, JFrame menuFrame, int coinSeed, int enemySeed) {
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

        // Define enemys                        vscode -> MARK: enemys
        enemys = new Vector<>();
        this.enemySeed = new Random(enemySeed);
        for (int i = 0; i < numEnemys; i++) {
            Point pos = null;
            do{
                pos = new Point(this.enemySeed.nextInt(width - 50), this.enemySeed.nextInt(height - 50));

            }while (checkColisionWithEnemy(new Rectangle(pos,new Dimension(50, 50)), null));
            Enemy e = new Enemy(this, pos);
            enemys.add(e);
            add(e);
        }
        for (Enemy i : enemys) {
            i.startMove();
        }
        
        // Define coins                         vscode -> MARK: coins
        coins = new Vector<>();
        this.coinSeed = new Random(coinSeed);

        for (int i = 0; i < numCoins; i++) {
            Rectangle pos;
            do {
                pos = new Rectangle(
                    this.coinSeed.nextInt(width - 20),
                    this.coinSeed.nextInt(height - 20),
                    20,
                    20
                );
            } while (checkColision(pos));
            
            coins.add(new Coin(pos));
            add(coins.get(i));
        }

        // HighScore table

        DefaultTableModel model = HighScoreTable.getHighScoreTableModel();
        if (model != null){
            JTable highScore = new JTable(model);
            highScore.setSelectionBackground(getBackground());
            highScore.setBackground(getBackground());
            add(highScore);
            highScore.setBounds(width - 175,25, 150, 250);
        }else{
            System.out.println("Coudn't connet to the database");
        }


        dummy = new JLabel(" ");
        add(dummy, BorderLayout.CENTER);
        EventLoop it = new EventLoop();
        it.start();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean checkColisionWithEnemy(Rectangle r, Enemy self) {
        for (Enemy e : enemys) {
            if (self == null || !e.equals(self))
                if (e.getBounds().intersects(r))
                    return true;

        }
        return checkColision(r);
    }

    public boolean checkColision(Rectangle r) {
        for (Wall w : walls) {
            if (w.getBounds().intersects(r)) {
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
    // Event loop                           vscode -> MARK: EventLoop
    class EventLoop extends Thread {
        @Override
        public void run() {
            new Thread(() -> {
                while (true){
                    if (coinSeed.nextFloat() > 0.8){
                        Rectangle pos;
                        do {
                            pos = new Rectangle(
                                coinSeed.nextInt(jf.getWidth() - 80),
                                coinSeed.nextInt(jf.getHeight() - 80),
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
                        gp.remove(coins.get(i));
                        coinsCounter.setText("Coins: " + ++coinsCount);
                        coins.remove(coins.get(i));
                    }
                }

                playerHealth.setText("Health: " + player.getHealth());


                if (player.getHealth() <= 0) {
                    gp.hideWindow();
                    JOptionPane.showMessageDialog(null, "\tYou died!\nCoins collected: " + coinsCount, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    Score s = new Score();
                    s.setName(player.getName());
                    s.setScore(coinsCount);
                    s.sendScore();
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
