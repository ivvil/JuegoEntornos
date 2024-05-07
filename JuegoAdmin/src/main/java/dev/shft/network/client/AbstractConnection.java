package dev.shft.network.client;

import dev.shft.network.ConnectionStatus;
import dev.shft.network.ConnectionType;

public abstract class AbstractConnection implements Connection {
	protected final ConnectionType type;

	public AbstractConnection(ConnectionType type) {
		this.type = type;
	}

	public static Connection createConnection(ConnectionType type) {
		switch (type) {
			case CLIENT:
				// return new;
			case ADMIN:
				// retutn new 
			default:
				throw new IllegalArgumentException("Unknown connection type " + type);
		}
	}

	protected ConnectionType getConnectionType() {
		return type;
	}

	protected ConnectionStatus checkConnection() {
		// TODO Implement actual connection diagnostics
		return ConnectionStatus.OK;
	}
}
