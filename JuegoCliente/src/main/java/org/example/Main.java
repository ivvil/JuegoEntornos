package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import org.example.multiplayer.MPGame;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {

        if ((System.getProperty("os.name").equals("Linux") && System.getenv("__GLX_VENDOR_LIBRARY_NAME") == null) || !System.getenv("__GLX_VENDOR_LIBRARY_NAME").equals("nvidia")) // If not nvidia and linux
            System.setProperty("sun.java2d.opengl", "True");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JFrame menuFrame = new JFrame("Menu");

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(800, 500);
        menuFrame.setResizable(false);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setUndecorated(true);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuFrame.add(menuPanel);

        JLabel title = new JLabel("Best Java Swing Game");
        menuPanel.add(title);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(170, 20, 500, 100);
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JButton singlePlayer = new JButton("Single Player");
        menuPanel.add(singlePlayer);
        singlePlayer.setBounds(210, 180, 350, 60);
        singlePlayer.setFont(new Font("Arial", Font.BOLD, 18));

        JButton multiPlayer = new JButton("Multi Player");
        menuPanel.add(multiPlayer);
        multiPlayer.setBounds(210, 270, 350, 60);
        multiPlayer.setFont(new Font("Arial", Font.BOLD, 18));

        JButton quit = new JButton("Quit");
        menuPanel.add(quit);
        quit.setBounds(210, 360, 350, 60);
        quit.setFont(new Font("Arial", Font.BOLD, 18));

        menuPanel.add(new JLabel(" ")); // Dummy

        JFrame[] gameFrame = new JFrame[1];

        singlePlayer.addActionListener(e -> {
            menuFrame.setVisible(false);

            Color playerColor = JColorChooser.showDialog(menuFrame, "Select a player color", new Color(51, 153, 255));
            String playerName = JOptionPane.showInputDialog(menuFrame, "Enter your name", "Player");

            newGame(gameFrame, menuFrame, playerColor, playerName);
            gameFrame[0].setVisible(true);
        });

        multiPlayer.addActionListener(e -> {
            menuFrame.setVisible(false);

            JOptionPane.showMessageDialog(menuFrame, "This feature is not implemented yet", "Error", JOptionPane.ERROR_MESSAGE);

            menuFrame.setVisible(true);

            // newMultiPlayer(menuFrame);
        });

        quit.addActionListener(e -> menuFrame.dispatchEvent(new WindowEvent(menuFrame, WindowEvent.WINDOW_CLOSING)));

        menuFrame.setVisible(true);

    }

    private static void newGame(JFrame[] gameFrame, JFrame menuFrame, Color playerColor, String name) {
        if (gameFrame[0] != null)
            gameFrame[0].dispose();
        gameFrame[0] = new JFrame("Game");
        int frameWidth = 1900;
        int frameHeight = 1060;
        Game game = new Game(frameWidth, frameHeight, gameFrame[0], menuFrame, (int) (Math.random() * 1000) + 1, (int) (Math.random() * 1000) + 1);
        game.getPlayer().setBackground(playerColor);
        float[] hsb = Color.RGBtoHSB(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue(), null);

        if (hsb[2] < 0.5f)
            game.getPlayer().setForeground(new Color(255, 255, 255));
        else
            game.getPlayer().setForeground(new Color(0, 0, 0));

        game.getPlayer().setName(name);

        gameFrame[0].add(game);
        gameFrame[0].setSize(frameWidth, frameHeight);
        gameFrame[0].setUndecorated(true);
        gameFrame[0].setResizable(false);
        gameFrame[0].setLocationRelativeTo(null);

        gameFrame[0].addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuFrame.setVisible(true);
                gameFrame[0].setVisible(false);
                gameFrame[0].dispose();
                gameFrame[0] = null;
            }
        });
    }

    private static void newMultiPlayer(JFrame menuFrame) {
        int frameWidth = 1900;
        int frameHeight = 1060;

        JFrame[] gameFrame = new JFrame[1];
        gameFrame[0] = new JFrame("Game");
        gameFrame[0].setVisible(false);
        gameFrame[0].setUndecorated(true);
        gameFrame[0].setResizable(false);
        gameFrame[0].setSize(frameWidth, frameHeight);

        JFrame hostSelector = new JFrame();
        hostSelector.setSize(800, 500);
        hostSelector.setResizable(false);
        hostSelector.setUndecorated(true);
        JPanel hostPanel = new JPanel(new BorderLayout());
        hostSelector.add(hostPanel);

        JLabel title = new JLabel("Enter the server details");
        hostPanel.add(title);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(170, 20, 500, 100);

        PlaceholderTextField host = new PlaceholderTextField("Enter host ip");
        hostPanel.add(host);
        host.setBounds(210, 180, 350, 60);
        host.setBoldFont(true);

        PlaceholderTextField port = new PlaceholderTextField("Enter port");
        hostPanel.add(port);
        port.setBounds(210, 270, 350, 60);
        port.setBoldFont(true);

        JButton hostButton = new JButton("Join");
        hostPanel.add(hostButton);
        hostButton.setBounds(210, 360, 350, 60);
        hostButton.setFont(new Font("Arial", Font.BOLD, 18));
        String[] connection = new String[2];
        MPGame[] game = new MPGame[1];
        hostButton.addActionListener(e -> {
            connection[0] = host.getText();
            connection[1] = port.getText();
            int pnum = 6942;
            if (connection[0].isBlank() || connection[1].isBlank()) {
                JOptionPane.showMessageDialog(hostSelector, "Please enter valid details");
                System.exit(1);
            }
            if (!connection[0].equals("localhost") && !connection[0].matches("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b")) {
                JOptionPane.showMessageDialog(hostSelector, "Invalid ip address", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            try {
                pnum = Integer.parseInt(connection[1]);
                if (pnum < 0 || pnum > 0xFFFF) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(hostSelector, "Invalid port number", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            hostSelector.setVisible(false);
            Color playerColor = JColorChooser.showDialog(hostSelector, "Select a player color", new Color(51, 153, 255));
            game[0] = new MPGame(connection[0], pnum, playerColor.getRGB(), gameFrame[0]);
        });


        hostSelector.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameFrame[0].setVisible(false);

                if (gameFrame[0] != null) {
                    gameFrame[0].dispose();
                    gameFrame[0] = null;
                }

                if (game[0] != null)
                    game[0] = null;

                menuFrame.setVisible(true);
                System.gc();
            }
        });

        gameFrame[0].addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuFrame.setVisible(true);
                gameFrame[0].setVisible(false);
                if (gameFrame[0] != null) {
                    gameFrame[0].dispose();
                    gameFrame[0] = null;
                }
                if (game[0] != null)
                    game[0] = null;
                menuFrame.setVisible(true);
                System.gc();
            }
        });

        hostPanel.add(new JLabel(" ")); // Dummy
        hostSelector.setLocationRelativeTo(null);
        hostSelector.setVisible(true);

    }
}