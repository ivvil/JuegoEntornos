package org.example.packets.client;

import org.example.packets.Packet;

public class EnemyPacket extends Packet {
    private final int id;
    private final int x;
    private final int y;
    private final boolean direction;
    private final boolean axis;

    public EnemyPacket(int x, int y, boolean axis, boolean direction, int id) {
        this.x = x;
        this.y = y;
        this.axis = axis;
        this.direction = direction;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getAxis() {
        return axis;
    }

    public boolean getDirection() {
        return direction;
    }

    public int getId() {
        return id;
    }
}
