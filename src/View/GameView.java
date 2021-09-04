package View;

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
        CONTAINER.resume();
    }

    @Override
    public void updateGameBar(int score, int coin, int life, int bullet) {
        MAIN_FRAME.updateGameBar(score,coin,life,bullet);
    }
    public static IGameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }
}
