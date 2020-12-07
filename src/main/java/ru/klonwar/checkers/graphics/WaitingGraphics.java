package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;

import java.awt.*;

public class WaitingGraphics {
    public void paint(Graphics2D g2d, int w, int h) {
        g2d.setColor(ColorEnum.TRANSPARENT_WHITE.getColor());
        g2d.fillRect(0, 0, w, h);
        g2d.setColor(ColorEnum.BLACK.getColor());
        g2d.setFont(new Font("monospace", Font.PLAIN, Config.FONT_SIZE * 2));
        g2d.drawString("Waiting...", w / 2 - Config.FONT_SIZE*10, h / 2 - Config.FONT_SIZE);
    }
}
