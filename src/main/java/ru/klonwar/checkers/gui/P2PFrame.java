package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.game.server.GuestServer;
import ru.klonwar.checkers.models.game.server.HostServer;
import ru.klonwar.checkers.models.game.server.SocketServer;
import ru.klonwar.checkers.util.Link;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.p2p.ClientType;
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
            if (gpLink.item != null && connectionState.getOpponentUser() != null) {
                if (connectionState.getThisType() == ClientType.HOST) {
                    HostServer hs = new HostServer(connectionState, db);
                    hs.connect();
                    gpLink.item.setGameServer(hs);
                } else {
                    GuestServer gs = new GuestServer(connectionState, db);
                    gs.connect();
                    gpLink.item.setGameServer(gs);
                }
                cl.next(body);
            }
        });

        cp = new ConnectionPanel(connectionState, db, () -> {
            if (gpLink.item != null && connectionState.getOpponentUser() != null) {
                if (connectionState.getThisType() == ClientType.HOST) {
                    this.setTitle("Шашки P2P (Host)");
                } else {
                    this.setTitle("Шашки P2P (Guest)");
                }
                ip.showInfoFromDB();
                cl.next(body);
                body.remove(0);
            }
        });

        gpLink.item = new GamePanel(() -> {
            ip.showInfoFromDB();
            cl.next(body);
        });

        lp = new P2PLoginPanel(connectionState, db, () -> {
            if (connectionState.getThisUser() != null) {
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
