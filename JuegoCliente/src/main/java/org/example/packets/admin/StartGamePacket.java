package org.example.packets.admin;

import org.example.packets.Packet;

public class StartGamePacket extends Packet{
    private final int x;
    private final int y;
    private final int rgb;

    public StartGamePacket(int x, int y, int rgb){
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

    public int getRgb() {
        return rgb;
    }
}
