package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;

public interface IGameEntity {

    EntityType getType();
    EntityStatus getEntityStatus();
    void updateEntityStatus();
    boolean isDead();
    void move();
    EntityCoordinates getEntityCoordinates();
    AnimationData getAnimation();
    void updateAnimation(AnimationData animation);
    void setDeathAnimation();
    void resetEntity();
    void changeCoordinate(int renderingTileSize);

}
