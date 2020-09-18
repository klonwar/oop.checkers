package ru.klonwar.checkers.models;

public class Checker {
    private final int color;
    private boolean king = false;

    public Checker(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void becomeKing() {
        this.king = true;
    }


}
