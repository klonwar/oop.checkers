package ru.klonwar.checkers.models.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.QueryResponse;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.p2p.ClientType;
import ru.klonwar.checkers.models.p2p.ConnectionState;

import java.util.concurrent.CompletableFuture;

public class Game {
    private final Field field;
    private int thisPlayerIndex = 0;
    private final Player whitePlayer;
    private final Player blackPlayer;

    private Integer winner = null;
    private Long finishTime = null;
    private CheckersDatabase db;
    private ConnectionState cs;

    public Game(ConnectionState cs, CheckersDatabase database) {
        this.db = database;
        field = new Field();
        this.cs = cs;

        if (cs.thisType == ClientType.HOST) {
            // Распределили цвета
            User[] users = new User[2];
            users[0] = cs.thisUser;
            users[1] = cs.opponentUser;

            int absolutelyRandomIndex = (int) Math.abs((System.currentTimeMillis()) % 2);
            int anotherIndex = (absolutelyRandomIndex == 0) ? 1 : 0;

            User whiteUser = users[absolutelyRandomIndex];
            User blackUser = users[anotherIndex];

            whitePlayer = new Player(whiteUser, field, 1);
            blackPlayer = new Player(blackUser, field, 0);

            // Слушаем, когда гость подключится
            cs.sc.read();

            // Определим свой индекс и отправили гостю его индекс.
            // Ходит вторым, если мы белые
            this.thisPlayerIndex = (whiteUser == cs.thisUser) ? 0 : 1;
            var guestIndex = ((whiteUser == cs.thisUser) ? 1 : 0);
            cs.sc.send(guestIndex + "");
        } else {
            // Отправили, что подключены
            cs.sc.send("ok");

            // Получили свой цвет
            String ans = cs.sc.read();
            this.thisPlayerIndex = Integer.parseInt(ans);

            // Созадим у себя плееров
            if (thisPlayerIndex == 0) {
                // Мы сейчас будем ходить
                whitePlayer = new Player(cs.thisUser, field, 1);
                blackPlayer = new Player(cs.opponentUser, field, 0);
            } else {
                // Хост сейчас будет ходить
                whitePlayer = new Player(cs.opponentUser, field, 1);
                blackPlayer = new Player(cs.thisUser, field, 0);
            }
        }

        if (whitePlayer.getUser() == cs.thisUser)
            enable();
        else
            disable();

        if (!isEnabled()) {
            readFieldState();
        }
    }

    private void disable() {
        cs.activeUser = false;
    }

    public void enable() {
        cs.activeUser = true;
    }

    public boolean isEnabled() {
        return cs.activeUser;
    }

    private void sendFieldState() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            cs.sc.send(mapper.writeValueAsString(field));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void readFieldState() {
        disable();
        CompletableFuture.supplyAsync(() -> cs.sc.read())
                .thenAccept((res) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Field newState = mapper.readValue(res, Field.class);

                        this.field.copyFrom(newState);
                        getActivePlayer().suggestPossibleMoves();
                        checkWinner();
                        enable();
                    } catch (Exception err) {
                        throw new RuntimeException(err);
                    }
                });
    }

    public Player getActivePlayer() {
        return (thisPlayerIndex == 0) ? whitePlayer : blackPlayer;
    }

    public int getThisPlayerIndex() {
        return thisPlayerIndex;
    }

    private void changeShownIndex() {
        thisPlayerIndex = (thisPlayerIndex == 1) ? 0 : 1;
    }

    /**
     * Механика смены активного игрока на следующего
     */

    public void switchPlayer() {
        checkWinner();

        // Ход совершен
        sendFieldState();
        readFieldState();
    }

    /**
     * Проверка активного игрока на наличие ходов
     */

    public void checkWinner() {
        if (!haveWinner()) {
            whitePlayer.suggestPossibleMoves();
            blackPlayer.suggestPossibleMoves();

            if (whitePlayer.getAvailableToClickCells().size() == 0) {
                // Победил черный
                // И это мы
                if (blackPlayer.getUser() == cs.thisUser) {
                    finish(2);
                    // Чел ждет ответа
                    sendFieldState();
                } else {
                    // Покажем, кто победил
                    finishWithoutQuery(2);
                    changeShownIndex();
                }
            } else if (blackPlayer.getAvailableToClickCells().size() == 0) {
                // Победил белый
                // И это мы
                if (whitePlayer.getUser() == cs.thisUser) {
                    finish(1);
                    // Чел ждет ответа
                    sendFieldState();
                } else {
                    // Покажем, кто победил
                    finishWithoutQuery(2);
                    changeShownIndex();
                }
            }
        }
    }

    public Field getField() {
        return field;
    }

    public boolean haveWinner() {
        return winner != null;
    }

    public Integer getWinner() {
        return winner;
    }

    public QueryResponse finish(Integer winner) {
        this.winner = winner;
        this.finishTime = System.currentTimeMillis();
        return db.addGame(this);
    }

    public void finishWithoutQuery(Integer winner) {
        this.winner = winner;
        this.finishTime = System.currentTimeMillis();
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Long getFinishTime() {
        return finishTime;
    }
}