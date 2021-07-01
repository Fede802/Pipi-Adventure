package View;

import javax.swing.*;

public class MainGUI extends JFrame {

    private GameBoard gameBoard;

    public MainGUI(){

        gameBoard = new GameBoard();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        this.add(gameBoard);
        this.pack();
    }
}
