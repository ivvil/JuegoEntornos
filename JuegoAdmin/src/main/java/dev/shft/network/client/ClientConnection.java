package dev.shft.network.client;

import dev.shft.network.ConnectionType;

public class ClientConnection extends AbstractConnection {

	public ClientConnection(String host, int port) {
		super(ConnectionType.CLIENT, host, port);
	}

	@Override
	public void initConnection() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'initConnection'");
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'closeConnection'");
	}

	@Override
	public void startListener() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'startListener'");
	}
	
}
