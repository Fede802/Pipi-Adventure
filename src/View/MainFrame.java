package view;

import controller.GameStateHandler;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    //    --------------------------------------------------------
    //                       STATIC FIELDS
    //    --------------------------------------------------------

    private static final Dimension MINIMUM_SIZE = new Dimension(IApplicationPanel.DEFAULT_WIDTH,IApplicationPanel.DEFAULT_HEIGHT);
    private static final String FRAME_TITLE = "Pipi Adventure";
    private static final ImageIcon LOGO = new ImageIcon("res/images/gameImages/Logo.png");

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final ComponentContainer APPLICATION_CONTAINER = new ComponentContainer();
    private final MenuPanel MAIN_MENU = new MenuPanel();
    private final GamePanel GAME_PANEL = new GamePanel();
    private final PausePanel PAUSE = new PausePanel(APPLICATION_CONTAINER);
    private final GameOverPanel GAME_OVER = new GameOverPanel(APPLICATION_CONTAINER);
    private final HelpPanel HELP = new HelpPanel();
    private final UpgradePanel UPGRADE_PANEL = new UpgradePanel();
    private final LoadingPanel LOADING_PANEL = new LoadingPanel(MAIN_MENU.getTitlePadding());

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public MainFrame() {
        super(FRAME_TITLE);
        APPLICATION_CONTAINER.add(GameStateHandler.LOADING_STATE,LOADING_PANEL);
        APPLICATION_CONTAINER.add(GameStateHandler.MENU_STATE,MAIN_MENU);
        APPLICATION_CONTAINER.add(GameStateHandler.GAME_STATE,GAME_PANEL);
        APPLICATION_CONTAINER.add(GameStateHandler.PAUSE_STATE,PAUSE);
        APPLICATION_CONTAINER.add(GameStateHandler.GAME_OVER_STATE,GAME_OVER);
        APPLICATION_CONTAINER.add(GameStateHandler.HELP_STATE,HELP);
        APPLICATION_CONTAINER.add(GameStateHandler.UPGRADE_STATE,UPGRADE_PANEL);
        this.add(APPLICATION_CONTAINER);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(LOGO.getImage());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //todo useless pack
        //this.pack();
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public ComponentContainer getComponentContainer(){
        return APPLICATION_CONTAINER;
    }

    public void setupMenu() {
        MAIN_MENU.setup(LOADING_PANEL.getBgTransl(),LOADING_PANEL.getBgAnimationCurrentFrame(),LOADING_PANEL.getCurrentTitlePaddingTop());
    }

    public void setupGameBar(int currentLives, int currentMaxLives, int currentBullets) {
        GAME_PANEL.setupGameBar(currentLives,currentMaxLives,currentBullets);
    }

    public void updateGameBar(int score, int coins, int lives, int bullets) {
        GAME_PANEL.updateGameBar(score,coins,lives,bullets);
    }

    public void setGameOverData(int currentScore, int recordScore, int currentCoins) {
        GAME_OVER.setData(currentScore,recordScore,currentCoins);
    }

    public void setupUpgradePanel(int currentLives, int currentBullets, int totalCoins) {
        UPGRADE_PANEL.setup(currentLives, currentBullets, totalCoins);
    }

    public void setupDaytime() {
        GAME_PANEL.setupDaytime();
    }

    public void updateDaytime() {
        GAME_PANEL.updateDaytime();
    }

    public void getResources() {
        MAIN_MENU.loadResources();
        GAME_PANEL.loadResources();
        PAUSE.loadResources();
        GAME_OVER.loadResources();
        HELP.loadResources();
        UPGRADE_PANEL.loadResources();
    }

    public void setGameRunning(boolean running) {
        GAME_PANEL.setGameRunning(running);
    }

    public void notifySizeChanged(int renderingTileSize) {
        GAME_PANEL.updateRenderingTileSize(renderingTileSize);
    }

}
