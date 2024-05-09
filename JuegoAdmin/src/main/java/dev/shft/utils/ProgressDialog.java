package dev.shft.utils;

import javax.swing.*;
import java.awt.*;

public class ProgressDialog extends JDialog {

    private JProgressBar progressBar;
	private JLabel label;
    private JLabel spinnerLabel;

    public ProgressDialog(Frame owner, String text) {
        super(owner);
        setTitle("Progress");
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(owner);

		label = new JLabel(text);
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        spinnerLabel = new JLabel(new SpinnerIcon());

        JPanel panel = new JPanel();
		panel.add(label);
        panel.add(progressBar);
        panel.add(spinnerLabel);

        add(panel);
    }

    private class SpinnerIcon implements Icon {

        private final int size;
        private final int[] angles;
        private int index;

        public SpinnerIcon() {
            size = 16;
            angles = new int[] { 0, 90, 180, 270 };
            index = 0;

            Timer timer = new Timer(100, (e) -> {
                index = (index + 1) % angles.length;
                repaint();
            });
            timer.start();
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x + size / 2, y + size / 2);
            g2.rotate(Math.toRadians(angles[index]));
            g2.fillOval(-size / 2, -size / 2, size, size);
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }
}
