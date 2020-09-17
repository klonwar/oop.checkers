package ru.klonwar.checkers.models;

public class Cell {
    private Checker checker;

    public Cell(Checker checker) {
        this.checker = checker;
    }

    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }
}
