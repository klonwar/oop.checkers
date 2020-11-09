package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.database.UserPair;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel body;
    private Timer loginListener;

    public MainFrame() {
        this.setTitle("Шашки");
        this.setContentPane(body);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(800, 620);
        this.setResizable(true);
        this.setFocusable(true);
        this.setVisible(true);

        body.setBackground(Color.white);
        CardLayout cl = new CardLayout();
        body.setLayout(cl);

        UserPair up = new UserPair(null, null);

        body.add(new LoginPanel(up), "Login");
        body.add(new GamePanel(), "Game");

        loginListener = new Timer(10, (e) -> {
            if (!up.equals(UserPair.NULL_PAIR)) {
                cl.next(body);
                body.remove(0);
                loginListener.stop();
            }
        });
        loginListener.start();
    }
}
