package controller;

import commons.EntityType;
import utils.FontLoader;
import view.GameView;


import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FontLoader.loadFonts();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameStateHandler.getInstance().startApplication();
            }
        });

    }

}
