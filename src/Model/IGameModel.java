package model;

import commons.*;

public interface IGameModel {

    void updateMap();
    int getTileData(int mapIndex,int mapX,int mapY);
    void changeDaytime();

    void moveEntities();

    void setPlayerJumping(boolean isPlayerJumping);
    void playerJump();
    void setPlayerFalling(boolean isFalling);
    void playerFall();
    void shoot();

    int getEntityCount(EntityType entityType);
    void updateEntityStatus(EntityType entityType, int entityID);
    EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus);
    AnimationData getEntityAnimationData(EntityType entityType, int entityID);
    void updateEntityAnimationData(EntityType entityType, int entityID, AnimationData animation);
    void updatePlayerAnimationOpacity(float opacity);
    boolean isEntityDead(EntityType entityType, int entityID);

    void changeEntitiesCoordinates(int renderedTileSize);
    void setup();

}