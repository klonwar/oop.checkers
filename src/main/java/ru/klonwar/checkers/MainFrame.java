package ru.klonwar.checkers;

import ru.klonwar.checkers.field.FieldJPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel body;
    private JButton button1;
    private JButton button2;
    private JPanel fieldContainer;
    private JLabel playerName;

    MainFrame() {
        this.setTitle("Шашки");
        this.setContentPane(body);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(800, 620);
        this.setResizable(true);
        this.setFocusable(true);
        this.setVisible(true);

        FieldJPanel field = new FieldJPanel();
        fieldContainer.add(field);
    }
}
