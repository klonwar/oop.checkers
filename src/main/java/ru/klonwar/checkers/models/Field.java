package ru.klonwar.checkers.models;

import java.util.ArrayList;

public class Field {
    private Cell activeCell = null;
    private ArrayList<Cell> possibleCells = new ArrayList<>();
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
            }
        }
    }

    public Cell[][] getFieldState() {
        return fieldState;
    }

    public Cell getActiveCell() {
        return activeCell;
    }

    public void setActiveCell(Cell activeCell) {
        this.activeCell = activeCell;
    }

    public ArrayList<Cell> getPossibleCells() {
        return possibleCells;
    }

    public void clearPossibleCells() {
        this.possibleCells = new ArrayList<>();
    }

    public void setPossibleCells(ArrayList<Cell> possibleCells) {
        this.possibleCells = possibleCells;
    }
}
