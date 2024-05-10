package org.example.network.client;

import static org.example.network.packets.Admin.Action.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.example.network.ConnectionType;
import org.example.network.packets.Admin;

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
		stopConnection();
	}

	@Override
	public void startListener() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'startListener'");
	}

	public void startGame() throws IOException {
		sendObj(new Admin(START_GAME));
	}

	public void stopGame() throws IOException {
		sendObj(new Admin(STOP_GAME));
	}

	public void restartGame() throws IOException {
		sendObj(RESTART_GAME);
	}

	public void shutdownServer() throws IOException {
		sendObj(SHUTDOWN_SERVER);
	}
	
}
