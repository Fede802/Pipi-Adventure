package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;

import java.util.ArrayList;

public interface IPlayer {
    void jump();
    void fall();
    boolean isJumping();
    void setJumping(boolean isJumping);
    void shoot();
    void updateBulletStatus(int bulletID);
    EntityCoordinates getBulletCoordinate(int bulletID);
}
