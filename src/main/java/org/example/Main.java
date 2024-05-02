package org.example;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int[] screenSize = getScreenSize();
        screenSize[1] -= 50;
        frame.add(new Game(screenSize[0], screenSize[1]));
        frame.setUndecorated(true);
        frame.setSize(screenSize[0], screenSize[1]);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static int[] getScreenSize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        return new int[]{(int) Math.round(width), (int) Math.round(height)};
    }
}