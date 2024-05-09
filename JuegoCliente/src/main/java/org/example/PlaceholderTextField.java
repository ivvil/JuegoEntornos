package org.example;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {

    private String placeholder;
    private boolean boldFont = false;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setBoldFont(boolean boldFont) {
        this.boldFont = boldFont;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.isEmpty() || !getText().isEmpty()) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        int textWidth = g.getFontMetrics().stringWidth(placeholder);
        int componentWidth = getWidth();
        int textHeight = g.getFontMetrics().getHeight();
        int componentHeight = getHeight();
        int y = (componentHeight - textHeight) / 2 + g.getFontMetrics().getAscent();
        int x = (componentWidth - textWidth) / 2;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        if (boldFont) {
            g.setFont(g.getFont().deriveFont(g.getFont().getStyle() | java.awt.Font.BOLD));
        }
        g.drawString(placeholder, x, y);
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}
