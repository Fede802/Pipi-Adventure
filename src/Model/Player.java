package model;

import commons.*;
import view.Animation;

import java.util.ArrayList;

public class Player extends GameEntity implements IPlayer{

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean isJumping = false;
    private boolean isFalling = false;
    private float opacity = 1f;

    public Player(EntityCoordinates entityCoordinates) {
        super(EntityType.PLAYER,RenderingType.PLAYER,entityCoordinates);
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
    public void move() {
        //TODO maybe use for start movement
        if(entityStatus == EntityStatus.ALIVE) {
            defaultWalkMovement(RIGHT_DIR);
        }
//        System.out.println(entityCoordinates.getTranslY());
    }
    @Override
    public void moveBullets(){
        //check if bullet is dead and if not move it
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
        System.out.println("JUMP");
        entityCoordinates.updateTraslY(-VEL_Y);
        if(Math.abs(entityCoordinates.getTranslY()) ==  RENDERED_TILE_SIZE) {
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
        }
    }

    @Override
    public void fall() {
        System.out.println("FALL");
        entityCoordinates.updateTraslY(VEL_Y);
        if(entityCoordinates.getTranslY() == RENDERED_TILE_SIZE){
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()+1);
        }

    }

    @Override
    public AnimationData getAnimation() {
//        if(isJumping||isFalling){
//            if(currentAnimationStep == animationStepList.get(currentAnimation)-1)
//                update = false;
//        }
////        AnimationData tempAnimation = animationList.get(currentAnimation);
////        if(tempAnimation.getCurrentAnimationStep() == tempAnimation.getAnimationStep()-1)
////                update = false;
////        }
//
//        return super.getAnimation(update);
        super.getAnimation();
        TEMP_ANIMATION.setOpacity(opacity);
        return TEMP_ANIMATION;
    }

    @Override
    public boolean isJumping() {
        return isJumping;
    }

    @Override
    public void setJumping(boolean isJumping) {
        //todo gestisci opacita dila
        this.isJumping = isJumping;
//        float prevOpacity = animationList.get(currentAnimation).getOpacity();
//        animationList.get(currentAnimation).resetAnimation();
        currentAnimation = commons.AnimationData.JUMPING_ANIMATION;
        if(!isJumping){
            isFalling = true;
        }else
            currentAnimationStep = 0;
//        animationList.get(currentAnimation).setOpacity(prevOpacity);
    }

    @Override
    public void setFalling(boolean isFalling) {
        this.isFalling = isFalling;
        //todo gestisci opacita dila
//        float prevOpacity = animationList.get(currentAnimation).getOpacity();
//        animationList.get(currentAnimation).resetAnimation();
//        currentAnimationStep =0;
        if(isFalling){
            currentAnimation = commons.AnimationData.JUMPING_ANIMATION;
//            currentAnimationStep = animationStepList.get(currentAnimation)-1;
            currentAnimationStep = AnimationData.LAST_FRAME;
        }else{
            currentAnimation = commons.AnimationData.WALK_ANIMATION_RIGHT;
            currentAnimationStep =0;
        }
//        animationList.get(currentAnimation).setOpacity(prevOpacity);

//        if(!isFalling)
//            currentAnimation = GameEntity.FALLING_ANIMATION;
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
        System.out.println("update bullet status");
        //todo maybe bullet could be remove in the next walk movement
//        bullets.remove(bulletID);
    }
    @Override
    public EntityCoordinates getBulletCoordinate(int bulletID, EntityStatus entityStatus){

//        int count = 0;
//        if(entityStatus == EntityStatus.ALIVE)
//        for(int i = 0; i < bullets.size();i++){
//            if(bullets.get(i).isAlive){
//                if(count == bulletID){
//                    count = i;
//                    i = bullets.size();
//                }else{
//                    count++;
//                }
//            }
//        }
//        else{
//            count = bulletID;
//        }
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
    public void changeCoordinate(){
        super.changeCoordinate();
        for(int i = 0; i < bullets.size();i++){
            bullets.get(i).changeCoordinate();
        }
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
    public void setup() {
        currentAnimationStep = 0;
        currentDeathloop = 0;
//        if(getAnimation() != null)
//            getAnimation().resetAnimation();
        currentAnimation = commons.AnimationData.WALK_ANIMATION_RIGHT;
//        getAnimation().resetAnimation();
        entityStatus = EntityStatus.ALIVE;
//        setAlive(true);
//        setDying(false);
        isFalling = false;
        isJumping = false;
    }

    @Override
    public void updateBulletAnimationData(int entityID, AnimationData animation) {
        bullets.get(entityID).updateAnimationData(animation);
    }
}
