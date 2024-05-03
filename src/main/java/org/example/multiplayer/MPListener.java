package org.example.multiplayer;

import org.example.packets.CoinPacket;
import org.example.packets.EnemyPacket;
import org.example.packets.LevelPacket;
import org.example.packets.PlayerPacket;

import java.util.EventListener;

public interface MPListener extends EventListener {
    void onConnect();

    void onDisconnect();

    void onMessage(String message);

    void onEnemyPacket(EnemyPacket packet);

    void onCoinPacket(CoinPacket packet);

    void onLevelPacket(LevelPacket packet);

    void onPlayerPacket(PlayerPacket packet);
}
