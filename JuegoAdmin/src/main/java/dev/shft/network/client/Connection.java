package dev.shft.network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public interface Connection {
	void initConnection() throws UnknownHostException, IOException;
	void closeConnection() throws IOException;
	void startListener();

}
