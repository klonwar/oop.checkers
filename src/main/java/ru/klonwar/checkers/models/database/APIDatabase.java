package ru.klonwar.checkers.models.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.klonwar.checkers.models.api.Fetch;
import ru.klonwar.checkers.models.game.GameMechanics;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class APIDatabase implements CheckersDatabase {
    private final String apiLink;

    public APIDatabase() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            apiLink = p.get("api-link").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public APIDatabase(String apiLink) {
        this.apiLink = apiLink;
    }

    @Override
    public QueryResponse addUser(User user) {
        try {
            Fetch fetch = new Fetch(apiLink + "/register/" + user.getLogin() + "/" + MD5.getMD5(user.getPassword()) + "/");
            String response = fetch.fetch();
            if (response.charAt(0) == '{' && response.charAt(1) == '}') {
                return new QueryResponse(false, "Такой пользователь уже зарегистрирован");
            }
            return new QueryResponse(true, "Успешно");
        } catch (Exception e) {
            return new QueryResponse(false, e.getMessage());
        }
    }

    @Override
    public QueryResponse addGame(GameMechanics gameMechanics) {
        try {
            Fetch fetch = new Fetch(apiLink +
                    "/new-game/" +
                    gameMechanics.getWhitePlayer().getId() + "/" +
                    gameMechanics.getBlackPlayer().getId() + "/" +
                    (gameMechanics.getWinner().ordinal() + 1) + "/");
            String response = fetch.fetch();
            if (response.charAt(0) == '{' && response.charAt(1) == '}') {
                return new QueryResponse(false, "Неверные параметры");
            }
            return new QueryResponse(true, "Успешно");
        } catch (Exception e) {
            return new QueryResponse(false, e.getMessage());
        }
    }

    @Override
    public List<GamerInfo> getTopUsers() {
        try {
            Fetch fetch = new Fetch(apiLink + "/gamers-list/");
            String response = fetch.fetch();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        try {
            Fetch fetch = new Fetch(apiLink + "/login/" + login + "/" + MD5.getMD5(password) + "/");
            String response = fetch.fetch();
            if (response.charAt(0) == '{' && response.charAt(1) == '}') {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
