package view;

public interface IGameView {

    void loadResources();
    void startApplication();
    void openWindow();
    void resumeWindow();

    void setupGameBar(int currentLives, int currentMaxLives, int currentBullets);
    void updateGameBar(int score, int coins, int lives, int bullets);

    void setGameOverData(int currentScore, int recordScore, int currentCoins);

    void setupUpgradePanel(int currentLives, int currentBullets, int totalCoins);

    void setupDaytime();
    void updateDaytime();

    void setGameRunning(boolean running);

    void getResources();
    void notifySizeChanged(int renderingTileSize);
    void hasToNotifyChangingScreen(boolean notify);

}
