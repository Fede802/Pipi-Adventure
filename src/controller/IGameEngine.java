package controller;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.Pair;

public interface IGameEngine {
    void updateGameStatus();
    Pair<EntityCoordinates, AnimationData> getEntityForRendering(int entityID);
    int getTileData(int mapIndex,int mapX,int mapY);
    void jump();
    double getMapTranslX();
    void shoot();
    void setupGame();
    void notifySizeChanged(int renderedTileSize);
    int getTotalEntity();

    void updateTotalCoin(int price);

    void updateCurrentLife();

    void updateCurrentBullets();

    void saveDataConfig();

    void updateAnimationData(AnimationData value,int entityID);

    void setResources();
}
