package org.example.packets;

import java.io.Serializable;

public class CoinPacket implements Serializable{
    private int coinSeed;

    public CoinPacket(int seed) {
        this.coinSeed = seed;
    }

    public int getSeed() {
        return coinSeed;
    }
}
