package model;

import commons.*;
import utils.GameDataConfig;

import java.util.ArrayList;

public class Player extends GameEntity implements IPlayer{

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private float opacity = 1f;

    public Player(EntityCoordinates entityCoordinates) {
        super(EntityType.PLAYER,RenderingType.PLAYER,entityCoordinates);
    }
    @Override
    public void move() {
        //TODO maybe use for start movement
        if(entityStatus == EntityStatus.ALIVE) {
            defaultWalkMovement(RIGHT_DIR);
        }
    }

    @Override
    public AnimationData getAnimation() {
        super.getAnimation();
        TEMP_ANIMATION.setOpacity(opacity);
        return TEMP_ANIMATION;
    }

    @Override
    public void updateAnimationData(AnimationData animationData) {
        super.updateAnimationData(animationData);
        opacity = animationData.getOpacity();
    }

    @Override
    public void setDeathAnimation() {
        currentAnimation = commons.AnimationData.DEATH_ANIMATION_RIGHT;
    }

    @Override
    public void resetEntity() {
        currentAnimationStep = 0;
        currentDeathLoop = 0;
        opacity = 1f;
        currentAnimation = AnimationData.WALK_ANIMATION_RIGHT;
        entityStatus = EntityStatus.ALIVE;
        entityCoordinates.setMapX(GameDataConfig.getInstance().getPlayerStartMapX());
        entityCoordinates.setMapY(GameDataConfig.getInstance().getPlayerStartMapY());
        entityCoordinates.setMapIndex(0);
        entityCoordinates.setTranslX(0);
        entityCoordinates.setTranslY(0);
    }

    @Override
    public void changeCoordinate(){
        super.changeCoordinate();
        for(int i = 0; i < bullets.size();i++){
            bullets.get(i).changeCoordinate();
        }
    }

    @Override
    public void moveBullets(){
        //if bullet is dead remove and and if not move it
        for(int i = bullets.size()-1 ; i >=0; i--) {
            if (bullets.get(i).getEntityStatus() != EntityStatus.ALIVE)
                bullets.remove(i);
            else
                bullets.get(i).move();
        }
    }

    @Override
    public void updateBulletsIndex(){
        for(int i = 0 ; i < bullets.size(); i++){
            bullets.get(i).getEntityCoordinates().setMapIndex(
                    bullets.get(i).getEntityCoordinates().getMapIndex()-1
            );
        }
    }

    @Override
    public void jump() {
        entityCoordinates.updateTraslY(-VEL_Y);
        if(Math.abs(entityCoordinates.getTranslY()) ==  RENDERED_TILE_SIZE) {
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
        }
    }

    @Override
    public void fall() {
        entityCoordinates.updateTraslY(VEL_Y);
        if(entityCoordinates.getTranslY() == RENDERED_TILE_SIZE){
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()+1);
        }

    }

    @Override
    public void setJumping(boolean isJumping) {
        currentAnimation = commons.AnimationData.JUMPING_ANIMATION;
        if(isJumping)
            currentAnimationStep = 0;
    }

    @Override
    public void setFalling(boolean isFalling) {
        if(isFalling){
            currentAnimation = commons.AnimationData.JUMPING_ANIMATION;
            currentAnimationStep = AnimationData.LAST_FRAME;
        }else{
            currentAnimation = commons.AnimationData.WALK_ANIMATION_RIGHT;
            currentAnimationStep =0;
        }
    }

    @Override
    public void shoot() {
        int mapIndex = entityCoordinates.getMapIndex();
        int mapX = entityCoordinates.getMapX()+1;
        if(mapX == MapSection.SECTION_SIZE){
            mapX = 0;
            mapIndex++;
        }
        bullets.add(new Bullet(new EntityCoordinates.Builder(mapX,entityCoordinates.getMapY())
                .setStartMapIndex(mapIndex)
                .setTranslX(entityCoordinates.getTranslX())
                .setTranslY(entityCoordinates.getTranslY())
                .build()));
    }

    @Override
    public void updateBulletStatus(int bulletID){
        bullets.get(bulletID).updateEntityStatus();
        //todo maybe bullet could be remove in the next walk movement
//        bullets.remove(bulletID);
    }

    @Override
    public EntityCoordinates getBulletCoordinate(int bulletID, EntityStatus entityStatus){
        EntityCoordinates tempE;
        if(entityStatus == EntityStatus.ALIVE && bullets.get(bulletID).getEntityStatus() != EntityStatus.ALIVE)
            tempE = null;
        else
            tempE = bullets.get(bulletID).getEntityCoordinates();
       return tempE;
    }

    @Override
    public AnimationData getBulletAnimation(int entityID){
        return bullets.get(entityID).getAnimation();
    }

    @Override
    public int bulletCount() {
        return bullets.size();
    }

    @Override
    public boolean isBulletDead(int entityID) {
        return bullets.get(entityID).isDead();
    }

    @Override
    public void updateBulletAnimationData(int entityID, AnimationData animation) {
        bullets.get(entityID).updateAnimationData(animation);
    }
}
