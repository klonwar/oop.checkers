package ru.klonwar.checkers.models;

import ru.klonwar.checkers.helpers.Pair;
import ru.klonwar.checkers.helpers.Position;

import java.util.ArrayList;

public class Player {
    private Field field;
    private final int color;

    private ArrayList<Pair<Position, Position>> availableMoves = new ArrayList<>();
    private ArrayList<Cell> availableToClickCells = new ArrayList<>();
    private Cell activeCell = null;

    public Player(Field field, int color) {
        this.color = color;
        this.field = field;

        suggestPossibleMoves();
    }

    public boolean moveChecker(Position from, Position to) {
        boolean endOfTurn = true;

        int deltaH = Math.round(Math.signum(to.getFirst() - from.getFirst()));
        int deltaV = Math.round(Math.signum(to.getSecond() - from.getSecond()));
        Cell toCell = field.getCellFromPosition(to);
        Cell fromCell = field.getCellFromPosition(from);

        if (deltaH != 0 || deltaV != 0) {
            int iterationsCount = 0;
            for (
                    int i = from.getFirst() + deltaH, j = from.getSecond() + deltaV;
                    ((deltaH >= 0) ? i <= to.getFirst() : i >= to.getFirst()) || ((deltaV >= 0) ? j <= to.getSecond() : j >= to.getSecond());
                    i += deltaH, j += deltaV, iterationsCount++
            ) {
                toCell = field.getCellFromPosition(new Position(i, j));

                if (toCell == null || fromCell == null) return true;

                toCell.setChecker(fromCell.getChecker());
                fromCell.setChecker(null);

                fromCell = toCell;
            }

            endOfTurn = iterationsCount <= 1;
        }

        if (!endOfTurn && findPossibleMovesFor(toCell).getSecond().size() == 0) {
            endOfTurn = true;
        }

        setActiveCell(toCell);
        suggestPossibleMoves();

        return endOfTurn;
    }

    public void clearHints() {
        activeCell = null;
    }

    public void suggestPossibleMoves() {
        availableMoves = new ArrayList<>();
        availableToClickCells = new ArrayList<>();

        ArrayList<Cell> requiredCells = new ArrayList<>();
        ArrayList<Pair<Position, Position>> requiredMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell item = field.getFieldState()[i][j];

                if (item.getChecker() != null && item.getChecker().getColor() == color) {
                    Pair<ArrayList<Position>, ArrayList<Position>> response = findPossibleMovesFor(item);

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

    private Pair<ArrayList<Position>, ArrayList<Position>> findPossibleMovesFor(Cell target) {
        Boolean[][] checkableField = new Boolean[8][8];
        Position position = field.getPositionFromCell(target);

        /*
         * Т.к. при возможности бить шашку необходимо это сделать, то
         * введем два массива: possible и required, которые помогут
         * отделить котлеты от мух
         * */

        ArrayList<Position> possible = new ArrayList<>();
        ArrayList<Position> required = new ArrayList<>();
        fpcRecursion(checkableField, possible, required, position, position);

        return new Pair<>(possible, required);
    }

    private void fpcRecursion(Boolean[][] checkableField, ArrayList<Position> possible, ArrayList<Position> required, Position now, Position target) {
        if (!Field.isInField(now) || checkableField[now.getFirst()][now.getSecond()] != null) {
            return;
        }

        checkableField[now.getFirst()][now.getSecond()] = true;

        Cell nowCell = field.getFieldState()[now.getFirst()][now.getSecond()];
        Cell targetCell = field.getFieldState()[target.getFirst()][target.getSecond()];

        if (targetCell.getChecker() == null) {
            return;
        }

        int nowH = now.getFirst();
        int nowV = now.getSecond();
        int targetH = target.getFirst();
        int targetV = target.getSecond();

        if (nowCell.getChecker() == null) {
            Checker targetChecker = targetCell.getChecker();
            if (targetChecker instanceof King) {
                // todo
            } else {
                if (targetChecker.getColor() == 0) {
                    if (nowV - targetV == 1 && Math.abs(nowH - targetH) == 1) {
                        possible.add(now);
                    }
                } else {
                    if (targetV - nowV == 1 && Math.abs(nowH - targetH) == 1) {
                        possible.add(now);
                    }
                }

                int deltaV = targetV - nowV;
                int deltaH = targetH - nowH;

                if (Math.abs(deltaV) == 2 && Math.abs(deltaH) == 2) {
                    int evilH = targetH - deltaH / 2;
                    int evilV = targetV - deltaV / 2;
                    if (Field.isInField(new Position(evilH, evilV))) {
                        Checker evilChecker = field.getFieldState()[evilH][evilV].getChecker();

                        if (evilChecker != null && evilChecker.getColor() != targetChecker.getColor()) {
                            required.add(now);
                        }
                    }
                }
            }
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                fpcRecursion(checkableField, possible, required, new Position(now.getFirst() + i, now.getSecond() + j), target);
            }
        }

    }


    public int getColor() {
        return color;
    }


    public ArrayList<Pair<Position, Position>> getAvailableMoves() {
        return availableMoves;
    }

    public Cell getActiveCell() {
        return activeCell;
    }

    public void setActiveCell(Cell activeCell) {
        this.activeCell = activeCell;
        suggestPossibleMoves();
    }

    public ArrayList<Cell> getAvailableToClickCells() {
        return availableToClickCells;
    }

    public void setAvailableToClickCells(ArrayList<Cell> availableToClickCells) {
        this.availableToClickCells = availableToClickCells;
    }
}
