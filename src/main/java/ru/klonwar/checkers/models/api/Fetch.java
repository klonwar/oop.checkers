package ru.klonwar.checkers.models.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Fetch {
    private String link;

    public Fetch(String link) {
        this.link = link;
    }

    public String fetch() {
        HttpURLConnection con;
        try {
            URL url = new URL(link);
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new APIError(e);
        }

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            return response.toString();
        } catch (IOException e) {
            throw new APIError(e);
        }
    }
}
