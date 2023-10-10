package controller;

public interface IGameStateHandler {

    int getCurrentState();

    void startApplication();
    void loadResources();

    void startGame();
    void openMenuPanel();
    void openUpgradePanel();
    void openHelpPanel();
    void openGameOverPanel();
    void openPausePanel();
    void resumeGame();

    void notifyChangingScreen();

}
