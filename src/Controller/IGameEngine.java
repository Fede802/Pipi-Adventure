package Controller;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pairs;


import java.util.ArrayList;
import java.util.Map;

public interface IGameEngine {
    int getSectionSize();
    int getMapLength();
    int getMapTraslX();
    //TODO later, there're final needed?
    int getTileData(int mapIndex,int mapX,int mapY);
    void updateGameStatus();
    ArrayList<Pairs<EntityCoordinates,Animation>> getEntityCoordinates();
//    ArrayList<Animation> getEntityAnimation();
//    void setPlayerInfo(int mapIndex,int mapX,int traslX,int mapY,int traslY);
    void setJumping(boolean jumping);
    boolean isJumping();
    void jump();
    void fall();

}
