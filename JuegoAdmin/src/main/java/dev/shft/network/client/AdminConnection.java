package dev.shft.network.client;

import java.io.IOException;
import java.net.UnknownHostException;

import dev.shft.network.ConnectionType;

public class AdminConnection extends AbstractConnection {

	public AdminConnection(String host, int port) {
		super(ConnectionType.ADMIN, host, port);
	}

	@Override
	public void initConnection() throws UnknownHostException, IOException {
		startConnection();
	}

	@Override
	public void closeConnection() throws IOException {
		// TODO Send a packet to tell the server to clear our connection
		
		stopConnection();
	}

	@Override
	public void startListener() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'startListener'");
	}
	
}
