package ru.klonwar.checkers.models.game;

public enum PlayerColor {
    WHITE,
    BLACK;

    public PlayerColor nextColor() {
        return (this == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

}
