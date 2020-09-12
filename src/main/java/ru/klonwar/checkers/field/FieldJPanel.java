package ru.klonwar.checkers.field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldJPanel extends JPanel {
    private final FieldController fieldController = new FieldController();

    public FieldJPanel() {
        // todo обработка кликов
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        fieldController.setGraphics((Graphics2D) g);
        fieldController.paint(Math.min(getWidth(), getHeight()), (Graphics2D) g);
    }
}
