package org.example;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.example.packets.admin.IAmAnAdminPacket;
import org.example.packets.admin.StartGamePacket;

public class ServerConnection {

	private final String host;
	private final int port;

	private Socket socket;
	private int currentPlayers;
	private int maxPlayers;
	
	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;

	private JFrame frame;
	private JLabel label;
	
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
			socket = new Socket(host, port);

			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());

			objOut.writeObject(new IAmAnAdminPacket());

			String data = (String) objIn.readObject();
			String[] split = data.split("\\|");
			currentPlayers = Integer.parseInt(split[0]);
			maxPlayers = Integer.parseInt(split[1]);

			new Thread(() -> {
				while(true){
					try {
						Object obj = objIn.readObject();
						if(obj instanceof Integer){
							currentPlayers = (int) obj;
							updateInfo();
						}
					} catch (IOException | ClassNotFoundException e) {
						JOptionPane.showMessageDialog(null, "Error while reading object", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}).start();

		}catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Unknown Host error", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection error", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Class not found error", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
        }
        return true;
	}

	public void updateInfo(){
		label.setText("Players: " + currentPlayers + "/" + maxPlayers);
		label.repaint();
	}

	public void showWindow(){
		frame = new JFrame("Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		JPanel panel = new JPanel(new BorderLayout());
		frame.add(panel);

		JLabel title = new JLabel("Server info");
		panel.add(title);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setBounds(170, 20, 400, 100);
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		label = new JLabel("Players: " + currentPlayers + "/" + maxPlayers);
		panel.add(label);
		label.setBounds(50, 150, 400, 50);

		JButton startGame = new JButton("Start game");
		panel.add(startGame);
		startGame.setBounds(50, 250, 400, 50);

		startGame.addActionListener(evt -> {
			try {
				objOut.writeObject(new StartGamePacket());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error while sending object", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});


		JLabel dummy = new JLabel(" ");
		panel.add(dummy);


		frame.setVisible(true);


	}
}

