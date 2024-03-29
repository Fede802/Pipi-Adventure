package controller;

import utils.FontLoader;
import javax.swing.SwingUtilities;

public class Main {

    //    --------------------------------------------------------
    //                       MAIN METHOD
    //    --------------------------------------------------------

    public static void main(String[] args) {
        FontLoader.loadFonts();
        SwingUtilities.invokeLater(() -> GameStateHandler.getInstance().loadResources());
    }
}
