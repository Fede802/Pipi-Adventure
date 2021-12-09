package view;

import controller.GameStateHandler;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final Dimension MINIMUM_SIZE = new Dimension(IApplicationPanel.DEFAULT_WIDTH,IApplicationPanel.DEFAULT_HEIGHT);
    private static final String FRAME_TITLE = "Pipi Adventure";
    private static final ImageIcon LOGO = new ImageIcon("res/images/gameImages/Logo.png");

    private final ComponentContainer APPLICATION_SCREEN = new ComponentContainer();
    private final MenuPanel MAIN_MENU = new MenuPanel();
    private final GamePanel GAME_PANEL = new GamePanel();
    private final PausePanel PAUSE = new PausePanel(APPLICATION_SCREEN);
    private final GameOverPanel GAME_OVER = new GameOverPanel(APPLICATION_SCREEN);
    private final HelpPanel HELP = new HelpPanel();
    private final UpgradePanel UPGRADE_PANEL = new UpgradePanel();
    private final LoadingPanel LOADING_PANEL = new LoadingPanel(MAIN_MENU.getTitlePadding());

    public MainFrame() {
        super(FRAME_TITLE);
        APPLICATION_SCREEN.add(GameStateHandler.LOADING_STATE,LOADING_PANEL);
        APPLICATION_SCREEN.add(GameStateHandler.MENU_STATE,MAIN_MENU);
        APPLICATION_SCREEN.add(GameStateHandler.GAME_STATE,GAME_PANEL);
        APPLICATION_SCREEN.add(GameStateHandler.PAUSE_STATE,PAUSE);
        APPLICATION_SCREEN.add(GameStateHandler.GAME_OVER_STATE,GAME_OVER);
        APPLICATION_SCREEN.add(GameStateHandler.HELP_STATE,HELP);
        APPLICATION_SCREEN.add(GameStateHandler.UPGRADE_STATE,UPGRADE_PANEL);
        this.add(APPLICATION_SCREEN);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(LOGO.getImage());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //todo useless pack
        //this.pack();
    }

    public ComponentContainer getComponentContainer(){
        return APPLICATION_SCREEN;
    }

    public void updateGameBar(int score, int coin, int life, int bullet) {
        GAME_PANEL.updateGameBar(score,coin,life,bullet);
    }

    public void setupGameBar(int currentLife, int currentMaxLife, int currentBullets) {
        GAME_PANEL.setupGameBar(currentLife,currentMaxLife,currentBullets);
    }

    public void notifySizeChanged(int renderedTileSize) {
        GAME_PANEL.updateTileSize(renderedTileSize);
    }

    public void setGameRunning(boolean running) {
        GAME_PANEL.setGameRunning(running);
    }

    public void setGameOverData(int currentScore, int recordScore, int currentCoin) {
        GAME_OVER.setData(currentScore,recordScore,currentCoin);
    }

    public void updateDayTime() {
        GAME_PANEL.updateDayTime();
    }

    public void setupDaytime() {
        GAME_PANEL.setupDayTime();
    }

    public void setupUpgradePanel(int currentLife, int currentBullets, int totalCoin) {
        UPGRADE_PANEL.setup(currentLife, currentBullets, totalCoin);
    }

    public void setupMenu() {
        MAIN_MENU.setup(LOADING_PANEL.getBgTransl(),LOADING_PANEL.getBgAnimationCurrentFrame(),LOADING_PANEL.getCurrentTitlePaddingTop());
    }

    public void getResources() {
        MAIN_MENU.loadResources();
        GAME_PANEL.loadResources();
        PAUSE.loadResources();
        GAME_OVER.loadResources();
        HELP.loadResources();
        UPGRADE_PANEL.loadResources();
    }

}
