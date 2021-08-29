package View;

import Controller.GameStateHandler;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    public static final Dimension MINIMUM_SIZE = new Dimension(600,600);
    public static final String FRAME_TITLE = "Pipi Adventure";

    private final MainMenu MAIN_MENU = new MainMenu();
    private final GameOver GAME_OVER = new GameOver();
    private final GamePanel GAME_PANEL = new GamePanel();
    private final ComponentContainer APPLICATION_SCREEN = new ComponentContainer();

    public MainFrame(){
        super(FRAME_TITLE);
        GAME_PANEL.setVisible(false);
        GAME_OVER.setVisible(false);

        APPLICATION_SCREEN.add(MAIN_MENU);
        APPLICATION_SCREEN.add(GAME_PANEL);
        APPLICATION_SCREEN.add(GAME_OVER);
        MAIN_MENU.requestFocus();

        this.add(APPLICATION_SCREEN);
        this.setPreferredSize(MINIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public ComponentContainer getComponentContainer(){
        return APPLICATION_SCREEN;
    }



}
