package org.example.packets;

public class GamePacket{
    private final WallPacket[] walls;
    private PlayerPacket[] players;
    //private final EnemyPacket[] enemys;
    private final Integer enemyMoveSeed;


    public GamePacket(WallPacket[] walls,/*EnemyPacket[] enemys,*/ Integer enemyMoveSeed){
        this.walls = walls;
        this.players = new PlayerPacket[0];
        // this.enemys = enemys;
        this.enemyMoveSeed = enemyMoveSeed;
    }

    public WallPacket[] getWalls(){
        return walls;
    }

    public PlayerPacket[] getPlayers(){
        return players;
    }

    // public EnemyPacket[] getEnemys(){
    //     return enemys;
    // }

    public Integer getEnemyMoveSeed(){
        return enemyMoveSeed;
    }

    public void addPlayer(PlayerPacket player){
        PlayerPacket[] newPlayers = new PlayerPacket[players.length + 1];
        System.arraycopy(players, 0, newPlayers, 0, players.length);
        newPlayers[players.length] = player;
        this.players = newPlayers;

    }
    
}