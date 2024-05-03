package org.example;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.Vector;
import java.awt.event.MouseListener;

public class Game extends JPanel {

    private final int          numCoins = 10;
    private final Player       player;
    private final Vector<Coin> coins;
    private final Vector<Wall> walls;
    private final Game         gp = this;
    private final JLabel coinsCounter;
    private int coinsCount = 0;
    private final JFrame jf;
    private static boolean isMuseClicked = false;
    private static Point clickPosition = new Point();

    public Game(int width, int height, JFrame frame) {
        this.jf = frame;
        setSize(width, height);
        setLayout(new BorderLayout());
        coinsCounter = new JLabel("Coins: " + coinsCount);
        add(coinsCounter);
        coinsCounter.setBounds((width / 2) - 25, 0, width, 50);
        int playerHeight = 50;
        int playerWidth = 25;
        this.player = new Player(this);
        add(player);
        addMouseListener(new MouseListener(){
            @Override
            public void mousePressed(MouseEvent e){
                isMuseClicked = true;
            }
            @Override
            public void mouseReleased(MouseEvent e){
                isMuseClicked = false;
            }
            @Override
            public void mouseClicked(MouseEvent e){
                clickPosition = e.getPoint();
            }
            @Override public void mouseEntered(MouseEvent e){}
            @Override public void mouseExited(MouseEvent e){}
        });
        player.setBounds((width / 2) - playerWidth, (height / 2) - playerHeight, playerWidth, playerHeight);
        walls = new Vector<>();
        Wall w1 = new Wall();
        w1.setBounds(40, 900, 800, 10);
        add(w1);
        walls.add(w1);
        coins = new Vector<>();
        for (int i = 0; i < numCoins; i++){
            coins.add(new Coin(this));
            add(coins.get(i));
            coins.get(i).setBounds(coins.get(i).getHitBox());
        }


        JLabel dummy = new JLabel(" ");
        add(dummy, BorderLayout.CENTER);
        EventLoop it = new EventLoop();
        it.start();
    }

    public boolean checkColision(Rectangle r){
        for (Wall w : walls){
            if (w.isColiding(r)) {
                return true;
            }
        }
        double x = r.getX();
        double y = r.getY();
        if (x < 0 || x > getWidth() - r.getWidth()) return true;
        if (y < 0 || y > getHeight() - r.getHeight()) return true;

        return false;
    }


    public Vector<Wall> getWalls(){
        return walls;
    }

    private void hideWindow(){
        jf.setVisible(false);
    }

    class EventLoop extends Thread{
        @Override
        public void run(){
            while (true){
                Rectangle playerHitBox = player.getBounds();
                for (int i = 0; i < coins.size(); i++){
                    if (coins.get(i).getHitBox().intersects(playerHitBox)){
                        gp.remove(coins.get(i));
                        coinsCounter.setText("Coins: " + ++coinsCount);
                        coins.remove(coins.get(i));
                        gp.repaint();
                    }
                }
                if (coinsCount >= 10) {
                    gp.hideWindow();
                    JOptionPane.showMessageDialog(null, "You Win!");
                    gp.jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
                    return;
                }
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
