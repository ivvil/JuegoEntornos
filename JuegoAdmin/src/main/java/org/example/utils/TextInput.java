package org.example.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.FocusManager;
import javax.swing.JTextField;

public class TextInput extends JTextField {

    private String placeholder;

    public TextInput(String placeholder) {
        this.placeholder = placeholder;
        setFont(new Font("Arial", Font.PLAIN, 24)); // set font size to match your needs
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(getFont());
            FontMetrics metrics = g2.getFontMetrics();
            int x = 5;
            int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            g2.setColor(Color.gray);
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }
}

