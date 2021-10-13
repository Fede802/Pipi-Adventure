package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;
import utils.GameDataConfig;

import java.util.HashMap;

public abstract class GameEntity implements IGameEntity{

    //    --------------------------------------------------------
//                       STATIC FIELD
//    --------------------------------------------------------
    public static int RENDERED_TILE_SIZE = GameDataConfig.getInstance().getRenderedTileSize();

    public static final int WALK_ANIMATION_RIGHT = 0;
    public static final int WALK_ANIMATION_LEFT = 1;
    public static final int DEATH_ANIMATION_RIGHT = 2;
    public static final int DEATH_ANIMATION_LEFT = 3;

    public static final int DEFAULT_DEATH_STEP = 100;

    //    --------------------------------------------------------
//                      INSTANCE FIELD
//    --------------------------------------------------------
    protected final EntityType ID;
    protected final HashMap<Integer, Animation> animationList = new HashMap<>();
    protected int deathStep = DEFAULT_DEATH_STEP;
    protected int currentDeathStep;
    protected EntityCoordinates entityCoordinates;
    protected boolean isAlive = true;
    protected boolean isDying = false;
    protected int currentAnimation = WALK_ANIMATION_RIGHT;
    //change to 4 8 5 10
    protected double TILE_STEP = 5.0;
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
    public void changeCoordinate(){
        RENDERED_TILE_SIZE = GameDataConfig.getInstance().getRenderedTileSize();
        entityCoordinates.setHeight(RENDERED_TILE_SIZE);
        entityCoordinates.setWidth(RENDERED_TILE_SIZE);
        entityCoordinates.setTranslX(entityCoordinates.getTranslX()/VEL_X*(RENDERED_TILE_SIZE/TILE_STEP));
        entityCoordinates.setTranslY(entityCoordinates.getTranslY()/VEL_Y*(RENDERED_TILE_SIZE/TILE_STEP));
        VEL_Y = VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
    }

    public EntityType getType(){
        return ID;
    }
}

