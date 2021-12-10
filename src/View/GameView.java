package view;

public class GameView implements IGameView {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static IGameView instance;

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final MainFrame MAIN_FRAME = new MainFrame();
    private final ComponentContainer CONTAINER = MAIN_FRAME.getComponentContainer();

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private GameView() {}

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void loadResources() {
        CONTAINER.loadResources();
    }

    @Override
    public void startApplication() {
        MAIN_FRAME.setupMenu();
        CONTAINER.startApplication();
    }

    @Override
    public void openWindow() {
        CONTAINER.switchState();
    }

    @Override
    public void resumeWindow() {
        CONTAINER.resumePreviousState();
    }

    @Override
    public void setupGameBar(int currentLives, int currentMaxLives, int currentBullets) {
        MAIN_FRAME.setupGameBar(currentLives,currentMaxLives,currentBullets);
    }

    @Override
    public void updateGameBar(int score, int coins, int lives, int bullets) {
        MAIN_FRAME.updateGameBar(score,coins,lives,bullets);
    }

    @Override
    public void setGameOverData(int currentScore, int recordScore, int currentCoins) {
        MAIN_FRAME.setGameOverData(currentScore,recordScore,currentCoins);
    }

    @Override
    public void setupUpgradePanel(int currentLives, int currentBullets, int totalCoins) {
        MAIN_FRAME.setupUpgradePanel(currentLives,currentBullets,totalCoins);
    }

    @Override
    public void setupDaytime() {
        MAIN_FRAME.setupDaytime();
    }

    @Override
    public void updateDaytime() {
        MAIN_FRAME.updateDaytime();
    }

    @Override
    public void setGameRunning(boolean running) {
        MAIN_FRAME.setGameRunning(running);
    }

    @Override
    public void getResources() {
        MAIN_FRAME.getResources();
    }

    @Override
    public void notifySizeChanged(int renderingTileSize) {
        MAIN_FRAME.notifySizeChanged(renderingTileSize);
    }

    @Override
    public void hasToNotifyChangingScreen(boolean notify) {
        CONTAINER.hasToNotifyChangingScreen(notify);
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static IGameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }

}

