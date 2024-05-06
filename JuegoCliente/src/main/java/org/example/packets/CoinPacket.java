package org.example.packets;

import java.io.Serializable;

public class CoinPacket implements Serializable{
    private int x;
    private int y;

    public CoinPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
