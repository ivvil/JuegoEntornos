package org.example;

import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class Coin extends JLabel {
    private final Rectangle hitBox;
    private final Game game;

    public Coin(Game game) {
        this.game = game;
        setText("O");
        setFont(new Font("Sans Serif", Font.BOLD, 16));
        setForeground(new Color(189, 198, 0));
        hitBox = genRandomPosition();
        setBounds(hitBox);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    private Rectangle genRandomPosition() {
        int size = 20;
        Rectangle r;
        do {
            r = new Rectangle(
                    (int) (Math.random() * game.getWidth()) - 100,
                    (int) (Math.random() * game.getHeight()) - 100,
                    size,
                    size);
        } while (game.checkColision(r));
        return r;
    }
}
