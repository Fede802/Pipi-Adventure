package controller;

import model.GameData;
import model.GameModel;
import view.GameView;

public class GameStateHandler implements IGameStateHandler {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    public final static int MENU_STATE = 0;
    public final static int GAME_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public final static int GAME_OVER_STATE = 3;
    public final static int UPGRADE_STATE = 4;
    public final static int HELP_STATE = 5;
    public final static int LOADING_STATE = 6;

    private static GameStateHandler instance = null;

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private int currentState = LOADING_STATE;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private GameStateHandler() {}

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public int getCurrentState() {
        return currentState;
    }

    @Override
    public void startApplication() {
        currentState = MENU_STATE;
        GameView.getInstance().startApplication();
    }

    @Override
    public void loadResources() {
        GameView.getInstance().loadResources();
    }

    @Override
    public void startGame() {
        currentState = GAME_STATE;
        GameView.getInstance().hasToNotifyChangingScreen(true);
        GameView.getInstance().openWindow();
    }

    @Override
    public void openMenuPanel() {
        currentState = MENU_STATE;
        GameView.getInstance().openWindow();
    }

    @Override
    public void openUpgradePanel() {
        currentState = UPGRADE_STATE;
        GameView.getInstance().setupUpgradePanel(
                GameData.getInstance().getCurrentMaxLife(),
                GameData.getInstance().getCurrentMaxBullets(),
                GameData.getInstance().getTotalCoin()
                );
        GameView.getInstance().openWindow();
    }

    @Override
    public void openHelpPanel() {
        currentState = HELP_STATE;
        GameView.getInstance().openWindow();
    }

    @Override
    public void openGameOverPanel() {
        currentState = GAME_OVER_STATE;
        GameView.getInstance().openWindow();
    }

    @Override
    public void openPausePanel() {
        currentState = PAUSE_STATE;
        GameView.getInstance().openWindow();
    }

    @Override
    public void resumeGame() {
        currentState = GAME_STATE;
        GameView.getInstance().resumeWindow();
    }

    @Override
    public void notifyChangingScreen() {
        GameEngine.getInstance().setupGame();
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static GameStateHandler getInstance() {
        if (instance == null)
            instance = new GameStateHandler();
        return instance;
    }

}
