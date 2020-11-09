package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.models.database.DatabaseHelper;
import ru.klonwar.checkers.models.database.UserItem;
import ru.klonwar.checkers.models.database.UserPair;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class LoginPanel extends JPanel {
    private JTextField user1_login = new JTextField("login1");
    private JTextField user1_password = new JTextField("password");
    private JButton loginButton = new JButton("Вход");
    private JTextField user2_login = new JTextField("login2");
    private JTextField user2_password = new JTextField("password");
    private JTextField register_login = new JTextField();
    private JTextField register_password = new JTextField();
    private JTextArea consoleOutput = new JTextArea();
    private JButton registerButton = new JButton("Зарегистрироваться");

    public LoginPanel(UserPair up) {
        setLayout(new GridLayout(0, 3, 10, 10));

        // Вход
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(8, 0, 10, 10));

        loginPanel.add(new JLabel("Вход", JLabel.CENTER));
        loginPanel.add(new JLabel("User 1"));
        loginPanel.add(user1_login);
        loginPanel.add(user1_password);

        loginPanel.add(new JLabel("User 2"));
        loginPanel.add(user2_login);
        loginPanel.add(user2_password);
        loginPanel.add(loginButton);

        // Регистрация
        JPanel regPanel = new JPanel();
        regPanel.setLayout(new GridLayout(8, 0, 10, 10));

        regPanel.add(new JLabel("Регистриация", JLabel.CENTER));
        regPanel.add(register_login);
        regPanel.add(register_password);
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

        DatabaseHelper db = new DatabaseHelper();

        loginButton.addActionListener((e) -> {
            String login1 = user1_login.getText();
            String password1 = user1_password.getText();

            String login2 = user2_login.getText();
            String password2 = user2_password.getText();

            UserItem user1 = db.getUserByLoginAndPassword(login1, password1);
            UserItem user2 = db.getUserByLoginAndPassword(login2, password2);

            if (user1 == null || user2 == null) {
                String[] logStr = new String[2];
                if (user1 == null) {
                    logStr[0] = "Данные пользователя 1 не верны";
                }
                if (user2 == null) {
                    logStr[1] = "Данные пользователя 2 не верны";
                }
                log(logStr);
                return;
            }

            if (user1.equals(user2)) {
                log("Нельзя играть самому с собой");
                return;
            }

            up.setFirst(user1);
            up.setSecond(user2);
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

            int res = db.addUser(login, password);
            switch (res) {
                case 0:
                    log("Пользователь успешно зарегистрирован");
                    break;
                case 1:
                    log("Такой пользователь уже зарегистрирован");
                    break;
                default:
                    log("Ошибка");
                    break;
            }
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
