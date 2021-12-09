package model;

import commons.*;
import utils.GameDataConfig;

public abstract class GameEntity implements IGameEntity {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    protected static int RENDERED_TILE_SIZE;
    protected static final int RIGHT_DIR = 0;
    protected static final int LEFT_DIR = 1;
    protected static final int DEFAULT_DEATH_LOOP = 1;
    protected static final int DEFAULT_WALKING_STEP = 96;
    protected static final AnimationData TEMP_ANIMATION = new AnimationData();

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    protected final EntityType ID;
    protected final RenderingType R_ID;

    protected EntityStatus entityStatus = EntityStatus.ALIVE;
    protected EntityCoordinates entityCoordinates;
    protected int deathLoop = DEFAULT_DEATH_LOOP;
    protected int currentDeathLoop;
    protected int walkingStep = DEFAULT_WALKING_STEP;
    protected int currentWalkingStep;
    protected int currentAnimationStep;
    protected int currentAnimation = AnimationData.WALK_ANIMATION_RIGHT;
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
    public EntityType getType(){
        return ID;
    }

    @Override
    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    @Override
    public void updateEntityStatus() {
        if(entityStatus == EntityStatus.ALIVE){
            entityStatus = EntityStatus.DYING;
            currentAnimationStep = 0;
            setDeathAnimation();
        }else{
            entityStatus = EntityStatus.DEAD;
        }
    }

    @Override
    public boolean isDead() {
        return (entityStatus == EntityStatus.DYING && currentDeathLoop == deathLoop);
    }

    @Override
    public EntityCoordinates getEntityCoordinates() {
        return entityCoordinates;
    }

    @Override
    public AnimationData getAnimation() {
        TEMP_ANIMATION.setupAnimation(currentAnimationStep,currentAnimation,R_ID);
        if(entityStatus == EntityStatus.DYING)
            TEMP_ANIMATION.setCurrentNumLoop(currentDeathLoop);
        return TEMP_ANIMATION;
    }

    @Override
    public void updateAnimationData(AnimationData animationData) {
        currentAnimationStep = animationData.getCurrentAnimationStep();
        if(entityStatus == EntityStatus.DYING)
            currentDeathLoop = animationData.getCurrentNumLoop();
    }

    @Override
    public void changeCoordinate(int renderedTileSize) {
        RENDERED_TILE_SIZE = renderedTileSize;
        entityCoordinates.setHeight(renderedTileSize);
        entityCoordinates.setWidth(renderedTileSize);
        entityCoordinates.setTraslX(entityCoordinates.getTraslX()/VEL_X*(renderedTileSize/TILE_STEP));
        entityCoordinates.setTraslY(entityCoordinates.getTraslY()/VEL_Y*(renderedTileSize/TILE_STEP));
        VEL_Y = VEL_X = renderedTileSize/TILE_STEP;
    }

    protected void defaultWalkMovement(int movingDirection) {
        if(movingDirection == RIGHT_DIR){
            entityCoordinates.updateTraslX(VEL_X);
            if(entityCoordinates.getTraslX() >= RENDERED_TILE_SIZE){
                entityCoordinates.setTraslX(entityCoordinates.getTraslX()-RENDERED_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
            }
            if(entityCoordinates.getMapX() == GameDataConfig.getInstance().getMapSectionSize()){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
                entityCoordinates.setMapX(0);
            }
        }else{
            entityCoordinates.updateTraslX(-VEL_X);
            if(Math.abs(entityCoordinates.getTraslX()) >= RENDERED_TILE_SIZE) {
                entityCoordinates.setTraslX(entityCoordinates.getTraslX() + RENDERED_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX() - 1);
            }
            if(entityCoordinates.getMapX() == -1){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()-1);
                entityCoordinates.setMapX(GameDataConfig.getInstance().getMapSectionSize()-1);
            }
        }
    }

}

