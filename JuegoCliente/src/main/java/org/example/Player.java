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
	
    private double maxSpeed = 1;
	private double acceleration = 0.02;
	private double friction = 1;
    private final int maxHealth = 10;
    private double instantSpeed = maxSpeed; // Why are maxSpeed and instantSpeed two different variables?
    private int health = maxHealth;

    private final Game game;

    public Player(Game game) {
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

	// public void move(Vector2 v) {
	// 	setPosition(Vector2.toPoint2(Vector2.add(v, Point2.toVector2(getPosition()))));
	// }
	

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
