package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;

public interface IMapGenerator {
    void generateMap();
    void updateMap();
    int getTileData(final int mapIndex,final int mapX,final int mapY);
    void updateDayTime();
    void moveEntities();
    int entityCount(EntityType entityType);
    EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus);
    AnimationData getEntityAnimation(EntityType entityType, int entityID);
    void updateAnimationData(EntityType entityType, int entityID, AnimationData animation);
    void updateEntitiesStatus(EntityType entityType,final int entityID);
    boolean isDead(EntityType entityType, int entityID);
    void changeCoordinate();
}
