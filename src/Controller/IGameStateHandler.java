package Controller;

public interface IGameStateHandler {

    int getPreviousState();

    int getCurrentState();

    void openGameWindow();

    void closeProgram();

    void openOptions();

    void closeOptions();


}
