package Controller;

public interface IGameStateHandler {
    int getCurrentState();
    void startGame();
    void menu();
    void openUpgradePanel();
    void openControlView();
    void gameOver();
    void pause();
    void resumeGame();
}
