package org.example;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

public class Player extends JButton {
    private double    maxSpeed     =  5;
    private final int sleepTime    = 10;
    private boolean isWPressed     = false;
    private boolean isSPressed     = false;
    private boolean isAPressed     = false;
    private boolean isDPressed     = false;
    private double instantSpeed    = maxSpeed;

    private final int[] screenBounds;

    public Player(int[] screenBounds, int width, int height){
        this.screenBounds = screenBounds;
        handleMovement();
    }

    private class KeyHandler extends Thread{
        private final double diagonalSpeed = 0.7071067811865476 * maxSpeed; // Math.cos(Math.PI/4) * maxSpeed;
        @Override
        public void run() {
            while (true) {
                if (isWPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isAPressed || isDPressed && !isSPressed) instantSpeed = diagonalSpeed;
                    }else if (!isAPressed && !isDPressed && !isSPressed) instantSpeed = maxSpeed;
                    movePlayer(Direction.UP);
                }
                if (isSPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isAPressed || isDPressed && !isWPressed) instantSpeed = diagonalSpeed;
                    } else if (!isAPressed && !isDPressed && !isWPressed) instantSpeed = maxSpeed;

                    movePlayer(Direction.DOWN);
                }
                if (isAPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isWPressed || isSPressed && !isDPressed) instantSpeed = diagonalSpeed;
                    } else if (!isWPressed && !isSPressed && !isDPressed) instantSpeed = maxSpeed;

                    movePlayer(Direction.LEFT);

                }
                if (isDPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isWPressed || isSPressed && !isAPressed) instantSpeed = diagonalSpeed;
                    } else if (!isWPressed && !isSPressed && !isAPressed) instantSpeed = maxSpeed;

                    movePlayer(Direction.RIGHT);
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    private void handleMovement(){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((e) -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_W)
                    isWPressed = true;

                if (e.getKeyCode() == KeyEvent.VK_S)
                    isSPressed = true;

                if (e.getKeyCode() == KeyEvent.VK_A)
                    isAPressed = true;

                if (e.getKeyCode() == KeyEvent.VK_D)
                    isDPressed = true;

            }
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                if (e.getKeyCode() == KeyEvent.VK_W)
                    isWPressed = false;

                if (e.getKeyCode() == KeyEvent.VK_S)
                    isSPressed = false;

                if (e.getKeyCode() == KeyEvent.VK_A)
                    isAPressed = false;

                if (e.getKeyCode() == KeyEvent.VK_D)
                    isDPressed = false;

            }
            return false;
        });
        KeyHandler kh = new KeyHandler();
        kh.start();
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private void movePlayer(Direction d) {
        int x = getX();
        int y = getY();
        switch (d) {
            case UP:
                y -= (int) Math.round(instantSpeed);
                break;
            case DOWN:
                y += (int) Math.round(instantSpeed);
                break;
            case LEFT:
                x -= (int) Math.round(instantSpeed);
                break;
            case RIGHT:
                x += (int) Math.round(instantSpeed);
                break;
        }
        if (x < 0 || x > screenBounds[0] - getWidth()) return;
        if (y < 0 || y > screenBounds[1] - getHeight()) return;
        setLocation(x, y);
    }
}
