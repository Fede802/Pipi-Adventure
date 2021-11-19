package model;

import commons.*;

public class EarthEnemy extends Enemy{


    public EarthEnemy(RenderingType R_ID, EntityCoordinates entityCoordinates) {
        super(R_ID, entityCoordinates);
    }

    @Override
    public void move() {
        if(entityStatus == EntityStatus.ALIVE) {
            if (currentWalkingStep < walkingStep / 2) {
                defaultWalkMovement(RIGHT_DIR);
            } else {
               defaultWalkMovement(GameEntity.LEFT_DIR);
            }
            currentWalkingStep++;
            if (currentWalkingStep == walkingStep / 2) {
                currentAnimation = commons.AnimationData.WALK_ANIMATION_LEFT;
            }
            if (currentWalkingStep == walkingStep) {
                currentAnimation = commons.AnimationData.WALK_ANIMATION_RIGHT;
                currentWalkingStep = 0;
            }
        }
    }
    @Override
    public void setDeathAnimation() {
        //todo check if befaore is walking right or left
        if(currentAnimation == commons.AnimationData.WALK_ANIMATION_RIGHT)
            currentAnimation = commons.AnimationData.DEATH_ANIMATION_RIGHT;
        else
            currentAnimation = commons.AnimationData.DEATH_ANIMATION_LEFT;
    }
}