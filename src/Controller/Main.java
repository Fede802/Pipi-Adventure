package Controller;

import Model.GameModel;
import Utils.FontLoader;
import View.GameView;

public class Main {
    public static void main(String[] args) {
        FontLoader.loadFonts();
//        GameModel.getInstance();
//        GameEngine.getInstance();
        GameView.getInstance().openMenuWindow();
    }
}
