package ru.klonwar.checkers;


import ru.klonwar.checkers.gui.MainFrame;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.SQLDatabase;
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

        String dbSource;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            dbSource = p.get("db-source").toString();
        } catch (Exception e) {
            dbSource = "error";
        }

        CheckersDatabase db;
        if ("xml".equals(dbSource)) {
            db = new XMLDatabase();
        } else {
            db = new SQLDatabase();
        }

        MainFrame mf = new MainFrame(db);
        mf.setVisible(true);

    }
}
