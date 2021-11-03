package model;

import commons.EntityCoordinates;
import commons.EntityType;
import utils.GameDataConfig;

public abstract class EarthEnemy extends GameEntity{

    public EarthEnemy(EntityType id, EntityCoordinates entityCoordinates) {
        super(id,entityCoordinates);
        TILE_STEP = 10.0;
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;

    }

    @Override
    public void move() {
        if(isAlive) {
            if (currentWalkingStep < walkingStep / 2) {
                defaultWalkMovement(GameEntity.WALK_ANIMATION_RIGHT);
            } else {
               defaultWalkMovement(GameEntity.WALK_ANIMATION_LEFT);
            }
            currentWalkingStep++;
            if (currentWalkingStep == walkingStep / 2) {
                currentAnimation = GameEntity.WALK_ANIMATION_LEFT;
            }
            if (currentWalkingStep == walkingStep) {
                currentAnimation = GameEntity.WALK_ANIMATION_RIGHT;
                currentWalkingStep = 0;
            }
        }
    }
    @Override
    public void setDeathAnimation() {
        //todo check if befaore is walking right or left
        if(currentAnimation == GameEntity.WALK_ANIMATION_RIGHT)
            currentAnimation = GameEntity.DEATH_ANIMATION_RIGHT;
        else
            currentAnimation = GameEntity.DEATH_ANIMATION_LEFT;
    }
}
