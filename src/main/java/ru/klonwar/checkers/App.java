package ru.klonwar.checkers;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.util.Locale;

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> (new MainFrame()).setVisible(true));
    }
}
