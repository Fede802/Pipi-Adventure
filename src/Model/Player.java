package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import commons.RenderingType;
import utils.GameDataConfig;

import java.util.ArrayList;

public class Player extends GameEntity implements IPlayer {

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final ArrayList<Bullet> BULLETS = new ArrayList<>();

    private float opacity = 1f;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public Player(EntityCoordinates entityCoordinates) {
        super(EntityType.PLAYER,RenderingType.PLAYER,entityCoordinates);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

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
        entityCoordinates.setTranslX(0);
        entityCoordinates.setTranslY(0);
    }

    @Override
    public void changeCoordinate(int renderingTileSize){
        super.changeCoordinate(renderingTileSize);
        for(int i = 0; i < BULLETS.size(); i++){
            BULLETS.get(i).changeCoordinate(renderingTileSize);
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
    public void updateBulletsMapIndex() {
        for(int i = 0; i < BULLETS.size(); i++){
            BULLETS.get(i).getEntityCoordinates().setMapIndex(
                    BULLETS.get(i).getEntityCoordinates().getMapIndex()-1
            );
        }
    }

    @Override
    public void jump() {
        entityCoordinates.updateTranslY(-velY);
        if(Math.abs(entityCoordinates.getTranslY()) == RENDERING_TILE_SIZE) {
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
        }
    }

    @Override
    public void fall() {
        entityCoordinates.updateTranslY(velY);
        if(entityCoordinates.getTranslY() == RENDERING_TILE_SIZE){
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()+1);
        }
    }

    @Override
    public void setJumping(boolean jumping) {
        if(jumping){
            currentAnimation = AnimationData.JUMPING_ANIMATION;
            currentAnimationStep = 0;
        }
    }

    @Override
    public void setFalling(boolean falling) {
        if(falling){
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
                .setTranslX(entityCoordinates.getTranslX())
                .setTranslY(entityCoordinates.getTranslY())
                .build()));
    }

    @Override
    public void updateBulletStatus(int bulletID) {
        BULLETS.get(bulletID).updateEntityStatus();
    }

    @Override
    public EntityCoordinates getBulletCoordinates(int bulletID, EntityStatus entityStatus) {
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
    public int bulletsCount() {
        return BULLETS.size();
    }

    @Override
    public boolean isBulletDead(int entityID) {
        return BULLETS.get(entityID).getEntityStatus() != EntityStatus.ALIVE;
    }

    @Override
    public void updateBulletAnimation(int entityID, AnimationData animation) {
        BULLETS.get(entityID).updateAnimation(animation);
    }

    @Override
    public void updateAnimationOpacity(float opacity) {
        this.opacity = opacity;
    }

}
