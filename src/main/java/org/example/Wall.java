package org.example;

import java.awt.Rectangle;
import javax.swing.JButton;

public class Wall extends JButton {
    public boolean isColiding(Rectangle r) {
        return getBounds().intersects(r);
    }

    public Wall(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
    }
}
