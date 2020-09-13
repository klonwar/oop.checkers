package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.graphics.FieldController;
import ru.klonwar.checkers.helpers.geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldJPanel extends JPanel {
    private FieldController fieldController = new FieldController();

    public FieldJPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                fieldController.onClick(new Point(e.getX(), e.getY()));

                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();

                repaint();
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    fieldController.clearSuggestion();
                    repaint();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        fieldController.paint(Math.min(getWidth(), getHeight()), (Graphics2D) g);
    }

    public void restart() {
        fieldController = new FieldController();
        repaint();
    }
}
