package controller;


import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;

import java.util.ArrayList;

public interface IGameEngine {
    void updateGameStatus();
    ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesForRendering();
    int getTileData(int mapIndex,int mapX,int mapY);
    double getMapTraslX();
    void setJumping(boolean isJumping);
    void shoot();
    void setupGame();
    void notifySizeChanged();
}

