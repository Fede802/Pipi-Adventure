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
            entityCoordinates.updateTranslY(-velY);
    }

    @Override
    public void setDeathAnimation() {
        currentAnimation = AnimationData.DEATH_ANIMATION_RIGHT;
    }

    @Override
    public void resetEntity() {
        //nothing to do
    }

}