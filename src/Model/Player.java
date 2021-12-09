package model;

import commons.*;
import utils.GameDataConfig;

import java.util.ArrayList;

public class Player extends GameEntity implements IPlayer {

    private final ArrayList<Bullet> BULLETS = new ArrayList<>();
    private float opacity = 1f;

    public Player(EntityCoordinates entityCoordinates) {
        super(EntityType.PLAYER,RenderingType.PLAYER,entityCoordinates);
    }

    @Override
    public void move() {
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
    public void setDeathAnimation() {
        currentAnimation = AnimationData.DEATH_ANIMATION_RIGHT;
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
        entityCoordinates.setTraslX(0);
        entityCoordinates.setTraslY(0);
    }

    @Override
    public void changeCoordinate(int renderedTileSize){
        super.changeCoordinate(renderedTileSize);
        for(int i = 0; i < BULLETS.size(); i++){
            BULLETS.get(i).changeCoordinate(renderedTileSize);
        }
    }

    @Override
    public void moveBullets(){
        //if bullet is dead remove and and if not move it
        for(int i = BULLETS.size()-1; i >=0; i--) {
            if (BULLETS.get(i).getEntityStatus() != EntityStatus.ALIVE) {
                BULLETS.remove(i);
            }
            else
                BULLETS.get(i).move();
        }
    }

    @Override
    public void updateBulletsIndex() {
        for(int i = 0; i < BULLETS.size(); i++){
            BULLETS.get(i).getEntityCoordinates().setMapIndex(
                    BULLETS.get(i).getEntityCoordinates().getMapIndex()-1
            );
        }
    }

    @Override
    public void jump() {
        entityCoordinates.updateTraslY(-VEL_Y);
        if(Math.abs(entityCoordinates.getTraslY()) ==  RENDERED_TILE_SIZE) {
            entityCoordinates.setTraslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
        }
    }

    @Override
    public void fall() {
        entityCoordinates.updateTraslY(VEL_Y);
        if(entityCoordinates.getTraslY() == RENDERED_TILE_SIZE){
            entityCoordinates.setTraslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()+1);
        }
    }

    @Override
    public void setJumping(boolean isJumping) {
        if(isJumping){
            currentAnimation = AnimationData.JUMPING_ANIMATION;
            currentAnimationStep = 0;
        }
    }

    @Override
    public void setFalling(boolean isFalling) {
        if(isFalling){
            currentAnimation = AnimationData.JUMPING_ANIMATION;
            currentAnimationStep = AnimationData.LAST_FRAME;
        }else{
            currentAnimation = AnimationData.WALK_ANIMATION_RIGHT;
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
        BULLETS.add(new Bullet(new EntityCoordinates.Builder(mapX,entityCoordinates.getMapY())
                .setStartMapIndex(mapIndex)
                .setTraslX(entityCoordinates.getTraslX())
                .setTraslY(entityCoordinates.getTraslY())
                .build()));
    }

    @Override
    public void updateBulletStatus(int bulletID) {
        BULLETS.get(bulletID).updateEntityStatus();
    }

    @Override
    public EntityCoordinates getBulletCoordinate(int bulletID, EntityStatus entityStatus) {
        EntityCoordinates tempE;
        if(entityStatus == EntityStatus.ALIVE && BULLETS.get(bulletID).getEntityStatus() != EntityStatus.ALIVE)
            tempE = null;
        else
            tempE = BULLETS.get(bulletID).getEntityCoordinates();
       return tempE;
    }

    @Override
    public AnimationData getBulletAnimation(int entityID){
        return BULLETS.get(entityID).getAnimation();
    }

    @Override
    public int bulletCount() {
        return BULLETS.size();
    }

    @Override
    public boolean isBulletDead(int entityID) {
        return BULLETS.get(entityID).getEntityStatus() != EntityStatus.ALIVE;
    }

    @Override
    public void updateBulletAnimationData(int entityID, AnimationData animation) {
        BULLETS.get(entityID).updateAnimationData(animation);
    }
    @Override
    public void updateAnimationOpacity(float opacity) {
        this.opacity = opacity;
    }

}
