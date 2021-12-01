package controller;


import utils.FontLoader;

import javax.swing.SwingUtilities;
import java.util.Random;


public class Main {
    private static final String FONT_PATH = "res/font/04B_30__.TTF";

    public static void main(String[] args) {
        FontLoader.loadFonts(FONT_PATH);
        SwingUtilities.invokeLater(() -> GameStateHandler.getInstance().loadResources());
    }

}
