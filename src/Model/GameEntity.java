package model;

import commons.*;
import utils.GameDataConfig;

import java.util.HashMap;

public abstract class GameEntity implements IGameEntity{

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------
    protected static int RENDERED_TILE_SIZE = GameDataConfig.getInstance().getRenderedTileSize();
    protected static int RIGHT_DIR = 0;
    protected static int LEFT_DIR = 1;
    protected static final int DEFAULT_DEATH_LOOP = 1;
    protected static final int DEFAULT_WALKING_STEP = 96;
    protected static final AnimationData TEMP_ANIMATION = new AnimationData();

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------
    protected final EntityType ID;
    protected final RenderingType R_ID;
    protected EntityStatus entityStatus = EntityStatus.ALIVE;
//    protected final HashMap<Integer, Integer> animationStepList = new HashMap<>();
//    private boolean entityDataLoaded;
    protected int deathLoop = DEFAULT_DEATH_LOOP;
    protected int currentDeathloop;
    protected int walkingStep = DEFAULT_WALKING_STEP;
    protected int currentWalkingStep;
    protected int currentAnimationStep;
    //todo add control for animationstep
    protected EntityCoordinates entityCoordinates;
    protected int currentAnimation = commons.AnimationData.WALK_ANIMATION_RIGHT;
    //change to 4 8 5 10
    protected double TILE_STEP = 5;
    protected double VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
    protected double VEL_Y = RENDERED_TILE_SIZE/TILE_STEP;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------
    public GameEntity(EntityType id,RenderingType R_ID, EntityCoordinates entityCoordinates){
        this.ID = id;
        this.R_ID = R_ID;
        this.entityCoordinates = entityCoordinates;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHOD
    //    --------------------------------------------------------
    @Override
    public EntityCoordinates getEntityCoordinates() {
        return entityCoordinates;
    }

    @Override
    public AnimationData getAnimation() {



//        AnimationData tempAnimation = animationList.get(currentAnimation);
//        tempAnimation.setCurrentAnimationStep(currentAnimationStep);
//        if(entityStatus == EntityStatus.DYING)
//            tempAnimation.setCurrentNumLoop(currentDeathloop);

//        int animationStep = animationStepList.get(currentAnimation);
//        if(update){
//            currentAnimationStep++;
//            if(currentAnimationStep == animationStep-1 || animationStep == 1){
//                if(entityStatus == EntityStatus.DYING)
//                    currentDeathloop++;
//            }
//            if(currentAnimationStep == animationStep)
//                currentAnimationStep = 0;
//            tempAnimation.update();
//            currentAnimationStep = tempAnimation.getCurrentAnimationStep();
//            if(entityStatus == EntityStatus.DYING)
//                currentDeathloop = tempAnimation.getCurrentNumLoop();
//        }
        TEMP_ANIMATION.setupAnimation(currentAnimationStep,currentAnimation,R_ID);
        if(entityStatus == EntityStatus.DYING)
            TEMP_ANIMATION.setCurrentNumLoop(currentDeathloop);
        return TEMP_ANIMATION;
    }

    @Override
    public void updateAnimationData(AnimationData animationData) {
        currentAnimationStep = animationData.getCurrentAnimationStep();
        if(entityStatus == EntityStatus.DYING)
            currentDeathloop = animationData.getCurrentNumLoop();
    }


    @Override
    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    @Override
    public void setEntityStatus(EntityStatus status) {
        entityStatus = status;
    }


    @Override
    public boolean isDead() {
        return (entityStatus == EntityStatus.DYING && currentDeathloop == deathLoop);
    }


    @Override
    public EntityType getType(){
        return ID;
    }

    protected void defaultWalkMovement(int movingDirection) {
        if(movingDirection == RIGHT_DIR){
            entityCoordinates.updateTraslX(VEL_X);
            if(entityCoordinates.getTranslX() >= RENDERED_TILE_SIZE){
                entityCoordinates.setTranslX(entityCoordinates.getTranslX()-RENDERED_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
            }
            if(entityCoordinates.getMapX() == GameDataConfig.getInstance().getMapSectionSize()){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
                entityCoordinates.setMapX(0);
            }
        }else{
            entityCoordinates.updateTraslX(-VEL_X);
            if(Math.abs(entityCoordinates.getTranslX()) >= RENDERED_TILE_SIZE) {
                entityCoordinates.setTranslX(entityCoordinates.getTranslX() + RENDERED_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX() - 1);
            }
            if(entityCoordinates.getMapX() == -1){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()-1);
                entityCoordinates.setMapX(GameDataConfig.getInstance().getMapSectionSize()-1);
            }
        }
    }

    @Override
    public void updateEntityStatus(){
        if(entityStatus == EntityStatus.ALIVE){
            entityStatus = EntityStatus.DYING;
            currentAnimationStep = 0;
            setDeathAnimation();
        }else{
            entityStatus = EntityStatus.DEAD;
//            if(getAnimation() != null)
//                getAnimation().resetAnimation();
        }
    }
    @Override
    public void resetEntity(){
        //todo use this and move setup method from player
//        getAnimation().resetAnimation();
        //todo maybe non serve rimetterlo a 0
        currentWalkingStep = 0;
//        currentAnimation = GameEntity.WALK_ANIMATION_RIGHT;
//        isAlive = true;
//        isDying = false;
    }

    @Override
    public void changeCoordinate(){
        RENDERED_TILE_SIZE = GameDataConfig.getInstance().getRenderedTileSize();
        entityCoordinates.setHeight(RENDERED_TILE_SIZE);
        entityCoordinates.setWidth(RENDERED_TILE_SIZE);
        entityCoordinates.setTranslX(entityCoordinates.getTranslX()/VEL_X*(RENDERED_TILE_SIZE/TILE_STEP));
        entityCoordinates.setTranslY(entityCoordinates.getTranslY()/VEL_Y*(RENDERED_TILE_SIZE/TILE_STEP));
        VEL_Y = VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
    }


}

