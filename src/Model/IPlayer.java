package model;

import commons.*;

public interface IPlayer {

    void moveBullets();
    void updateBulletsMapIndex();
    void jump();
    void fall();
    void setJumping(boolean jumping);
    void setFalling(boolean falling);
    void shoot();

    void updateBulletStatus(int bulletID);
    EntityCoordinates getBulletCoordinates(int bulletID, EntityStatus entityStatus);
    AnimationData getBulletAnimation(int entityID);
    int bulletsCount();
    boolean isBulletDead(int entityID);
    void updateBulletAnimation(int entityID, AnimationData animation);
    void updateAnimationOpacity(float opacity);

}
