package view;

public interface IGameView {
    void openWindow();
    void resumeWindow();
    void updateGameBar(int score, int coin, int life, int bullet);
    void notifySizeChanged();
    void hasToNotifyChangingScreen(boolean notify);

    void startApplication();

    void isGameRunning(boolean running);

    void setGameOverData(int currentScore, int recordScore, int currentCoin);
}
