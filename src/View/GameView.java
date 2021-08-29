package View;

public class GameView implements IGameView {

    private static GameView instance = null;
    private final MainFrame MAIN_FRAME = new MainFrame();
    private final ComponentContainer CONTAINER = MAIN_FRAME.getComponentContainer();
    private GameView(){}
    public static IGameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }
    @Override
    public void openGameMenu(){
        MAIN_FRAME.setVisible(true);
    }
    @Override
    public void openGameWindow() {
        //TODO later, when created starts gamePanel loading, check this for loading screen
        CONTAINER.switchState();
        CONTAINER.startGame();
    }

    @Override
    public int getRenderedTileSize() {
        return MapDrawer.RENDERED_TILE_SIZE;
    }

//    @Override
//    public void openGameOverWindow() {
//        MAIN_FRAME.moveScreen();
//    }
}
