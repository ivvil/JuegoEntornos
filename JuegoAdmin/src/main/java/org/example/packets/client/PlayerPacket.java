package org.example.packets.client;

import org.example.packets.Packet;

public class PlayerPacket extends Packet{
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

    public int getColor() {
        return rgb;
    }
}
