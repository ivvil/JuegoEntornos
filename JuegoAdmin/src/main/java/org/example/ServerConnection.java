package org.example;

import org.example.packets.admin.IAmAnAdminPacket;
import org.example.packets.admin.StartGamePacket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;

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
							ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
							currentPlayers = ois.readInt();
							updateInfo();
							ois.close();
						}catch (StreamCorruptedException e) {
							currentPlayers++;
							updateInfo();
						}catch (IOException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error while reading object", "Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("Error while reading object: " + e.getMessage());
						}

					}
			}).start();

		} catch (UnknownHostException e) {
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
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		JPanel panel = new JPanel(new BorderLayout());
		frame.add(panel);

		JLabel title = new JLabel("Server info");
		panel.add(title);
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setBounds(200, 20, 430, 100);
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		label = new JLabel("Players: " + currentPlayers + "/" + maxPlayers);
		panel.add(label);
		Font font = new Font("Arial", Font.PLAIN, 24);
		FontMetrics metrics = panel.getFontMetrics(font);
		int textWidth = metrics.stringWidth("Players: " + currentPlayers + "/" + maxPlayers);
		int frameWidth = frame.getWidth();
		int x = (frameWidth - textWidth) / 2;
		label.setFont(font);
		label.setBounds(x, 100, 430, 100);

		JButton startGame = new JButton("Start game");
		panel.add(startGame);
		startGame.setFont(new Font("Arial", Font.BOLD, 22));
		startGame.setBounds(50, 250, 400, 50);

		JButton stopServer = new JButton("Shutdown server");
		panel.add(stopServer);
		stopServer.setFont(new Font("Arial", Font.BOLD, 22));
		stopServer.setBounds(100, 300, 400, 100);
		

		startGame.addActionListener(evt -> {
				try {
					objOut.writeObject(new StartGamePacket());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error while sending object", "Error", JOptionPane.ERROR_MESSAGE);
				}
			});


		JLabel dummy = new JLabel(" ");
		panel.add(dummy);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


	}
}

