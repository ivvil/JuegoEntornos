package org.example;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection {

	private final String host;
	private final int port;

	private Socket socket;
	
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	public ServerConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public boolean connect(){
		try {
			if(!host.equals("localhost") && !host.matches("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b")){
				JOptionPane.showMessageDialog(null, "Host must be a valid IP address", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			// socket = new Socket(host, port);
			boolean io = false;
			boolean uh = false;

			// outputStream = new ObjectOutputStream(socket.getOutputStream());
			// inputStream = new ObjectInputStream(socket.getInputStream());
			if (io)
				throw new IOException("Connection error");
			if (uh)
				throw new UnknownHostException("Unknown Host error");
		}catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Unknown Host error", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection error", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	
	
}

