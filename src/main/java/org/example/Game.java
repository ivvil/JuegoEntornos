package org.example;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.Vector;

public class Game extends JPanel {

    private final int          numCoins = 10;
    private final Player       player;
    private final Vector<Coin> coins;
    private final Game         gp = this;
    private final JLabel coinsCounter;
    private int coinsCount = 0;

    public Game(int width, int height) {
        setLayout(new BorderLayout());
        coinsCounter = new JLabel("Coins: " + coinsCount);
        add(coinsCounter);
        coinsCounter.setBounds((width / 2) - 25, 0, width, 50);
        int playerHeight = 50;
        int playerWidth = 25;
        int[] screenBounds = new int[]{width,height};
        this.player = new Player(screenBounds, playerWidth, playerHeight);
        add(player);
        player.setBounds((width / 2) - playerWidth, (height / 2) - playerHeight, playerWidth, playerHeight);
        coins = new Vector<>();
        for (int i = 0; i < numCoins; i++){
            coins.add(new Coin(screenBounds));
            add(coins.get(i));
            coins.get(i).setBounds(coins.get(i).getHitBox());
        }
        JLabel dummy = new JLabel(" ");
        add(dummy, BorderLayout.CENTER);
        InteractionsThread it = new InteractionsThread();
        it.start();
    }

    class InteractionsThread extends Thread{
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
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
