package org.example.packets.client;

import org.example.packets.Packet;

public class CoinPacket extends Packet{
    private int coinSeed;

    public CoinPacket(int seed) {
        this.coinSeed = seed;
    }

    public int getSeed() {
        return coinSeed;
    }
}
