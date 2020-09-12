package ru.klonwar.checkers.field;

import ru.klonwar.checkers.cell.CellGraphics;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.figures.Checker;
import ru.klonwar.checkers.figures.CheckerGraphics;

import java.awt.*;

public class FieldController {
    private final FieldCore fieldCore;
    private final Checker[][] fieldState;

    private FieldGraphics fieldGraphics;
    private Graphics2D g2d;

    public FieldController() {
        this.fieldCore = new FieldCore();
        this.fieldState = fieldCore.getFieldState();
    }

    public void setGraphics(Graphics2D g2d) {
        this.g2d = g2d;
        createGraphics();
    }

    private void createGraphics() {
        CellGraphics[][] cellGraphics = new CellGraphics[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CheckerGraphics checkerGraphics = null;
                if (fieldState[i][j] != null) {
                    checkerGraphics = new CheckerGraphics((fieldState[i][j].color == 0) ? Config.BLACK_CHECKER : Config.WHITE_CHECKER, g2d);
                }

                cellGraphics[i][j] = new CellGraphics(checkerGraphics, ((i+j) % 2 == 0) ? Config.CELL_BRIGHT : Config.CELL_DARK, g2d);
            }
        }

        this.fieldGraphics = new FieldGraphics(cellGraphics, g2d);
    }

    public void paint(int fieldWidth, Graphics2D to) {
        this.g2d = to;
        this.fieldGraphics.paint(fieldWidth);
    }
}
