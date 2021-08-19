package Controller;

import View.GameView;

public class GameStateHandler implements IGameStateHandler{

    public final static int MENU_STATE = 0;
    public final static int GAME_STATE = 1;
    private int currentState = 0;
    private int previousState = 0;


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
        GameView.getInstance().openGameWindow();
    }

    @Override
    public void closeProgram() {

    }

    @Override
    public void openOptions() {

    }

    @Override
    public void closeOptions() {

    }
}
