package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.helpers.Link;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.SQLDatabase;
import ru.klonwar.checkers.models.database.UserPair;
import ru.klonwar.checkers.models.game.Game;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel body;

    public MainFrame(CheckersDatabase db) {
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

        InfoPanel ip;
        Link<GamePanel> gpLink = new Link<>();
        LoginPanel lp;

        ip = new InfoPanel(up, db, () -> {
            if (gpLink.item != null) {
                gpLink.item.setGame(new Game(up.getFirst(), up.getSecond(), db));
                cl.next(body);
            }
        });

        gpLink.item = new GamePanel( () -> {
            ip.showInfoFromDB();
            cl.next(body);
        });

        lp = new LoginPanel(up, db, () -> {
            if (!up.equals(UserPair.NULL_PAIR)) {
                ip.showInfoFromDB();
                cl.next(body);
                body.remove(0);
            }
        });

        body.add(lp, "Login");
        body.add(ip, "Info");
        body.add(gpLink.item, "Game");
    }


}
