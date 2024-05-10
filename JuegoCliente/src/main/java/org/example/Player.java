package org.example;

import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

public class Player extends JButton {
	
    private double maxSpeed = 3;
    private final int sleepTime = 6;
    private final int maxHealth = 10;
    private double instantSpeed = maxSpeed;
    private int health = maxHealth;
    private String name = "Player";
    private final Game game;

    // [0, 0] : Nothing pressed | [1, 0] : W pressed | [0, 1] : A pressed | [1, 1] : W and A pressed
    //[-1 , 0]: S pressed | [0, -1] : D pressed | [-1, -1] : S and D pressed | [1, -1] : W and D pressed | [-1, 1] : S and A pressed
    private final byte[] inputMap = new byte[2];

    public Player(Game game) {
        this.game = game;
        setupInputListener();
        setFont(new Font("Arial", Font.PLAIN, 15));
        setText("•.•");
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    private class MovePlayer extends Thread {
        private final double diagonalSpeed = 0.7071067811865476 * maxSpeed; // Math.cos(Math.PI/4) * maxSpeed;

        @Override
        public void run() {
            while (true) {
                if (inputMap[0] != 0 && inputMap[1] != 0)
                    instantSpeed = diagonalSpeed;
                else
                    instantSpeed = maxSpeed;
                
                if (inputMap[0] == 1)
                    movePlayer(Direction.UP, instantSpeed);
                
                if (inputMap[0] == -1)
                    movePlayer(Direction.DOWN, instantSpeed);
                
                if (inputMap[1] == 1)
                    movePlayer(Direction.LEFT, instantSpeed);
                
                if (inputMap[1] == -1)
                    movePlayer(Direction.RIGHT, instantSpeed);
                try{
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setupInputListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((e) -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_W)
                    if(inputMap[0] < 1)
                        inputMap[0] += 1;

                if (e.getKeyCode() == KeyEvent.VK_S)
                    if(inputMap[0] > -1)
                        inputMap[0] -= 1;
                    

                if (e.getKeyCode() == KeyEvent.VK_A)
                    if(inputMap[1] < 1)
                        inputMap[1] += 1;
                    

                if (e.getKeyCode() == KeyEvent.VK_D)
                    if(inputMap[1] > -1)
                        inputMap[1] -= 1;
                    

            }
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                if (e.getKeyCode() == KeyEvent.VK_W)
                    if (inputMap[0] > -1)
                        inputMap[0] -= 1;
                
                if (e.getKeyCode() == KeyEvent.VK_S)
                    if (inputMap[0] < 1)
                        inputMap[0] += 1;

                if (e.getKeyCode() == KeyEvent.VK_A)
                    if (inputMap[1] > -1)
                        inputMap[1] -= 1;

                if (e.getKeyCode() == KeyEvent.VK_D)
                    if (inputMap[1] < 1)
                        inputMap[1] += 1;
                    
            }
            return false;
        });
        MovePlayer mp = new MovePlayer();
        mp.start();
    }



    private void movePlayer(Direction d, double speed) {
        int original_x = getX();
        int original_y = getY();
        int x = original_x;
        int y = original_y;
        
        switch (d) {
            case UP:
                y -= (int) Math.round(speed);
                break;
            case DOWN:
                y += (int) Math.round(speed);
                break;
            case LEFT:
                x -= (int) Math.round(speed);
                break;
            case RIGHT:
                x += (int) Math.round(speed);
                break;
        }
        if (game.checkColision(new Rectangle(x, y, getWidth(), getHeight())))
            return;

        setLocation(x, y);

        if (game.checkColision(new Rectangle(x, y, getWidth(), getHeight())))
            setLocation(original_x, original_y);
    }
}
