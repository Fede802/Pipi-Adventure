package Controller;

public interface IGameStateHandler {

    int getPreviousState();
    int getCurrentState();
    void openGameWindow();
    void openGameOverWindow();

}
