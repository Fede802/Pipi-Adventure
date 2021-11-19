package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;

public interface IGameEntity {

    void move();
    EntityCoordinates getEntityCoordinates();
    AnimationData getAnimation();
    void updateAnimationData(AnimationData animationData);
    EntityStatus getEntityStatus();
    void setEntityStatus(EntityStatus status);
    boolean isDead();
    EntityType getType();
    void setDeathAnimation();
    void updateEntityStatus();
    void resetEntity();
    void changeCoordinate();




}
