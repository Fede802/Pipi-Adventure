package model;

import commons.*;

public class FlyingEnemy extends Enemy{


    public FlyingEnemy(RenderingType R_ID, EntityCoordinates entityCoordinates) {
        super(R_ID, entityCoordinates);
        currentAnimation = commons.AnimationData.WALK_ANIMATION_LEFT;
    }

    @Override
    public void move() {

        if(entityStatus == EntityStatus.ALIVE) {
            defaultWalkMovement(LEFT_DIR);
        }
    }
    @Override
    public void setDeathAnimation() {
        currentAnimation = commons.AnimationData.DEATH_ANIMATION_LEFT;
    }
}
