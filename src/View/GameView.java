package View;

public class GameView implements IGameView {

    private static GameView instance = null;
    private final MainFrame MAIN_FRAME = new MainFrame();
    private final ComponentContainer CONTAINER = MAIN_FRAME.getComponentContainer();

    private GameView() {
    }

    public static IGameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }

    @Override
    public void openMenuWindow() {
        CONTAINER.switchState();
    }

    @Override
    public void openGameWindow() {
        //TODO later, when created starts gamePanel loading, check this for loading screen
        CONTAINER.switchState();
    }

    @Override
    public void openPauseWindow() {
        CONTAINER.switchState();
        CONTAINER.pause();
    }

    @Override
    public void openGameOverWindow() {
        CONTAINER.switchState();
        CONTAINER.gameOver();
    }

    @Override
    public void updateGameBar(int score, int coin, int life, int bullet) {
        CONTAINER.updateGameBar(score,coin,life,bullet);
    }
}
