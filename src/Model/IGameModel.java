package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;
import commons.Pair;

import java.util.ArrayList;

public interface IGameModel {
    void updateMap();
    int getTileData(int mapIndex,int mapX,int mapY);
    boolean isPlayerJumping();
    void setPlayerJumping(boolean isPlayerJumping);
    void playerJump();
    void playerFall();
    void shoot();
    EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID);
    Animation getEntityAnimation(int entityID);
    int getEntityCount(EntityType entityType, EntityType entityStatus);

    void updateEntitiesStatus(EntityType entityType, int entityID);
    void changeCoordinate();
    void setup();

    void moveEntity();
}