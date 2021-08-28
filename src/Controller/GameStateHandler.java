package Controller;

import View.GameView;

public class GameStateHandler implements IGameStateHandler{
    public final static int MENU_STATE = 0;
    public final static int GAME_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public final static int GAME_OVER_STATE = 2;
    public static boolean overlay = false;

    private int currentState = MENU_STATE;
    private int previousState = currentState;


    private static GameStateHandler instance = null;
    private GameStateHandler(){}
    public static GameStateHandler getInstance() {
        if (instance == null)
            instance = new GameStateHandler();
        return instance;
    }

    @Override
    public int getPreviousState() {
        return previousState;
    }

    @Override
    public int getCurrentState() {
        return currentState;
    }

    @Override
    public void openGameWindow() {
        previousState = currentState;
        currentState = GAME_STATE;
        overlay = false;
        GameView.getInstance().openGameWindow();
    }

    @Override
    public void openGameOverWindow() {
        previousState = currentState;
        currentState = GAME_OVER_STATE;
        overlay = true;
        //GameView.getInstance().openGameOverWindow(overlay);
    }
}
