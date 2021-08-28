package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pairs;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public interface IGameModel {
    int getSectionSize();
    int getMapLength();
    int getMapTraslX();
    //TODO later, there're final needed?
    int getTileData(int mapIndex,int mapX,int mapY);
    void setMapTraslX(int mapTraslX);
    void updateGameStatus();
    void updateMap();
    //TODO later, maybe all this chain has to be named getEntityInfo()?
    ArrayList<Pairs<EntityCoordinates,Animation>> getEntityCoordinates();
    EntityCoordinates getPlayerCoordinates();

//    void setPlayerInfo(int mapIndex,int mapX,int traslX,int mapY,int traslY);
    void setJumping(boolean jumping);
    boolean isJumping();
    void jump();
    void fall();
}
