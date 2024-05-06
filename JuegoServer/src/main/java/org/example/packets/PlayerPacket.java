package org.example.packets;

import java.awt.*;
import java.io.Serializable;

public class PlayerPacket implements Serializable{
    private int x;
    private int y;
    private int rgb;

    public PlayerPacket(int x, int y, int rgb) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return new Color(rgb);
    }
}
