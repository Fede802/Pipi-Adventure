package Controller;

import Utils.Config;
import Utils.FontLoader;
import View.GameView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FontLoader.loadFonts();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameView.getInstance().openWindow();
            }
        });

    }
}
