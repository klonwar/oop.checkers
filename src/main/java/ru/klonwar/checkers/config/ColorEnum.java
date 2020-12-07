package ru.klonwar.checkers.config;

import java.awt.*;

public enum ColorEnum {

    BLACK(new Color(38, 50, 56)),
    CELL_DARK(new Color(38, 50, 56)),
    WHITE(new Color(255, 255, 255)),
    TRANSPARENT_WHITE(new Color(255, 255, 255, 150)),
    BACKGROUND(new Color(240, 240, 240)),
    CELL_BRIGHT(new Color(240, 240, 240)),
    WHITE_CHECKER(new Color(176, 190, 197)),
    BLACK_CHECKER(new Color(96, 125, 139)),
    ACTIVE_COLOR(new Color(121, 134, 203)),
    POSSIBLE_COLOR(new Color(76, 175, 80)),
    REQUIRED_COLOR(new Color(255, 213, 79)),
    KING_ACCENT(new Color(255, 213, 79));

    private Color color;

    ColorEnum(Color color) {
        this.color = color;
    }

    public Color getColor() {
       return this.color;
    }
}
