package ru.klonwar.checkers.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {

    public GamePanel() {
        setLayout(new CardLayout());

        JPanel fieldContainer = new JPanel();
        fieldContainer.setLayout(new CardLayout());

        add(fieldContainer);

        FieldJPanel field = new FieldJPanel();

        fieldContainer.add(field);
    }
}
