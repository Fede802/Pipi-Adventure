package controller;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.Pair;

public interface IGameEngine {

    void updateGameStatus();

    int getTileData(int mapIndex,int mapX,int mapY);
    double getMapTranslX();

    int getTotalEntity();
    Pair<EntityCoordinates, AnimationData> getEntityForRendering(int entityID);
    void updateAnimationData(AnimationData value,int entityID);

    void jump();
    void shoot();

    void updateTotalCoin(int price);
    void updateCurrentLife();
    void updateCurrentBullets();
    void saveDataConfig();

    void setResources();
    void setupGame();
    void notifySizeChanged(int renderedTileSize);



    //debug purpose
    void switchImmortality();
    //debug purpose
    void switchWallCollision();
    //debug purpose
    void switchEntityCollision();
    //debug purpose
    void switchInfiniteBullets();
}
