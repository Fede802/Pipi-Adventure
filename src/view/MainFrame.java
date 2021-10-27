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


    public MainFrame(){
        super(FRAME_TITLE);

        GameDataConfig.getInstance().setRenderedTileSize((int) (Math.min(MINIMUM_SIZE.getWidth(),MINIMUM_SIZE.getHeight())/GameDataConfig.getInstance().getMinTileToRender()));
        GAME_PANEL =  new GamePanel();
        GAME_PANEL.setPreferredSize(MINIMUM_SIZE);
        GAME_PANEL.setVisible(false);
//        GAME_OVER.setVisible(false);
        PAUSE.setVisible(false);
        HELP.setVisible(false);

        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.MENU_STATE,MAIN_MENU));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.GAME_STATE,GAME_PANEL));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.PAUSE_STATE,PAUSE));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.GAME_OVER_STATE,GAME_OVER));
        APPLICATION_SCREEN.add(new Pair<>(GameStateHandler.HELP_STATE,HELP));

        this.add(APPLICATION_SCREEN);
//        this.setPreferredSize(MINIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
}
