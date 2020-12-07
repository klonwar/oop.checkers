package ru.klonwar.checkers.models.game;

import ru.klonwar.checkers.util.Position;

public class Field {
    public static final int width = 8;
    public static final int height = 8;

    private Cell[][] fieldState = new Cell[height][width];

    /**
     * При создании объекта <code>Field</code> поле заполняется шашками
     * в стандартном порядке
     */

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
            }
        }
    }

    /**
     * Проверяет позицию на принадлежность полю
     */

    public static boolean isInField(Position position) {
        if (position == null) return false;
        return !(position.getFirst() < 0 ||
                position.getFirst() >= width ||
                position.getSecond() < 0 ||
                position.getSecond() >= height
        );
    }

    /**
     * Возвращает объект <code>Cell</code> в указанной позиции
     */

    public Cell getCellFromPosition(Position position) {
        if (!Field.isInField(position)) return null;
        return fieldState[position.getFirst()][position.getSecond()];
    }

    /**
     * Ищет позицию указанного <code>Cell</code>
     */

    public Position getPositionFromCell(Cell cell) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cell == fieldState[i][j]) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Ищет позицию <code>Cell</code>, в котором находится указанный <code>Checker</code>
     */

    public Position getPositionFromChecker(Checker checker) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (checker == fieldState[i][j].getChecker()) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    public Cell[][] getFieldState() {
        return fieldState;
    }


    public void setFieldState(Cell[][] fieldState) {
        this.fieldState = fieldState;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res.append(fieldState[j][i].toString()).append(" ");
            }
            res.append("\n");
        }
        return "Field{\n" +
                res
                +"}";
    }

    public void copyFrom(Field field) {
        for (int i=0; i<Field.height; i++) {
            for (int j = 0; j < Field.height; j++) {
                fieldState[i][j].setChecker(field.fieldState[i][j].getChecker());
            }
        }
    }
}
