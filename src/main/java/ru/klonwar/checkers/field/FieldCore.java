package ru.klonwar.checkers.field;

import ru.klonwar.checkers.figures.Checker;

public class FieldCore {
    private Checker[][] fieldState = new Checker[8][8];

    public FieldCore() {
        for (int i = 0; i < fieldState.length; i++) {
            for (int j = 0; j < fieldState[i].length; j++) {
                if (((j == 0 || j == 2) && i % 2 == 1) || (j == 1 && i % 2 == 0)) {
                    fieldState[i][j] = new Checker(0);
                }
                if (((j == fieldState.length - 1 || j == fieldState.length - 3) && i % 2 == 0) || (j == fieldState.length - 2 && i % 2 == 1)) {
                    fieldState[i][j] = new Checker(1);
                }
            }
        }
    }

    public Checker[][] getFieldState() {
        return fieldState;
    }
}
