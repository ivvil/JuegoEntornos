package org.example.packets;

public class CoinPacket extends Packet{
    private int coinSeed;

    public CoinPacket(int seed) {
        this.coinSeed = seed;
    }

    public int getSeed() {
        return coinSeed;
    }
}
