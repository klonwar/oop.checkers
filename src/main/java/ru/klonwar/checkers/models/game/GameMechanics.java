package ru.klonwar.checkers.models.game;

import ru.klonwar.checkers.models.database.User;

import java.util.HashMap;
import java.util.Map;

public class GameMechanics {
    private final Map<PlayerColor, Player> players = new HashMap<>();
    private final Field field;
    private PlayerColor winner = null;
    private PlayerColor currentPlayerColor;

    public GameMechanics(Player whitePlayer, Player blackPlayer) {
        this.field = new Field();
        whitePlayer.setField(field);
        blackPlayer.setField(field);
        players.put(PlayerColor.WHITE, whitePlayer);
        players.put(PlayerColor.BLACK, blackPlayer);
        setCurrentPlayerColor(PlayerColor.WHITE);
    }

    public static Map<PlayerColor, Player> provideColors(User u1, User u2) {
        Map<PlayerColor, Player> colorizedPlayers = new HashMap<>();

        User[] users = new User[2];
        users[0] = u1;
        users[1] = u2;

        int absolutelyRandomIndex = (int) Math.abs((System.currentTimeMillis()) % 2);
        int anotherIndex = (absolutelyRandomIndex + 1) % 2;
        User whiteUser = users[absolutelyRandomIndex];
        User blackUser = users[anotherIndex];

        colorizedPlayers.put(PlayerColor.WHITE, new Player(whiteUser, PlayerColor.WHITE));
        colorizedPlayers.put(PlayerColor.BLACK, new Player(blackUser, PlayerColor.BLACK));

        return colorizedPlayers;
    }

    public void nextTurn() {
        checkWinner();
    }

    public PlayerColor checkWinner() {
        if (!haveWinner()) {
            for (Map.Entry<PlayerColor, Player> item : players.entrySet()) {
                Player p = item.getValue();
                PlayerColor pc = item.getKey();

                p.suggestPossibleMoves();
                if (p.getAvailableToClickCells().size() == 0) {
                    finish(pc.nextColor());
                    return pc.nextColor();
                }
            }
        }
        return null;
    }

    public Player getActivePlayer() {
        return players.get(currentPlayerColor);
    }

    public void changeShownIndex() {
        currentPlayerColor = currentPlayerColor.nextColor();
    }

    public Field getField() {
        return field;
    }

    public boolean haveWinner() {
        return winner != null;
    }

    public PlayerColor getWinner() {
        return winner;
    }

    public void finish(PlayerColor winner) {
        this.winner = winner;
    }

    public Player getWhitePlayer() {
        return players.get(PlayerColor.WHITE);
    }

    public Player getBlackPlayer() {
        return players.get(PlayerColor.BLACK);
    }

    public void setCurrentPlayerColor(PlayerColor currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }

    public PlayerColor getCurrentPlayerColor() {
        return currentPlayerColor;
    }
}
