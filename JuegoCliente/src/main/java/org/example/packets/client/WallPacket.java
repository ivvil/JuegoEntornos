package org.example.packets.client;

import org.example.packets.Packet;

public class WallPacket extends Packet{
    private int x;
    private int y;
    private int width;
    private int height;

    public WallPacket(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
