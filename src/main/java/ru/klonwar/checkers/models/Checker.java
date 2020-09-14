package ru.klonwar.checkers.models;

import java.util.ArrayList;

public class Checker {
    private final int color;

    private ArrayList<Cell> possible = new ArrayList<>();
    private ArrayList<Cell> required = new ArrayList<>();

    public Checker(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public ArrayList<Cell> getRequired() {
        return required;
    }

    public void setRequired(ArrayList<Cell> required) {
        this.required = required;
    }

    public ArrayList<Cell> getPossible() {
        return possible;
    }

    public void setPossible(ArrayList<Cell> possible) {
        this.possible = possible;
    }
}
