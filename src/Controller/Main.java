package Controller;

import Model.MapGenerator;
import View.GameView;
import View.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//           SwingUtilities.invokeLater(()->new MainFrame().setVisible(true));
        GameView.getInstance().openGameWindow();

    }
}
