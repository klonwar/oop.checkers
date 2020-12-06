package ru.klonwar.checkers.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.SocketCommunicator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionPanel extends JPanel {
    private final JTextArea consoleOutput = new JTextArea();
    private final CheckersDatabase db;
    private final JTextField hostAddress = new JTextField("3001");
    private final JTextField connectionAddress = new JTextField("localhost:3001");
    private final JButton hostButton = new JButton("Стать хостом");
    private final JButton guestButton = new JButton("Присоединиться");

    public ConnectionPanel(ConnectionState cs, CheckersDatabase db, Runnable switchCards) {
        this.db = db;
        setLayout(new GridLayout(0, 3, 10, 10));

        // Host info
        JPanel u1Panel = new JPanel();
        u1Panel.setLayout(new GridLayout(8, 0, 10, 10));

        u1Panel.add(hostAddress);
        u1Panel.add(hostButton);

        // Guest info
        JPanel u2Panel = new JPanel();
        u2Panel.setLayout(new GridLayout(8, 0, 10, 10));

        u2Panel.add(connectionAddress);
        u2Panel.add(guestButton);

        // Консоль
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setBackground(new Color(38, 50, 56));

        consoleOutput.setBackground(new Color(38, 50, 56));
        consoleOutput.setForeground(new Color(76, 175, 80));
        consoleOutput.setFont(new Font("monospace", Font.PLAIN, 14));
        consoleOutput.setLineWrap(true);
        consolePanel.add(consoleOutput, BorderLayout.SOUTH);

        add(u1Panel);
        add(u2Panel);
        add(consolePanel);

        hostButton.addActionListener((e) -> {
            String portS = hostAddress.getText();
            int port = Integer.parseInt(portS);

            try {
                ServerSocket ss = new ServerSocket(port);
                Socket socket = ss.accept();
                ObjectMapper mapper = new ObjectMapper();
                var sc = new SocketCommunicator(socket);
                cs.sc = sc;
                log("Client connected");

                // Получаем данные о юзере
                String opponentData = sc.read();
                cs.opponentUser = mapper.readValue(opponentData, User.class);

                // Передаем данные о себе
                sc.send(mapper.writeValueAsString(cs.thisUser));

                SwingUtilities.invokeLater(switchCards);
            } catch (IOException ioException) {
                log(ioException.getMessage());
            }
        });

        guestButton.addActionListener((e) -> {
            String address = connectionAddress.getText();
            String hostname = address.substring(0, address.indexOf(':'));
            int port = Integer.parseInt(address.substring(address.indexOf(':') + 1));

            try {
                Socket socket = new Socket(hostname, port);
                var sc = new SocketCommunicator(socket);
                ObjectMapper mapper = new ObjectMapper();
                cs.sc = sc;
                log("You are connected");

                // Передаем данные о себе
                sc.send(mapper.writeValueAsString(cs.thisUser));

                // Получаем данные о хосте
                String opponentData = sc.read();
                cs.opponentUser = mapper.readValue(opponentData, User.class);

                SwingUtilities.invokeLater(switchCards);
            } catch (IOException ioException) {
                log(ioException.getMessage());
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
