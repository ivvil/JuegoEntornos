package org.example;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JButton;

import org.example.datatypes.Point2;
import org.example.datatypes.Vector2;

public class Player extends JButton {
	// TODO Rip out this fucking piece of trash
	
    private double maxSpeed = 3;
	private double acceleration = 0.2;
	private double friction = 0.1;
    private final int sleepTime = 6;
    private final int maxHealth = 10;
    private boolean isWPressed = false;
    private boolean isSPressed = false;
    private boolean isAPressed = false;
    private boolean isDPressed = false;
	private int[] timePressed = {0, 0, 0, 0};
    private double instantSpeed = maxSpeed; // Why are maxSpeed and instantSpeed two different variables?
    private int health = maxHealth;

    private final Game game;

    public Player(Game game) {
        handleMovement();
        this.game = game;
        setBackground(new Color(74, 196, 250));
        setText("•.•");
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

	public double getAcceleration() {
		return acceleration;
	}

	public double getFriction() {
		return friction;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public Point2 getPosition() {
		return new Point2(getX(), getY());
	}

	public void setPosition(Point2 p) {
		setLocation((int) p.x, (int) p.y);
	}

	public void move(Vector2 v) {
		setPosition(Vector2.toPoint2(Vector2.add(v, Point2.toVector2(getPosition()))));
	}
	
    private class KeyHandler extends Thread {
        private final double diagonalSpeed = 0.7071067811865476 * maxSpeed; // Math.cos(Math.PI/4) * maxSpeed;

        @Override
        public void run() {
            while (true) {
                if (isWPressed) {
					timePressed[0]++;
                    if (instantSpeed == maxSpeed) {
                        if (isAPressed || isDPressed && !isSPressed)
                            instantSpeed = diagonalSpeed;
					} else {
						if (!isAPressed && !isDPressed && !isSPressed) instantSpeed = maxSpeed;
						timePressed[0] = 0;
					}

                }
                if (isSPressed) {
					timePressed[1]++;
                    if (instantSpeed == maxSpeed) {
                        if (isAPressed || isDPressed && !isWPressed)
                            instantSpeed = diagonalSpeed;
                    } else {
						if (!isAPressed && !isDPressed && !isWPressed) instantSpeed = maxSpeed;
						timePressed[1] = 0;
					}

                    movePlayer(Direction.DOWN);
                }
                if (isAPressed) {
					timePressed[2]++;
                    if (instantSpeed == maxSpeed) {
                        if (isWPressed || isSPressed && !isDPressed)
                            instantSpeed = diagonalSpeed;
					} else {
						if (!isWPressed && !isSPressed && !isDPressed) instantSpeed = maxSpeed;
						timePressed[2] = 0;

					}
                    movePlayer(Direction.LEFT);

                }
                if (isDPressed) {
					timePressed[3]++;
                    if (instantSpeed == maxSpeed) {
                        if (isWPressed || isSPressed && !isAPressed)
                            instantSpeed = diagonalSpeed;
                    } else {
						if (!isWPressed && !isSPressed && !isAPressed) instantSpeed = maxSpeed;
						timePressed[3] = 0;
					}

                    movePlayer(Direction.RIGHT);
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
				System.out.println(Arrays.toString(timePressed));
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
        // kh.start();
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
