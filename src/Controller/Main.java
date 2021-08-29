package Controller;

import View.GameView;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //TODO move this to utils
        GraphicsEnvironment ge = null;
        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Resources/Font/04B_30__.TTF")));
        } catch(FontFormatException e){} catch (IOException e){
            System.out.println("Failed loading font");
        }
        GameView.getInstance().openGameMenu();
    }
}
