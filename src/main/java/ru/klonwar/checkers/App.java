package ru.klonwar.checkers;


import ru.klonwar.checkers.gui.P2PFrame;
import ru.klonwar.checkers.models.database.APIDatabase;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.H2Database;
import ru.klonwar.checkers.models.database.XMLDatabase;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

public class App {
    private static void setDefaultFont(String fontName, int size) {
        UIManager.getDefaults().forEach((key, value1) -> {
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource fr = (FontUIResource) value;
                fr = new FontUIResource(
                        (fontName != null) ? fontName : fr.getFontName(),
                        fr.getStyle(),
                        (size > 0) ? size : fr.getSize()
                );
                UIManager.put(key, fr);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Locale.setDefault(Locale.ROOT);
        setDefaultFont("monospace", 14);

        CheckersDatabase db = new APIDatabase();
        P2PFrame p2pf = new P2PFrame(db);
        p2pf.setVisible(true);
    }
}
