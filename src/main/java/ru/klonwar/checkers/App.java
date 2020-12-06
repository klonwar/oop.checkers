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

        String dbSource = "api";
        String gameMode = "single";
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            dbSource = p.get("db-source").toString();
            gameMode = p.get("mode").toString();
        } catch (Exception e) {
            dbSource = "error";
        }

        if (!gameMode.equals("single")) {
            dbSource = "api";
        }

        CheckersDatabase db;
        if ("xml".equals(dbSource)) {
            db = new XMLDatabase();
        } else if ("api".equals(dbSource)) {
            db = new APIDatabase();
        } else {
            db = new H2Database();
        }

        P2PFrame p2pf = new P2PFrame(db);
        p2pf.setVisible(true);
    }
}
