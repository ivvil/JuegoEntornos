package dev.shft.network.packets;

import dev.shft.network.ConnectionType;

public class Connection {
	public final ConnectionType intent;

	public Connection(ConnectionType intent) {
		this.intent = intent;
	}
}
