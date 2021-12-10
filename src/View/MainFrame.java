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
    private final MenuPanel MENU = new MenuPanel();
    private final GamePanel GAME = new GamePanel();
    private final PausePanel PAUSE = new PausePanel(APPLICATION_CONTAINER);
    private final GameOverPanel GAME_OVER = new GameOverPanel(APPLICATION_CONTAINER);
    private final HelpPanel HELP = new HelpPanel();
    private final UpgradePanel UPGRADE = new UpgradePanel();
    private final LoadingPanel LOADING = new LoadingPanel(MENU.getTitlePadding());

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public MainFrame() {
        super(FRAME_TITLE);
        APPLICATION_CONTAINER.add(GameStateHandler.LOADING_STATE, LOADING);
        APPLICATION_CONTAINER.add(GameStateHandler.MENU_STATE, MENU);
        APPLICATION_CONTAINER.add(GameStateHandler.GAME_STATE, GAME);
        APPLICATION_CONTAINER.add(GameStateHandler.PAUSE_STATE,PAUSE);
        APPLICATION_CONTAINER.add(GameStateHandler.GAME_OVER_STATE,GAME_OVER);
        APPLICATION_CONTAINER.add(GameStateHandler.HELP_STATE,HELP);
        APPLICATION_CONTAINER.add(GameStateHandler.UPGRADE_STATE, UPGRADE);
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
        MENU.setup(LOADING.getBgTransl(), LOADING.getBgAnimationCurrentFrame(), LOADING.getCurrentTitlePaddingTop());
    }

    public void setupGameBar(int currentLives, int currentMaxLives, int currentBullets) {
        GAME.setupGameBar(currentLives,currentMaxLives,currentBullets);
    }

    public void updateGameBar(int score, int coins, int lives, int bullets) {
        GAME.updateGameBar(score,coins,lives,bullets);
    }

    public void setGameOverData(int currentScore, int recordScore, int currentCoins) {
        GAME_OVER.setData(currentScore,recordScore,currentCoins);
    }

    public void setupUpgradePanel(int currentLives, int currentBullets, int totalCoins) {
        UPGRADE.setup(currentLives, currentBullets, totalCoins);
    }

    public void setupDaytime() {
        GAME.setupDaytime();
    }

    public void updateDaytime() {
        GAME.updateDaytime();
    }

    public void getResources() {
        MENU.loadResources();
        GAME.loadResources();
        PAUSE.loadResources();
        GAME_OVER.loadResources();
        HELP.loadResources();
        UPGRADE.loadResources();
    }

    public void setGameRunning(boolean running) {
        GAME.setGameRunning(running);
    }

    public void notifySizeChanged(int renderingTileSize) {
        GAME.updateRenderingTileSize(renderingTileSize);
    }

}
