package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;

public interface IMapGenerator {

    void generateMap();
    void updateMap();
    int getTileData(int mapIndex, int mapX, int mapY);
    void updateDaytime();

    void moveEntities();
    int entityCount(EntityType entityType);
    EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus);
    AnimationData getEntityAnimation(EntityType entityType, int entityID);
    void updateEntityAnimation(EntityType entityType, int entityID, AnimationData animation);
    void updateEntityStatus(EntityType entityType, int entityID);
    boolean isEntityDead(EntityType entityType, int entityID);
    void changeEntitiesCoordinates(int renderingTileSize);

}
