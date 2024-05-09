package dev.shft.network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public interface Connection {
	void initConnection();
	void closeConnection();
	void startListener();
}