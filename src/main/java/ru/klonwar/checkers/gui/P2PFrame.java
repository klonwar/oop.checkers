package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.helpers.Link;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.database.UserPair;
import ru.klonwar.checkers.models.game.Game;
import ru.klonwar.checkers.models.p2p.ConnectionState;

import javax.swing.*;
import java.awt.*;

public class P2PFrame extends JFrame {
    private JPanel body;

    public P2PFrame(CheckersDatabase db) {
        this.setTitle("Шашки P2P");
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

        ConnectionState connectionState = new ConnectionState();

        // Экраны
        ConnectionPanel cp;
        InfoPanel ip;
        Link<GamePanel> gpLink = new Link<>();
        P2PLoginPanel lp;

        ip = new InfoPanel(connectionState, db, () -> {
            if (gpLink.item != null && connectionState.opponentUser != null) {
                gpLink.item.setGame(new Game(connectionState, db));
                cl.next(body);
            }
        });

        cp = new ConnectionPanel(connectionState, db, () -> {
            if (gpLink.item != null && connectionState.opponentUser != null) {
                cl.next(body);
                body.remove(0);
            }
        });

        gpLink.item = new GamePanel( () -> {
            cl.next(body);
        });

        lp = new P2PLoginPanel(connectionState, db, () -> {
            if (connectionState.thisUser != null) {
                cl.next(body);
                body.remove(0);
            }
        });

        body.add(lp, "Login");
        body.add(cp, "Connection");
        body.add(ip, "Info");
        body.add(gpLink.item, "Game");
    }
}
