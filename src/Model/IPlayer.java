package model;

import commons.*;

public interface IPlayer {
    void moveBullets();
    void updateBulletsIndex();
    void jump();
    void fall();
    boolean isJumping();
    void setJumping(boolean isJumping);
    void setFalling(boolean isFalling);
    void shoot();
    void updateBulletStatus(int bulletID);
    EntityCoordinates getBulletCoordinate(int bulletID, EntityStatus entityStatus);
    AnimationData getBulletAnimation(int entityID);
    int bulletCount();
    boolean isBulletDead(int entityID);
    void setup();

    void updateBulletAnimationData(int entityID, AnimationData animation);
}
