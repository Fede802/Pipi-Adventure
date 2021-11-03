package controller;

import model.GameData;
import model.GameModel;
import view.GameView;

public class GameStateHandler implements IGameStateHandler{
    public final static int MENU_STATE = 0;
    public final static int GAME_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public final static int GAME_OVER_STATE = 3;
    public final static int UPGRADE_STATE = 4;
    public final static int HELP_STATE = 5;
    public final static int LOADING_STATE = 6;

    private int currentState = LOADING_STATE;

    private static GameStateHandler instance = null;

    private GameStateHandler() {}

    @Override
    public int getCurrentState() {
        return currentState;
    }

    @Override
    public void startGame() {
        currentState = GAME_STATE;
        GameView.getInstance().hasToNotifyChangingScreen(true);
        GameView.getInstance().openWindow();

    }

    @Override
    public void menu() {
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
    public void openControlView() {
        currentState = HELP_STATE;
        GameView.getInstance().openWindow();
    }

    @Override
    public void gameOver() {
        currentState = GAME_OVER_STATE;
        GameView.getInstance().openWindow();
    }

    @Override
    public void pause() {
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

    @Override
    public void startApplication() {
        currentState = MENU_STATE;
        GameView.getInstance().startApplication();
    }

    @Override
    public void loadResources() {
        GameView.getInstance().loadResources();
    }

    public static GameStateHandler getInstance() {
        if (instance == null)
            instance = new GameStateHandler();
        return instance;
    }
}
