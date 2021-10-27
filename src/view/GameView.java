package view;

public class GameView implements IGameView{
    private static GameView instance = null;
    private final MainFrame MAIN_FRAME = new MainFrame();
    private final ComponentContainer CONTAINER = MAIN_FRAME.getComponentContainer();

    private GameView() {}

    @Override
    public void openWindow() {
        CONTAINER.switchState();
    }

    @Override
    public void resumeWindow() {
        CONTAINER.resumePreviousState();
    }

    @Override
    public void updateGameBar(int score, int coin, int life, int bullet) {
        MAIN_FRAME.updateGameBar(score,coin,life,bullet);
    }

    @Override
    public void notifySizeChanged() {
        MAIN_FRAME.notifySizeChanged();
    }

    @Override
    public void hasToNotifyChangingScreen(boolean notify) {
        CONTAINER.hasToNotifyChangingScreen(notify);
    }

    @Override
    public void startApplication() {
        CONTAINER.startApplication();
    }

    @Override
    public void isGameRunning(boolean running) {
        MAIN_FRAME.setGameRunning(running);
    }

    @Override
    public void setGameOverData(int currentScore, int recordScore, int currentCoin) {
        MAIN_FRAME.setGameOverData(currentScore,recordScore,currentCoin);
    }

    @Override
    public void updateDayTime() {
        MAIN_FRAME.updateDayTime();
    }

    public static IGameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }
}

