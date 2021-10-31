package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import utils.GameDataConfig;

import java.util.HashMap;

public abstract class GameEntity implements IGameEntity{

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------
    protected static int RENDERED_TILE_SIZE = GameDataConfig.getInstance().getRenderedTileSize();
    protected static final int WALK_ANIMATION_RIGHT = 0;
    protected static final int WALK_ANIMATION_LEFT = 1;
    protected static final int DEATH_ANIMATION_RIGHT = 2;
    protected static final int DEATH_ANIMATION_LEFT = 3;
    protected static final int DEFAULT_DEATH_LOOP = 1;
    protected static final int DEFAULT_WALKING_STEP = 96;

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------
    protected final EntityType ID;
    protected final HashMap<Integer, Animation> animationList = new HashMap<>();
    protected int deathLoop = DEFAULT_DEATH_LOOP;
    protected int currentDeathloop;
    protected int walkingStep = DEFAULT_WALKING_STEP;
    protected int currentWalkingStep;
    protected int currentAnimationStep;
    //todo add control for animationstep
    protected EntityCoordinates entityCoordinates;
    protected boolean isAlive = true;
    protected boolean isDying = false;
    protected int currentAnimation = WALK_ANIMATION_RIGHT;
    //change to 4 8 5 10
    protected double TILE_STEP = 5;
    protected double VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
    protected double VEL_Y = RENDERED_TILE_SIZE/TILE_STEP;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------
    public GameEntity(EntityType id, EntityCoordinates entityCoordinates){
        this.ID = id;
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
    public Animation getAnimation(boolean update) {
        Animation tempAnimation = animationList.get(currentAnimation);
        tempAnimation.setCurrentAnimationStep(currentAnimationStep);
        if(isDying)
            tempAnimation.setCurrentNumLoop(currentDeathloop);
        if(update){
            tempAnimation.update();
            currentAnimationStep = tempAnimation.getCurrentAnimationStep();
            if(isDying)
                currentDeathloop = tempAnimation.getCurrentNumLoop();
        }
        return animationList.get(currentAnimation);
    }

    @Override
    public Animation getAnimation() {
        return animationList.get(currentAnimation);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    @Override
    public boolean isDying() {
        return isDying;
    }

    @Override
    public void setDying(boolean dying) {
        isDying = dying;
    }

    @Override
    public boolean isDead() {
        return (isDying && currentDeathloop == deathLoop);
    }


    @Override
    public EntityType getType(){
        return ID;
    }

    protected void defaultWalkMovement(int movingDirection) {
        if(movingDirection == WALK_ANIMATION_RIGHT){
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
        if(isAlive){
            setAlive(false);
            setDying(true);
            currentAnimationStep = 0;
            setDeathAnimation();
        }else{
            setDying(false);
            if(getAnimation() != null)
                getAnimation().resetAnimation();
        }
    }
    @Override
    public void resetEntity(){
        getAnimation().resetAnimation();
        //todo maybe non serve rimetterlo a 0
        currentWalkingStep = 0;
        currentAnimation = GameEntity.WALK_ANIMATION_RIGHT;
        isAlive = true;
        isDying = false;
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

