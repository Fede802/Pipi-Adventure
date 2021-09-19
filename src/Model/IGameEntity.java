package model;

import commons.Animation;
import commons.EntityCoordinates;

public interface IGameEntity {
    void move();
    EntityCoordinates getEntityCoordinates();
    Animation getAnimation();
    boolean isAlive();
    void setAlive(boolean isAlive);
    boolean isDying();
    void setDying(boolean isDying);
    void changeCoordinate();
}
