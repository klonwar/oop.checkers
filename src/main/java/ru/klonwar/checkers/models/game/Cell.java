package ru.klonwar.checkers.models.game;

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

    @Override
    public String toString() {
        String res = (checker != null) ? checker.toString() : " ";
        return "[" + res + "]";
    }
}
