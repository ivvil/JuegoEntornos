package org.example.packets;

public class EnemyPacket {
    private int x;
    private int y;

    public EnemyPacket(int x, int y, int width, int height, int speed, int sleepTime) {
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
