package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.GamerInfo;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.p2p.ConnectionState;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InfoPanel extends JPanel {
    JButton startGameButton = new JButton("Новая игра");
    JPanel u1Panel;
    JPanel u2Panel;
    private final CheckersDatabase db;
    private final ConnectionState cs;

    public InfoPanel(ConnectionState cs, CheckersDatabase db, Runnable switchCards) {
        this.db = db;
        this.cs = cs;
        setLayout(new GridLayout(0, 3, 10, 10));

        // User 1 info
        u1Panel = new JPanel();
        u1Panel.setLayout(new GridLayout(8, 0, 10, 10));

        // Top list
        u2Panel = new JPanel();
        u2Panel.setLayout(new GridLayout(8, 0, 10, 10));

        u2Panel.add(new JLabel("Топ игроков"));

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
        List<GamerInfo> topUsersList = db.getTopUsers();

        u2Panel.removeAll();
        u2Panel.add(new JLabel("Топ игроков"));

        for (int i = 1; i <= 7 && i < topUsersList.size(); i++) {
            GamerInfo item = topUsersList.get(i - 1);
            u2Panel.add(new JLabel("\"" + item.getLogin() + "\". Побед: " + item.getWins()), i);
        }


    }
}
