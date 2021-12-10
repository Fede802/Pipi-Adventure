package model;

import commons.*;

public class FlyingEnemy extends Enemy {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public FlyingEnemy(RenderingType R_ID, EntityCoordinates entityCoordinates) {
        super(R_ID, entityCoordinates);
        currentAnimation = AnimationData.WALK_ANIMATION_LEFT;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void move() {
        if(entityStatus == EntityStatus.ALIVE) {
            defaultWalkMovement(LEFT_DIR);
        }
    }

    @Override
    public void setDeathAnimation() {
        currentAnimation = AnimationData.DEATH_ANIMATION_LEFT;
    }

    @Override
    public void resetEntity() {
        //nothing to do
    }

}
