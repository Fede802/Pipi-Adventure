package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;

public interface IGameEntity {

    void move();
    EntityCoordinates getEntityCoordinates();
    Animation getAnimation();
    boolean isAlive();
    void setAlive(boolean isAlive);
    boolean isDying();
    void setDying(boolean isDying);
    boolean isDead();
    EntityType getType();
    void setDeathAnimation();
    void updateEntityStatus();
    void resetEntity();
    void changeCoordinate();


}
