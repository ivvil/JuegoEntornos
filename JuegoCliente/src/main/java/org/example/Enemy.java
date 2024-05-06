package org.example;

import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JButton;

public class Enemy extends JButton {
    private final Game game;
    private final int sleepTime = 10;
    private final int speed = 2;

    public Enemy(Game game) {
        this.game = game;
        setText("•_•");
        setBounds(genRandomPosition());
        setBackground(new Color(255, 50, 50));
        setForeground(new Color(0, 0, 0));
    }

    public void startMove(){
        Movement movement = new Movement();
        movement.start();
    }

    private Rectangle genRandomPosition() {
        int size = 50;
        Rectangle r;
        do {
            r = new Rectangle(
                    (int) (Math.random() * game.getWidth()) - 100,
                    (int) (Math.random() * game.getHeight()) - 100,
                    size,
                    size);
        } while (game.checkColision(r) || game.checkColisionWithEnemy(r, this));
        return r;
    }

    private class Movement extends Thread {
        private boolean axis; // true move x | false move y
        private boolean direction; // true positive | false negative

        public Movement() {
            this.axis = Math.random() < 0.5;
            this.direction = Math.random() < 0.5;
        }

        @Override
        public void run() {
            int counter = 0;
            int change = (int) (Math.random() * 500) + 100;
            boolean result;
            while (true) {
                if (counter++ > change) {
                    this.axis = Math.random() < 0.5;
                    this.direction = Math.random() < 0.5;
                    counter = 0;
                    change = (int) (Math.random() * 500) + 100;
                }
                if (axis) {
                    if (direction) {
                        if (getX() + getWidth() + 15 > game.getWidth())
                            direction = !direction;
                        result = moveEnemy(Direction.RIGHT);
                    } else {
                        if (getX() - 15 < 0)
                            direction = !direction;
                        result = moveEnemy(Direction.LEFT);
                    }
                } else {
                    if (direction) {
                        if (getY() + getHeight() + 15 > game.getHeight())
                            direction = !direction;
                        result = moveEnemy(Direction.DOWN);
                    } else {
                        if (getY() - 15 < 0)
                            direction = !direction;
                        result = moveEnemy(Direction.UP);
                    }
                }
                if (result) {
                    this.direction = !direction;
                    this.axis = !axis;
                }

                if (game.getPlayer().getBounds().intersects(getBounds())) {
                    game.getPlayer().setHealth(game.getPlayer().getHealth() - 1);
                    setLocation(genRandomPosition().getLocation());
                    this.direction = Math.random() < 0.5;
                    this.axis = Math.random() < 0.5;
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private boolean moveEnemy(Direction d) {
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
        Rectangle r = new Rectangle(x, y, getWidth(), getHeight());
        if (game.checkColision(r))
            return true;

        if (game.checkColisionWithEnemy(r, this))
            return true;

        setLocation(x, y);

        if (game.checkColision(r)) {
            setLocation(original_x, original_y);
            return true;
        }

        if (game.checkColisionWithEnemy(r, this)) {
            setLocation(original_x, original_y);
            return true;
        }

        return false;
    }

}
