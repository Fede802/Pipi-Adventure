package model;

import commons.EntityCoordinates;
import commons.EntityType;

public class FlyingEnemy extends GameEntity{

    public FlyingEnemy(EntityType id, EntityCoordinates entityCoordinates) {
        super(id,entityCoordinates);
        currentAnimation = GameEntity.WALK_ANIMATION_LEFT;
        TILE_STEP = 10.0;
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;

    }

    @Override
    public void move() {
        if(isAlive) {
            defaultWalkMovement(GameEntity.WALK_ANIMATION_LEFT);
        }
    }
    @Override
    public void setDeathAnimation() {
        currentAnimation = GameEntity.DEATH_ANIMATION_LEFT;
    }
}
