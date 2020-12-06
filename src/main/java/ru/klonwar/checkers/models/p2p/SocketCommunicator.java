package ru.klonwar.checkers.models.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketCommunicator {
    private final Socket s;

    public SocketCommunicator(Socket socket) {
        this.s = socket;
    }

    public String read() {
        try {
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            return bf.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String text) {
        try {
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(text);
            pr.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
