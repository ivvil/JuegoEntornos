package org.example;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.UIManager;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = (int) size.getWidth() - 100;
        int frameHeight = (int) size.getHeight() - 100;
        frame.add(new Game(frameWidth, frameHeight, frame));
        frame.setSize(frameWidth, frameHeight);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}