package Controller;

public interface IGameStateHandler {

    int getPreviousState();
    int getCurrentState();
    void startGame();
    void menu();
    void gameOver();
    void pause();

}
