package Controller;

import Model.GameModel;
import View.GameView;

public class GameStateHandler implements IGameStateHandler{public final static int MENU_STATE = 0;
    public final static int GAME_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public final static int GAME_OVER_STATE = 3;
    public final static int UPGRADE_STATE = 4;
    public final static int HELP_STATE = 5;

    private int currentState = MENU_STATE;

    private static GameStateHandler instance = null;

    private GameStateHandler() {}

    @Override
    public int getCurrentState() {
        return currentState;
    }

    @Override
    public void startGame() {
        currentState = GAME_STATE;
        GameModel.getInstance().changeCoordinate();
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

    public static GameStateHandler getInstance() {
        if (instance == null)
            instance = new GameStateHandler();
        return instance;
    }
}
