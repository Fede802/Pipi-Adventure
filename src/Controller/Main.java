package Controller;

import Model.MapGenerator;
import View.GameView;
import View.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
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
