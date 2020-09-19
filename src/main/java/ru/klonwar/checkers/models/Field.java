package ru.klonwar.checkers.models;

import ru.klonwar.checkers.helpers.Position;

public class Field {
    private final Cell[][] fieldState = new Cell[8][8];

    public Field() {
        for (int i = 0; i < fieldState.length; i++) {
            for (int j = 0; j < fieldState[i].length; j++) {
                fieldState[i][j] = new Cell(null);
                if (((j == 0 || j == 2) && i % 2 == 1) || (j == 1 && i % 2 == 0)) {
                    fieldState[i][j].setChecker(new Checker(0));
                }
                if (((j == fieldState.length - 1 || j == fieldState.length - 3) && i % 2 == 0) || (j == fieldState.length - 2 && i % 2 == 1)) {
                    fieldState[i][j].setChecker(new Checker(1));
                }
                // todo remove
                if (i == 6 && j == 5) {
                    fieldState[i][j].setChecker(new King(1));
                }
                if (i == 5 && j == 4) {
                    fieldState[i][j].setChecker(new Checker(0));
                }
                if (i == 1 && j == 0 || i == 2 && j == 1) {
                    fieldState[i][j].setChecker(null);
                }
            }
        }
    }

    public static boolean isInField(Position position) {
        if (position == null) return false;
        return !(position.getFirst() < 0 ||
                position.getFirst() >= 8 ||
                position.getSecond() < 0 ||
                position.getSecond() >= 8
        );
    }

    public Cell getCellFromPosition(Position position) {
        if (!Field.isInField(position)) return null;
        return fieldState[position.getFirst()][position.getSecond()];
    }

    public Position getPositionFromCell(Cell cell) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cell == fieldState[i][j]) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    public Cell[][] getFieldState() {
        return fieldState;
    }
}
