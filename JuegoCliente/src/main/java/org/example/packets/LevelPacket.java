package org.example.packets;

public class LevelPacket {
    private final WallPacket[] walls;

    public LevelPacket(WallPacket[] walls) {
        this.walls = walls;
    }

    public WallPacket[] getWalls() {
        return walls;
    }

}
