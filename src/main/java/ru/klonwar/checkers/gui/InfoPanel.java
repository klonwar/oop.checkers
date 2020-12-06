package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.GameInfo;
import ru.klonwar.checkers.models.database.UserPair;
import ru.klonwar.checkers.models.p2p.ConnectionState;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InfoPanel extends JPanel {
    JButton startGameButton = new JButton("Новая игра");
    JTextArea user1Info = new JTextArea();
    JTextArea user2Info = new JTextArea();
    UserPair up;
    private final CheckersDatabase db;

    public InfoPanel(ConnectionState cs, CheckersDatabase db, Runnable switchCards) {
        this.db = db;
        setLayout(new GridLayout(0, 3, 10, 10));
        this.up = up;

        // User 1 info
        JPanel u1Panel = new JPanel();
        u1Panel.setLayout(new CardLayout());

        user1Info.setLineWrap(true);
        u1Panel.add(user1Info);

        // User 2 info
        JPanel u2Panel = new JPanel();
        u2Panel.setLayout(new CardLayout());

        user2Info.setLineWrap(true);
        u2Panel.add(user2Info);

        // Консоль
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setLayout(new GridLayout(8, 0, 10, 10));
        consolePanel.add(new JLabel("Действия", JLabel.CENTER));
        consolePanel.add(startGameButton);

        add(u1Panel);
        add(u2Panel);
        add(consolePanel);

        startGameButton.addActionListener((e) -> {
            SwingUtilities.invokeLater(switchCards);
        });

    }

    public void showInfoFromDB() {
        List<GameInfo> u1GamesList = db.getGamesInfoForUserID(up.getFirst().getId());
        List<GameInfo> u2GamesList = db.getGamesInfoForUserID(up.getSecond().getId());

        user1Info.setText(u1GamesList.toString());
        user2Info.setText(u2GamesList.toString());
    }
}
