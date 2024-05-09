package dev.shft.network.packets;

import dev.shft.network.ConnectionStatus;
import dev.shft.network.ConnectionType;

public class Connection {
	public final ConnectionType type;
	public final ConnectionStatus intent;

	public Connection(ConnectionType type, ConnectionStatus intent) {
		this.type = type;
		this.intent = intent;

	}
}
