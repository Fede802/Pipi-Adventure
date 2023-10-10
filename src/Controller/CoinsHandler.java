package controller;

import commons.EntityType;

public class CoinsHandler extends EntitiesHandler {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public CoinsHandler() {
        super(EntityType.COIN);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHOD
    //    --------------------------------------------------------

    @Override
    protected void collideWithEnemy(int entity, int externalEntity) {
        //nothing to do
    }

    @Override
    protected void collideWithCoin(int entity, int externalEntity) {
        //nothing to do
    }

    @Override
    protected void wallCollision(int entity) {
        //nothing to do
    }

}
