package ru.klonwar.checkers.config;

import java.awt.*;

public final class Config {
    private Config() {
    }

    public static final int FONT_SIZE = 16;
    public static final int LINE_STROKE = 4;


    public static final Color BLACK = new Color(38, 50, 56);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BACKGROUND = new Color(240, 240, 240);

    public static final Color WHITE_CHECKER = new Color(176, 190, 197);
    public static final Color BLACK_CHECKER = new Color(96, 125, 139);

    public static final Color CELL_BRIGHT = BACKGROUND;
    public static final Color CELL_DARK = BLACK;
    public static final Color ACTIVE_COLOR = new Color(121, 134, 203);
    public static final Color POSSIBLE_COLOR = new Color(76, 175, 80);
    public static final Color REQUIRED_COLOR = new Color(255, 80, 80);

    public static final Font FONT = new Font("monospace", Font.PLAIN, Config.FONT_SIZE);


}
