package ru.klonwar.checkers.models;

public class Cell {
    private Checker checker;
    private boolean active = false;
    private boolean possible = false;
    private boolean required = false;

    public Cell(Checker checker) {
        this.checker = checker;
    }

    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPossible() {
        return possible;
    }

    public void setPossible(boolean possible) {
        this.possible = possible;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
