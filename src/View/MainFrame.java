package View;

import Controller.GameStateHandler;

import javax.swing.*;

public class MainFrame extends JFrame {
    private GamePanel gamePanel;
    private MainMenu mainMenu;

    public MainFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu = new MainMenu();

        gamePanel = new GamePanel();
        gamePanel.setVisible(false);
        this.add(gamePanel);
        this.add(mainMenu);
        this.pack();

    }

    public  void switchState(){
        if (GameStateHandler.getInstance().getPreviousState() == GameStateHandler.MENU_STATE){
            if (GameStateHandler.getInstance().getCurrentState() == GameStateHandler.GAME_STATE){
                mainMenu.setVisible(false);
                gamePanel.setVisible(true);
            }
        }
    }
}
