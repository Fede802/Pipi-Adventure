package View;

public interface IGameView {
    void openMenuWindow();
    void openGameWindow();
    void openPauseWindow();
    void openGameOverWindow();
    void updateGameBar(int score, int coin, int life, int bullet);
}
