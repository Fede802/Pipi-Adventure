package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import commons.RenderingType;
import utils.GameDataConfig;

public abstract class GameEntity implements IGameEntity {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    protected static int RENDERING_TILE_SIZE;
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
    //it can be changed with numbers that give a finite result in the role of divisors
    protected double tileStep = 5.0;
    protected double velX = RENDERING_TILE_SIZE / tileStep;
    protected double velY = RENDERING_TILE_SIZE / tileStep;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public GameEntity(EntityType ID,RenderingType R_ID, EntityCoordinates entityCoordinates){
        this.ID = ID;
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
    public void updateAnimation(AnimationData animation) {
        currentAnimationStep = animation.getCurrentAnimationStep();
        if(entityStatus == EntityStatus.DYING)
            currentDeathLoop = animation.getCurrentNumLoop();
    }

    @Override
    public void changeCoordinate(int renderingTileSize) {
        RENDERING_TILE_SIZE = renderingTileSize;
        entityCoordinates.setHeight(renderingTileSize);
        entityCoordinates.setWidth(renderingTileSize);
        entityCoordinates.setTranslX(entityCoordinates.getTranslX()/ velX *(renderingTileSize/ tileStep));
        entityCoordinates.setTranslY(entityCoordinates.getTranslY()/ velY *(renderingTileSize/ tileStep));
        velY = velX = renderingTileSize/ tileStep;
    }

    protected void defaultWalkMovement(int movingDirection) {
        if(movingDirection == RIGHT_DIR){
            entityCoordinates.updateTranslX(velX);
            if(entityCoordinates.getTranslX() >= RENDERING_TILE_SIZE){
                entityCoordinates.setTranslX(entityCoordinates.getTranslX()- RENDERING_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
            }
            if(entityCoordinates.getMapX() == GameDataConfig.getInstance().getMapSectionSize()){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
                entityCoordinates.setMapX(0);
            }
        }else{
            entityCoordinates.updateTranslX(-velX);
            if(Math.abs(entityCoordinates.getTranslX()) >= RENDERING_TILE_SIZE) {
                entityCoordinates.setTranslX(entityCoordinates.getTranslX() + RENDERING_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX() - 1);
            }
            if(entityCoordinates.getMapX() == -1){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()-1);
                entityCoordinates.setMapX(GameDataConfig.getInstance().getMapSectionSize()-1);
            }
        }
    }

}

