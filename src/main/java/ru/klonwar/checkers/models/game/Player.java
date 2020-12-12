package ru.klonwar.checkers.models.game;

import ru.klonwar.checkers.util.Pair;
import ru.klonwar.checkers.util.Position;
import ru.klonwar.checkers.models.database.User;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Field field;
    private final PlayerColor color;
    private final User user;

    private List<Pair<Position, Position>> availableMoves = new ArrayList<>();
    private List<Cell> availableToClickCells = new ArrayList<>();
    private Cell activeCell = null;

    public Player(User user, Field field, PlayerColor color) {
        this.user = user;
        this.color = color;
        this.field = field;

        suggestPossibleMoves();
    }

    public Player(User user, PlayerColor color) {
        this.user = user;
        this.color = color;
    }

    /**
     * Игрок передвигает шашку из позиции <code>from</code> в позицию <code>to</code>
     */

    public boolean moveChecker(Position from, Position to) {
        boolean endOfTurn;

        Cell toCell = field.getCellFromPosition(to);
        Cell fromCell = field.getCellFromPosition(from);
        if (fromCell.getChecker() == null) return false;

        // Переместим шашку, параллельно поедая все шашки на пути
        endOfTurn = fromCell.getChecker().eatEverything(field, from, to);

        // Если надо - превратим в дамку
        Checker checker = toCell.getChecker();
        if (checker.canBecomeKing(to)) {
            checker.becomeKing();
            suggestPossibleMoves();
        }

        // Если ходов точно нет
        if (!endOfTurn && toCell.getChecker().findPossibleMoves(field).getSecond().size() == 0) {
            endOfTurn = true;
        }

        setActiveCell(toCell);

        return endOfTurn;
    }

    /**
     * Игрок убирает фокус с ячейки
     */

    public void clearActiveCell() {
        activeCell = null;
    }

    /**
     * Игрок анализирует доску и запоминает все возможные или
     * обязательные ходы своими шашками
     */

    public void suggestPossibleMoves() {
        availableMoves = new ArrayList<>();
        availableToClickCells = new ArrayList<>();

        List<Cell> requiredCells = new ArrayList<>();
        List<Pair<Position, Position>> requiredMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell item = field.getFieldState()[i][j];

                if (item.getChecker() != null && item.getChecker().getColor() == color) {
                    Pair<List<Position>, List<Position>> response = item.getChecker().findPossibleMoves(field);

                    for (Position to : response.getFirst()) {
                        availableMoves.add(new Pair<>(new Position(i, j), to));
                    }
                    for (Position to : response.getSecond()) {
                        requiredMoves.add(new Pair<>(new Position(i, j), to));
                    }

                    if (response.getSecond().size() != 0) {
                        requiredCells.add(item);
                    }
                    if (response.getFirst().size() != 0) {
                        availableToClickCells.add(item);
                    }
                }
            }
        }

        if (requiredCells.size() != 0) {
            availableToClickCells = requiredCells;
        }
        if (requiredMoves.size() != 0) {
            availableMoves = requiredMoves;
        }
    }


    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return user.getId();
    }

    public String getLogin() {
        return user.getLogin();
    }

    public PlayerColor getColor() {
        return color;
    }

    public List<Pair<Position, Position>> getAvailableMoves() {
        return availableMoves;
    }

    public Cell getActiveCell() {
        return activeCell;
    }

    /**
     * Игрок фокусируется на ячейке
     */

    public void setActiveCell(Cell activeCell) {
        this.activeCell = activeCell;
        suggestPossibleMoves();
    }

    public List<Cell> getAvailableToClickCells() {
        return availableToClickCells;
    }

    public void setAvailableToClickCells(List<Cell> availableToClickCells) {
        this.availableToClickCells = availableToClickCells;
    }
}
