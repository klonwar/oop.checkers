package ru.klonwar.checkers.config;

import java.awt.*;

public final class Config {
    private Config() {
    }

    public static final int DEFAULT_HEIGHT = 30;
    public static final int MIN_WIDTH = 60;
    public static final int FONT_SIZE = 16;
    public static final int FONT_SIZE_LABEL = 18;
    public static final int LINE_STROKE = 4;
    public static final int BOLD_STROKE = 6;


    public static final Color BLACK = new Color(38, 50, 56);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BACKGROUND = new Color(240, 240, 240);

    public static final Color WHITE_CHECKER = new Color(0, 150, 136);
    public static final Color BLACK_CHECKER = new Color(183, 28, 28);

    public static final Color CELL_BRIGHT = BACKGROUND;
    public static final Color CELL_DARK = BLACK;
    public static final Color SOLVED_BG_COLOR = new Color(67, 160, 71);
    public static final Color SOLVED_SELECTED_BG_COLOR = new Color(27, 94, 32);

    public static final Font FONT = new Font("monospace", Font.PLAIN, Config.FONT_SIZE);


}
