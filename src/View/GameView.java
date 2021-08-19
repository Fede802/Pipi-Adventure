package View;

public class GameView implements IGameView{
    private static GameView instance = null;
    private final MainFrame MAIN_FRAME;
    private GameView(){
        MAIN_FRAME = new MainFrame();}
    public static IGameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }

    @Override
    public void gameOver() {

    }

    @Override
    public double jump() {
        return 0;
    }

    @Override
    public void openGameWindow() {MAIN_FRAME.switchState();}

    @Override
    public void openGameMenu() { MAIN_FRAME.setVisible(true);}
}
