package controller;


import utils.FontLoader;

import javax.swing.SwingUtilities;
import java.util.Random;


public class Main {
//todo reset current choice


    public static void main(String[] args) {
        int temp = Integer.MAX_VALUE;
        System.out.println(temp);
        FontLoader.loadFonts();
        SwingUtilities.invokeLater(() -> GameStateHandler.getInstance().loadResources());
    }

}
