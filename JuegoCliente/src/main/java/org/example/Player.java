package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

public class Player extends JButton {
	// TODO Rip out this fucking piece of trash
	
    private double maxSpeed = 3;
    private final int sleepTime = 6;
    private final int maxHealth = 10;
    private boolean isWPressed = false;
    private boolean isSPressed = false;
    private boolean isAPressed = false;
    private boolean isDPressed = false;
    private double instantSpeed = maxSpeed;
    private int health = maxHealth;

    private final Game game;

    public Player(Game game) {
        handleMovement();
        this.game = game;
        setBackground(new Color(74, 196, 250));
        setText("•.•");
        new Thread(() -> {
            int len = 0;
            while (true) {
                long frameStart = System.nanoTime();
                game.revalidate();
                long frameEnd = System.nanoTime();
                double frameRate = 1000000000.0 / (frameEnd - frameStart);
                String fps = String.format("FPS: %.2f", frameRate);
                System.out.println(fps);
                len = fps.length();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.print("\033[1F\033[" + len + "J");
                System.out.flush();
            }
        }).start();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private class KeyHandler extends Thread {
        private final double diagonalSpeed = 0.7071067811865476 * maxSpeed; // Math.cos(Math.PI/4) * maxSpeed;

        @Override
        public void run() {
            while (true) {
                if (isWPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isAPressed || isDPressed && !isSPressed)
                            instantSpeed = diagonalSpeed;
                    } else if (!isAPressed && !isDPressed && !isSPressed)
                        instantSpeed = maxSpeed;
                    movePlayer(Direction.UP);
                }
                if (isSPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isAPressed || isDPressed && !isWPressed)
                            instantSpeed = diagonalSpeed;
                    } else if (!isAPressed && !isDPressed && !isWPressed)
                        instantSpeed = maxSpeed;

                    movePlayer(Direction.DOWN);
                }
                if (isAPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isWPressed || isSPressed && !isDPressed)
                            instantSpeed = diagonalSpeed;
                    } else if (!isWPressed && !isSPressed && !isDPressed)
                        instantSpeed = maxSpeed;

                    movePlayer(Direction.LEFT);

                }
                if (isDPressed) {
                    if (instantSpeed == maxSpeed) {
                        if (isWPressed || isSPressed && !isAPressed)
                            instantSpeed = diagonalSpeed;
                    } else if (!isWPressed && !isSPressed && !isAPressed)
                        instantSpeed = maxSpeed;

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

    private void handleMovement() {
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

    private void movePlayer(Direction d) {
        int original_x = getX();
        int original_y = getY();
        int x = original_x;
        int y = original_y;
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
        if (game.checkColision(new Rectangle(x, y, getWidth(), getHeight())))
            return;

        setLocation(x, y);

        if (game.checkColision(new Rectangle(x, y, getWidth(), getHeight())))
            setLocation(original_x, original_y);
    }
}
