package view;

import commons.Pair;
import controller.GameStateHandler;
import utils.GameDataConfig;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final Dimension MINIMUM_SIZE = new Dimension(600,600);
    public static final String FRAME_TITLE = "Pipi Adventure";
    private final ComponentContainer APPLICATION_SCREEN = new ComponentContainer();
    private final MenuPanel MAIN_MENU = new MenuPanel();
    private final GamePanel GAME_PANEL;
    private final PausePanel PAUSE = new PausePanel(APPLICATION_SCREEN);
    private final GameOverPanel GAME_OVER = new GameOverPanel(APPLICATION_SCREEN);
    private final HelpPanel HELP = new HelpPanel();
    private final UpgradePanel UPGRADE_PANEL = new UpgradePanel();
    private final LoadingPanel LOADING_PANEL = new LoadingPanel();
    private ImageIcon logo = new ImageIcon("res/images/gameImages/Logo.png");


    public MainFrame(){
        super(FRAME_TITLE);

        GameDataConfig.getInstance().setRenderedTileSize((int) (Math.min(MINIMUM_SIZE.getWidth(),MINIMUM_SIZE.getHeight())/GameDataConfig.getInstance().getMinTileToRender()));
        GAME_PANEL =  new GamePanel();
        GAME_PANEL.setPreferredSize(MINIMUM_SIZE);
        GAME_PANEL.setVisible(false);
        GAME_OVER.setVisible(false);
        PAUSE.setVisible(false);
        HELP.setVisible(false);
        UPGRADE_PANEL.setVisible(false);
        MAIN_MENU.setVisible(false);

        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.LOADING_STATE,LOADING_PANEL));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.MENU_STATE,MAIN_MENU));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.GAME_STATE,GAME_PANEL));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.PAUSE_STATE,PAUSE));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.GAME_OVER_STATE,GAME_OVER));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.HELP_STATE,HELP));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.UPGRADE_STATE,UPGRADE_PANEL));

        this.add(APPLICATION_SCREEN);
//        this.setPreferredSize(MINIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(logo.getImage());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public ComponentContainer getComponentContainer(){
        return APPLICATION_SCREEN;
    }

    public void updateGameBar(int score, int coin, int life, int bullet){
        GAME_PANEL.updateGameBar(score,coin,life,bullet);
    }

    public void notifySizeChanged() {
        GAME_PANEL.updateTileSize();
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

    public void setupGameBar(int currentLife, int currentMaxLife, int currentBullets) {
        GAME_PANEL.setupGameBar(currentLife,currentMaxLife,currentBullets);
    }

    public void getResources() {
        System.out.println("menuLoad");
        MAIN_MENU.loadResources();
        System.out.println("gameload");
        GAME_PANEL.loadResources();
        System.out.println("pauseload");
        PAUSE.loadResources();
        System.out.println("overload");
        GAME_OVER.loadResources();
        System.out.println("helpload");
        HELP.loadResources();
        System.out.println("upgradeload");
        UPGRADE_PANEL.loadResources();
        System.out.println("finisload");
    }

    public void setupMenu() {
        MAIN_MENU.setup(LOADING_PANEL.getBgTrasl(),LOADING_PANEL.getTitlePadding(),LOADING_PANEL.getBgGif());
    }
}
