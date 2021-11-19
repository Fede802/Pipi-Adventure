package model;

import commons.*;

public interface IGameModel {
    void updateMap();
    int getTileData(int mapIndex,int mapX,int mapY);
    void changeDaytime();

    void moveEntity();
    void updateEntitiesStatus(EntityType entityType, int entityID);

    boolean isPlayerJumping();
    void setPlayerJumping(boolean isPlayerJumping);
    void playerJump();
    void playerFall();
    void shoot();

    EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus);
    AnimationData getEntityAnimation(EntityType entityType, int entityID);
    int getEntityCount(EntityType entityType);
    boolean isDead(EntityType entityType, int entityID);

    void changeCoordinate();
    void setup();

    void setPlayerFalling(boolean isFalling);

    void updateAnimationData(EntityType entityType, int entityID, AnimationData animation);
}