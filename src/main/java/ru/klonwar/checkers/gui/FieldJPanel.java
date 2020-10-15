package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.helpers.geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldJPanel extends JPanel {
    private JPanelController jpanelController = new JPanelController();

    public FieldJPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jpanelController.onClick(new Point(e.getX(), e.getY()));
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    jpanelController.clearHints();
                    repaint();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        jpanelController.paint(g2d, Math.min(getWidth(), getHeight()));
    }

    public void restart() {
        jpanelController = new JPanelController();
        repaint();
    }
}
