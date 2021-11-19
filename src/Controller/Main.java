package controller;


import utils.FontLoader;

import javax.swing.SwingUtilities;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        FontLoader.loadFonts();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameStateHandler.getInstance().loadResources();
            }
        });



     // GameStateHandler.getInstance().loadResources();
    }

}
