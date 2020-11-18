package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.UserPair;
import ru.klonwar.checkers.models.game.Checker;
import ru.klonwar.checkers.models.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {
    private final FieldJPanel field;

    public GamePanel(Runnable switchCards) {
        setLayout(new CardLayout());

        JPanel fieldContainer = new JPanel();
        fieldContainer.setLayout(new CardLayout());

        add(fieldContainer);

        field = new FieldJPanel(switchCards);

        fieldContainer.add(field);
    }

    public void setGame(Game game) {
        field.setGame(game);
    }
}
