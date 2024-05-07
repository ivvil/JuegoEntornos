package org.example.packets;

public class EnemyPacket extends Packet{
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
