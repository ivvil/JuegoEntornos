package dev.shft;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerConnection {

	private final String address;
	private final int port;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	
	public ServerConnection(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void connect() {
		
	}
	
}

