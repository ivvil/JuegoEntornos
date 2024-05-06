package org.example.packets;

import java.io.Serializable;

public class EnemyPacket implements Serializable{
    private int x;
    private int y;
    private boolean direction;
    private boolean axis;

    public EnemyPacket(int x, int y, boolean axis, boolean direction) {
        this.x = x;
        this.y = y;
        this.axis = axis;
        this.direction = direction;
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
}
