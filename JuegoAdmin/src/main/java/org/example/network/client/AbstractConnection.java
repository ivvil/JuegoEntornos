package org.example.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.example.network.ConnectionStatus;
import org.example.network.ConnectionType;

public abstract class AbstractConnection implements Connection {
	protected final ConnectionType type;

	protected final String host;
	protected final int port;

	protected Socket socket;

	protected ObjectOutputStream outputStream;
	protected ObjectInputStream inputStream;

	public AbstractConnection(ConnectionType type, String host, int port) {
		this.type = type;
		this.host = host;
		this.port = port;
	}

	public static Connection createConnection(ConnectionType type, String host, int port) {
		switch (type) {
			case CLIENT:
				return new ClientConnection(host, port);
			case ADMIN:
				return new AdminConnection(host, port);
			default:
				throw new IllegalArgumentException("Unknown connection type " + type);
		}
	}

	protected ConnectionType getConnectionType() {
		return type;
	}

	protected void startConnection() throws UnknownHostException, IOException {
		socket = new Socket(host, port);

		outputStream = new ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());

		sendObj(new org.example.network.packets.Connection(type, ConnectionStatus.CONNECTED)); // Send a connected package to notify the server
	}

	protected void stopConnection() throws IOException {
		sendObj(new org.example.network.packets.Connection(type, ConnectionStatus.DISCONNECTED)); // Before closing down the connection we notify the server by sending it a disconected status
		
		outputStream.close();
		inputStream.close();

		socket.close();
	}

	protected ConnectionStatus checkConnection() {
		// TODO Implement actual connection diagnostics
		return ConnectionStatus.OK;
	}
	
    protected void sendObj(Object o) throws IOException {
		outputStream.writeObject(o);
	}
}
