package dev.shft;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import dev.shft.utils.ProgressDialog;
import dev.shft.utils.TextInput;

public class Main {
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

		JButton connect = new JButton("Connect");
		panel.add(connect);
		connect.setBounds(50, 250, 400, 50);

		panel.add(new JLabel(" "));

		connect.addActionListener(evt -> {
				ProgressDialog pd = new ProgressDialog(window, "Connecting");
				pd.setVisible(true);
			});
		 
		window.setVisible(true);
    }
}
