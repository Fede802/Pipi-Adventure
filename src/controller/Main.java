package controller;

import commons.EntityType;
import utils.FontLoader;
import view.GameView;


import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FontLoader.loadFonts();
        int count = 0;
        for(int i = 0; i < 20; i++){
            count++;
            if(count == 10)
                continue;
            System.out.println(count);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameStateHandler.getInstance().startApplication();
            }
        });

    }

}
