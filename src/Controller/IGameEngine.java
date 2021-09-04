package Controller;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;

import java.util.ArrayList;

public interface IGameEngine {
    void updateGameStatus();
    ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesCoordinates();
    int getTileData(int mapIndex,int mapX,int mapY);
    double getMapTraslX();
    void setJumping(boolean isJumping);
}
