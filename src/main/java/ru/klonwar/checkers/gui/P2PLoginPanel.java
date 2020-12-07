package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.QueryResponse;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.p2p.ConnectionState;

import javax.swing.*;
import java.awt.*;

public class P2PLoginPanel extends JPanel {
    private final JTextField user1_login = new JTextField("login1");
    private final JTextField user1_password = new JTextField("password");
    private final JButton loginButton = new JButton("Вход");
    private final JTextField register_login = new JTextField();
    private final JTextField register_password = new JTextField();
    private final JTextArea consoleOutput = new JTextArea();

    public P2PLoginPanel(ConnectionState connectionState, CheckersDatabase db, Runnable switchToInfo) {
        setLayout(new GridLayout(0, 3, 10, 10));

        // Вход
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(8, 0, 10, 10));

        loginPanel.add(new JLabel("Вход", JLabel.CENTER));
        loginPanel.add(user1_login);
        loginPanel.add(user1_password);
        loginPanel.add(loginButton);

        // Регистрация
        JPanel regPanel = new JPanel();
        regPanel.setLayout(new GridLayout(8, 0, 10, 10));

        regPanel.add(new JLabel("Регистриация", JLabel.CENTER));
        regPanel.add(register_login);
        regPanel.add(register_password);
        JButton registerButton = new JButton("Зарегистрироваться");
        regPanel.add(registerButton);

        // Консоль
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setBackground(new Color(38, 50, 56));

        consoleOutput.setBackground(new Color(38, 50, 56));
        consoleOutput.setForeground(new Color(76, 175, 80));
        consoleOutput.setFont(new Font("monospace", Font.PLAIN, 14));
        consoleOutput.setLineWrap(true);
        consolePanel.add(consoleOutput, BorderLayout.SOUTH);

        add(loginPanel);
        add(regPanel);
        add(consolePanel);

        loginButton.addActionListener((e) -> {
            String login1 = user1_login.getText();
            String password1 = user1_password.getText();

            User user = db.getUserByLoginAndPassword(login1, password1);

            if (user == null) {
                String[] logStr = new String[2];
                logStr[0] = "Данные пользователя не верны";
                log(logStr);
                return;
            }

            loginButton.setEnabled(false);
            connectionState.setThisUser(user);
            SwingUtilities.invokeLater(switchToInfo);
        });

        registerButton.addActionListener((e) -> {
            String login = register_login.getText();
            String password = register_password.getText();
            if (login == null || login.length() < 3) {
                log("Слишком короткий логин");
                return;
            }

            if (password == null || password.length() < 3) {
                log("Слишком короткий пароль");
                return;
            }

            QueryResponse res = db.addUser(new User(-1, login, password));
            log(res.getMessage());
        });
    }

    private void log(String text) {
        consoleOutput.setText("> " + text + "\n");
    }

    private void log(String[] text) {
        StringBuilder output = new StringBuilder();
        for (String item : text) {
            if (item != null)
                output.append("> ").append(item).append("\n");
        }
        consoleOutput.setText(output.toString());
    }

}
