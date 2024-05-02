package org.example;

import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class Coin extends JLabel {
    private final Rectangle hitBox;
    public Coin(int[] screenBounds){
        setText("O");
        int[] pos = genRandomPosition(screenBounds);
        setFont(new Font("Sans Serif", Font.BOLD, 16));
        setForeground(new Color(189, 198, 0));
        hitBox = new Rectangle(pos[0], pos[1], 25, 25);
        setBounds(hitBox);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    private static int[] genRandomPosition(int[] screenBounds){
        int width = (int) (Math.random() * screenBounds[0]);
        int height = (int) (Math.random() * screenBounds[1]);

        return new int[]{width, height};
    }
}
