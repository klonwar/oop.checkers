package ru.klonwar.checkers.gui;

import javax.swing.*;

public class MainFrame extends JFrame {

    private JPanel body;
    private JButton restartButton;
    private JButton button2;
    private JPanel fieldContainer;
    private JLabel playerName;

    public MainFrame() {
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

        restartButton.addActionListener(e -> {
            field.restart();
        });
    }
}
