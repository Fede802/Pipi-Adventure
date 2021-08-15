package Model;

public interface IGameModel {
    double getMapTralsX();
    void setMapTraslX(int mapTraslX);
    int[] getEntityPosition();
    void setEntityPosition(int[] entityPosition);
    double getEntityTraslY();
    void setEntityTraslY(double entityTraslY);
    void updateGameStatus();
    void updateMap();
    int getTileData(int mapIndex, int iIndex, int jIndex);
    int[] getPlayerPosition();
}
