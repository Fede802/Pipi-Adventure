package Controller;

public class GameStateHandler implements IGameStateHandler{

    public final static int MENU_STATE = 0;
    public final static int GAME_STATE = 1;
    private int currentState = 0;

    private static GameStateHandler instance = null;
    private GameStateHandler(){}
    public static GameStateHandler getInstance() {
        if (instance == null)
            instance = new GameStateHandler();
        return instance;
    }

    @Override
    public void openGameWindow() {
        
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
