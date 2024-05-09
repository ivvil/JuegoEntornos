package org.example.network.packets;

import org.example.network.ConnectionStatus;
import org.example.network.ConnectionType;

public class Connection {
	public final ConnectionType type;
	public final ConnectionStatus intent;

	public Connection(ConnectionType type, ConnectionStatus intent) {
		this.type = type;
		this.intent = intent;

	}
}
