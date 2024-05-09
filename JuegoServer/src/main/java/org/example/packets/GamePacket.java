package org.example.packets;

import java.io.Serializable;

public class GamePacket implements Serializable{
    private final WallPacket[] walls;
    private final PlayerPacket[] players;
    //private final EnemyPacket[] enemys;
    private final Integer enemyMoveSeed;


    public GamePacket(WallPacket[] walls, Integer enemyMoveSeed){
        this.walls = walls;
        this.players = new PlayerPacket[0];
        //this.enemys = new EnemyPacket[0];
        this.enemyMoveSeed = enemyMoveSeed;
    }

    public void addPlayer(PlayerPacket player){
        PlayerPacket[] newPlayers = new PlayerPacket[players.length + 1];
        for (int i = 0; i < players.length; i++) {
            newPlayers[i] = players[i];
        }
        newPlayers[players.length] = player;
    }
    
}