package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.database.DatabaseHelper;
import ru.klonwar.checkers.models.database.GameItem;
import ru.klonwar.checkers.models.database.UserPair;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InfoFrame extends JFrame {
    private JButton resumeButton;
    private JButton newGameButton;
    private JPanel body;
    private JPanel user1Info;
    private JPanel user2Info;
    private JPanel activeGames;
    private Runnable showGameFrame;
    private UserPair up;

    public InfoFrame(UserPair up) {
        this.up = up;

        this.setTitle("Шашки");
        this.setContentPane(body);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(800, 620);
        this.setResizable(true);
        this.setFocusable(true);
        this.setVisible(true);

        DatabaseHelper db = new DatabaseHelper();
        List<GameItem> gamesList =  db.getGamesForTwoUsers(up.getFirst().getId(), up.getSecond().getId());
    }

    public void setShowGameFrame(Runnable showGameFrame) {
        this.showGameFrame = showGameFrame;
    }
}
