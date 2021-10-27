package model;

import commons.*;

import java.util.ArrayList;

public interface IPlayer {
    void moveBullets();
    void updateBulletsIndex();
    void jump();
    void fall();
    boolean isJumping();
    void setJumping(boolean isJumping);
    void shoot();
    void updateBulletStatus(int bulletID);
    EntityCoordinates getBulletCoordinate(int bulletID, EntityStatus entityStatus);
    Animation getBulletAnimation(int entityID);
    int bulletCount();
    boolean isBulletDead(int entityID);
    void setup();
}
