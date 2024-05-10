package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import org.example.utils.ProgressDialog;
import org.example.utils.TextInput;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static ServerConnection serverConnection;

    public static void main(String[] args) {
		if (System.getProperty("os.name").equals("Linux") && System.getenv("__GLX_VENDOR_LIBRARY_NAME") != null && !System.getenv("__GLX_VENDOR_LIBRARY_NAME").equals("nvidia")) // If not nvidia and linux
            System.setProperty("sun.java2d.opengl", "True");
            
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JFrame window = new JFrame("Server browser");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 500);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout());
        window.add(panel);

        JLabel title = new JLabel("Select server");
        panel.add(title);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(170, 20, 400, 100);
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JTextField hostInput = new TextInput("Host");
		panel.add(hostInput);
		hostInput.setBounds(50, 150, 400, 50);

        JTextField portInput = new TextInput("Port");
        panel.add(portInput);
        portInput.setBounds(50, 250, 400, 50);

		JButton connect = new JButton("Connect");
		panel.add(connect);
		connect.setBounds(50, 350, 400, 50);

		panel.add(new JLabel(" "));

		connect.addActionListener(evt -> {
            String host = hostInput.getText();
            String port = portInput.getText();
            boolean result;
            ProgressDialog[] pd = new ProgressDialog[]{new ProgressDialog(window, "Connecting")};
            new Thread(() ->{
                pd[0].setVisible(true); // If this is not in another thread the program will freeze until the dialog is closed
            }).start();
            try {
                Thread.sleep(800); // Wait some time so we can appreciate the dialog
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                    serverConnection = new ServerConnection(host, Integer.parseInt(port));
                    result = serverConnection.connect();
                } catch (NumberFormatException e) {
                    pd[0].setVisible(false);
                    JOptionPane.showMessageDialog(null, "Port must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                    result = false;
                }
                if (!result) {
                    pd[0].setVisible(false);
                    window.requestFocus();
                    return;
                }

                pd[0].setVisible(false);
                System.out.println("Connected to server");
                window.setVisible(false);
                serverConnection.showWindow();
			});
		 
		window.setVisible(true);
    }
}
