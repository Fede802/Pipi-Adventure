package Controller;

import Model.MapGenerator;
import View.GameView;
import View.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameView.getInstance().openGameMenu();
    }
}
