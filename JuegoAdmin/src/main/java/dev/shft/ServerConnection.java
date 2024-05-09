package dev.shft;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection {

	private final String address;
	private final int port;

	private Socket socket;
	
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	public ServerConnection(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(address, port);
		
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());
	}

	
	
}

