package View;

public interface IGameView {
    void openMenuWindow();
    void openGameWindow();
    void openPauseWindow();
    void openGameOverWindow();

    int getRenderedTileSize();
//    void openGameOverWindow();
}