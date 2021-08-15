package Controller;

public interface IGameEngineForView {
    void updateGameStatus();
    double getMapTraslation();
    void setMapTraslation();
    int[] getEntityPosition();
    void pauseGame();
    void restartGame();
    void stopGame();
    int getTileData(int mapIndex, int iIndex, int jIndex);
    void jump();
    int[] getPlayerPosition();

}
