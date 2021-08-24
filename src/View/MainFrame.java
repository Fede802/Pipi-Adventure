package View;

import Controller.GameStateHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public static final Dimension MINIMUM_SIZE = new Dimension(600,600);
    public static final String FRAME_TITLE = "Pipi Adventure";

    private final MainMenu MAIN_MENU = new MainMenu();
    private final GamePanel GAME_PANEL = new GamePanel();

    private final ArrayList<Component> STATE_LIST = new ArrayList<>();

    public MainFrame(){
        super(FRAME_TITLE);
        STATE_LIST.add(MAIN_MENU);
        STATE_LIST.add(GAME_PANEL);
        this.add(STATE_LIST.get(GameStateHandler.getInstance().getCurrentState()));
        this.setPreferredSize(MINIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public  void switchState(){
        this.remove(STATE_LIST.get(GameStateHandler.getInstance().getPreviousState()));
        this.add(STATE_LIST.get(GameStateHandler.getInstance().getCurrentState()));
        this.pack();
    }

    public void startGame(){
        GAME_PANEL.startGame();
    }

}

