package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Utils.Config;
import Utils.GameConfig;

import java.util.ArrayList;

public abstract class GameEntity implements IGameEntity{
    //TODO this size could change maybe not could be final

    public static int RENDERED_TILE_SIZE = GameConfig.getInstance().getRenderedTileSize();
    public static final int WALK_ANIMATION_RIGHT = 0;
    public static final int WALK_ANIMATION_LEFT = 1;
    public static final int DEATH_ANIMATION = 2;

    protected final ArrayList<Animation> animationList = new ArrayList<>(){{add(WALK_ANIMATION_RIGHT,null);add(WALK_ANIMATION_LEFT,null);add(DEATH_ANIMATION,null);}};

    public static final int PLAYER_ID = 0;
    public static final int ENEMY_ID = 1;
    public static final int COIN_ID = 2;
    public static final int BULLET_ID = 3;

    public static final int DEATH_STEP = 60;

    protected int currentDeathStep = 0;
    protected EntityCoordinates entityCoordinates;
    protected boolean isAlive = true;
    protected boolean isDying = false;

    protected int currentAnimation = WALK_ANIMATION_RIGHT;

    public GameEntity(EntityCoordinates entityCoordinates){
        this.entityCoordinates = entityCoordinates;
    }

    @Override
    public void move() {
        RENDERED_TILE_SIZE = GameConfig.getInstance().getRenderedTileSize();
    }

    //TODO implement there part of move for death animation maybe
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
}

