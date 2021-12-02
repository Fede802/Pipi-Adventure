package model;

import commons.*;

public interface IGameModel {
    void updateMap();
    int getTileData(int mapIndex,int mapX,int mapY);
    void changeDaytime();

    void moveEntities();
    void updateEntitiesStatus(EntityType entityType, int entityID);

    void setPlayerJumping(boolean isPlayerJumping);
    void playerJump();
    void setPlayerFalling(boolean isFalling);
    void playerFall();
    void shoot();

    EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus);
    AnimationData getEntityAnimationData(EntityType entityType, int entityID);
    void updateAnimationData(EntityType entityType, int entityID, AnimationData animation);
    void updateAnimationOpacity(float opacity);
    int getEntityCount(EntityType entityType);
    boolean isDead(EntityType entityType, int entityID);

    void changeCoordinate(int renderedTileSize);
    void setup();



}