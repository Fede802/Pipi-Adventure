package controller;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;

import java.util.ArrayList;

public interface IGameEngine {
    void updateGameStatus();
    Pair<EntityCoordinates, Animation> getEntityForRendering(int entityID);
    int getTileData(int mapIndex,int mapX,int mapY);
    void setJumping(boolean isJumping);
    double getMapTranslX();
    void shoot();
    void setupGame();
    void notifySizeChanged();
    int getTotalEntity();

    void updateTotalCoin(int price);

    void updateCurrentLife();

    void updateCurrentBullets();

    void saveDataConfig();

    int getPlayerId();
}
