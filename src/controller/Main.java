package controller;


import utils.FontLoader;

import javax.swing.SwingUtilities;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        FontLoader.loadFonts();
        SwingUtilities.invokeLater(() -> GameStateHandler.getInstance().loadResources());
    }

}
