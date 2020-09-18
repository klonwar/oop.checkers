package ru.klonwar.checkers.config;

import java.awt.*;

public enum ColorEnum {
    BLACK,
    WHITE,
    BACKGROUND,
    WHITE_CHECKER,
    BLACK_CHECKER,
    KING_ACCENT,
    CELL_BRIGHT,
    CELL_DARK,
    ACTIVE_COLOR,
    POSSIBLE_COLOR,
    REQUIRED_COLOR;

    public Color getColor() {
        switch (this) {
            case BLACK:
            case CELL_DARK:
                return new Color(38, 50, 56);
            case WHITE:
                return new Color(255, 255, 255);
            case BACKGROUND:
            case CELL_BRIGHT:
                return new Color(240, 240, 240);
            case WHITE_CHECKER:
                return new Color(176, 190, 197);
            case BLACK_CHECKER:
                return new Color(96, 125, 139);
            case ACTIVE_COLOR:
                return new Color(121, 134, 203);
            case POSSIBLE_COLOR:
                return new Color(76, 175, 80);
            case REQUIRED_COLOR:
            case KING_ACCENT:
                return new Color(255, 213, 79);
            default:
                return new Color(0,0,0);
        }
    }
}
