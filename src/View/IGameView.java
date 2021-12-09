package view;

public interface IGameView {

    void loadResources();
    void startApplication();
    void openWindow();
    void resumeWindow();

    void setupGameBar(int currentLife, int currentMaxLife, int currentBullets);
    void updateGameBar(int score, int coin, int life, int bullet);

    void setGameOverData(int currentScore, int recordScore, int currentCoin);

    void setupUpgradePanel(int currentLife, int currentBullets, int totalCoin);

    void setupDaytime();
    void updateDayTime();

    void setGameRunning(boolean running);

    void getResources();
    void notifySizeChanged(int renderedTileSize);
    void hasToNotifyChangingScreen(boolean notify);

}
