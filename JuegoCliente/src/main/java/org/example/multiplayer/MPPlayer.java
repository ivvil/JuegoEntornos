package org.example.multiplayer;

import javax.swing.JButton;
import org.example.Direction;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class MPPlayer extends JButton {
    private final int rgb;
    private final boolean isSelf;

    private double maxSpeed = 3;
    private final int sleepTime = 6;
    private final int maxHealth = 10;

    
    private double instantSpeed = maxSpeed;
    private int health = maxHealth;
    
    private final MPGame game;
    // [0, 0] : Nothing pressed | [1, 0] : W pressed | [0, 1] : A pressed | [1, 1] : W and A pressed
    //[-1 , 0]: S pressed | [0, -1] : D pressed | [-1, -1] : S and D pressed | [1, -1] : W and D pressed | [-1, 1] : S and A pressed
    private final byte[] inputMap = new byte[2];
    
    public MPPlayer(int rgb, boolean isSelf, MPGame game){
        this.rgb = rgb;
        this.isSelf = isSelf;
        this.game = game;
        if (isSelf){
            setupInputListener();
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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


    private void movePlayer(Direction direction, double speed){
        Point pos = getLocation();
        switch (direction){
            case UP:
                pos.y -= speed;
                break;
            case DOWN:
                pos.y += speed;
                break;
            case LEFT:
                pos.x -= speed;
                break;
            case RIGHT:
                pos.x += speed;
                break;
        }
        Rectangle r = new Rectangle(pos.x, pos.y, getWidth(), getHeight());
        if (!game.checkColision(r)){
            setLocation(pos);
        }
        game.getConnection().sendPlayerMove(pos);
    }  
}
