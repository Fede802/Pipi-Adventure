package Controller;

public interface IGameEngine {
    int getSectionSize();
    int getMapLength();
    int getMapTraslX();
    //TODO later, there're final needed?
    int getTileData(int mapIndex,int mapX,int mapY);
    void updateGameStatus();
    int[] getPlayerInfo();
    void setPlayerInfo(int mapIndex,int mapX,int traslX,int mapY,int traslY);
    void setJumping(boolean jumping);
    boolean isJumping();
    void jump();
    void fall();

}
