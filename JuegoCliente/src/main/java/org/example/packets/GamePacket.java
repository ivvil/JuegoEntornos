package org.example.packets;

import java.util.Vector;

public class GamePacket{
    private final WallPacket[] walls;
    private final PlayerPacket[] players;
    private final EnemyPacket[] enemys;
    private final Integer enemyMoveSeed;


    public GamePacket(WallPacket[] walls, PlayerPacket[] players, EnemyPacket[] enemys, Integer enemyMoveSeed){
        this.walls = walls;
        this.players = players;
        this.enemys = enemys;
        this.enemyMoveSeed = enemyMoveSeed;
    }
    
}