package org.example;

import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class Coin extends JLabel {
    private final Rectangle hitBox;

    public Coin(Rectangle hitBox) {
        setText("O");
        setFont(new Font("Sans Serif", Font.BOLD, 16));
        setForeground(new Color(189, 198, 0));
        this.hitBox = hitBox;
        setBounds(hitBox);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
