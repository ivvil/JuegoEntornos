package org.example;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        if (System.getenv("__GLX_VENDOR_LIBRARY_NAME") == null || !System.getenv("__GLX_VENDOR_LIBRARY_NAME").equals("nvidia") || !System.getProperty("os.name").equals("Linux")) // If nvidia and linux
            System.setProperty("sun.java2d.opengl", "true");
            
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

            newGame(gameFrame, menuFrame, playerColor);
            gameFrame[0].setVisible(true);
        });

        multiPlayer.addActionListener(e -> {
            menuFrame.setVisible(false);
            JOptionPane.showMessageDialog(menuFrame, "Multiplayer is not implemented yet", "Error",
                    JOptionPane.ERROR_MESSAGE);
            menuFrame.setVisible(true);
        });

        quit.addActionListener(e -> menuFrame.dispatchEvent(new WindowEvent(menuFrame, WindowEvent.WINDOW_CLOSING)));

        menuFrame.setVisible(true);

    }

    private static void newGame(JFrame[] gameFrame, JFrame menuFrame, Color playerColor) {
        if (gameFrame[0] != null)
            gameFrame[0].dispose();
        gameFrame[0] = new JFrame("Game");
        int frameWidth = 1900;
        int frameHeight = 1060;
        Game game = new Game(frameWidth, frameHeight, gameFrame[0], menuFrame);
        game.getPlayer().setBackground(playerColor);
        float[] hsb = Color.RGBtoHSB(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue(), null);

        if (hsb[2] < 0.5f)
            game.getPlayer().setForeground(new Color(255, 255, 255));
        else
            game.getPlayer().setForeground(new Color(0, 0, 0));

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
}