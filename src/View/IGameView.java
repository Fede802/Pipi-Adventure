package View;

public interface IGameView {
    void openWindow();
    void resumeWindow();
    void updateGameBar(int score, int coin, int life, int bullet);
}
