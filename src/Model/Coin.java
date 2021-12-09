package model;

import commons.*;

public class Coin extends GameEntity {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public Coin(EntityCoordinates entityCoordinates) {
        super(EntityType.COIN, RenderingType.COIN,entityCoordinates);
        deathLoop = 5;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void move() {
        if(entityStatus == EntityStatus.DYING)
            entityCoordinates.updateTraslY(-VEL_Y);
    }

    @Override
    public void setDeathAnimation() {
        currentAnimation = commons.AnimationData.DEATH_ANIMATION_RIGHT;
    }

    @Override
    public void resetEntity() {
        //nothing to do
    }

}