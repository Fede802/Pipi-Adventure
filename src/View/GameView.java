package View;

public class GameView implements IGameView{
    private static GameView instance = null;
    private GameView(){}
    public static IGameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }

    @Override
    public void gameOver() {

    }



    @Override
    public void openGameWindow() {
        new MainFrame().setVisible(true);
    }
}
