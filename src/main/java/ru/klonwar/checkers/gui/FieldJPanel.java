package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldJPanel extends JPanel {
    private final GameGraphics jpanelGraphics;

    public FieldJPanel(Runnable switchCards) {
        jpanelGraphics = new GameGraphics();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jpanelGraphics.onClick(new Point(e.getX(), e.getY()));
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
                    jpanelGraphics.clearHints();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_R && e.isControlDown()) {
                    restart();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_F1) {
                    SwingUtilities.invokeLater(switchCards);
                }
            }
        });

        requestFocusInWindow();
    }

    public void setGame(Game game) {
        jpanelGraphics.setGame(game);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        jpanelGraphics.paint(g2d, Math.min(getWidth(), getHeight()));
    }

    public void restart() {
        jpanelGraphics.restart();
        repaint();
    }
}
