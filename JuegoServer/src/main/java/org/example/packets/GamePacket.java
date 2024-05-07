package org.example.packets;

public class GamePacket extends Packet{
    private final WallPacket[] walls;
    private final PlayerPacket[] players;
    private final Integer enemyMoveSeed;
    private final Integer coinSeed;


    public GamePacket(WallPacket[] walls, Integer enemyMoveSeed, Integer coinSeed){
        this.walls = walls;
        this.players = new PlayerPacket[0];
        this.enemyMoveSeed = enemyMoveSeed;
        this.coinSeed = coinSeed;
    }

    public void addPlayer(PlayerPacket player){
        PlayerPacket[] newPlayers = new PlayerPacket[players.length + 1];
        for (int i = 0; i < players.length; i++) {
            newPlayers[i] = players[i];
        }
        newPlayers[players.length] = player;
    }

    public WallPacket[] getWalls() {
        return walls;
    }

    public PlayerPacket[] getPlayers() {
        return players;
    }

    public Integer getEnemyMoveSeed() {
        return enemyMoveSeed;
    }

    public Integer getCoinSeed() {
        return coinSeed;
    }
    
}