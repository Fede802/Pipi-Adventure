package controller;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.Pair;

public interface IGameEngine {

    void updateGameStatus();

    int getTileData(int mapIndex,int mapX,int mapY);
    double getMapTranslX();

    int getTotalEntities();
    Pair<EntityCoordinates, AnimationData> getEntityForRendering(int entityID);
    void updateAnimation(AnimationData animation, int entityID);

    void jump();
    void shoot();

    void updateTotalCoins(int variation);
    void updateCurrentLives();
    void updateCurrentBullets();
    void saveDataConfig();

    void setResources();
    void setupGame();
    void notifySizeChanged(int renderingTileSize);



    //debug purpose
    void switchImmortality();
    //debug purpose
    void switchWallCollision();
    //debug purpose
    void switchEntityCollision();
    //debug purpose
    void switchInfiniteBullets();
}
