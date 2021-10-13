package model;

import commons.*;

import java.util.ArrayList;

public interface IGameModel {
    void updateMap();
    int getTileData(int mapIndex,int mapX,int mapY);

    void moveEntity();
    void updateEntitiesStatus(EntityType entityType, int entityID, EntityStatus entityStatus);

    boolean isPlayerJumping();
    void setPlayerJumping(boolean isPlayerJumping);
    void playerJump();
    void playerFall();
    void shoot();

    EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus);
    Animation getEntityAnimation(EntityType entityType, int entityID);
    int getEntityCount(EntityType entityType);
    boolean isAlive(EntityType entityType, int entityID);

    void changeCoordinate();
    void setup();


}