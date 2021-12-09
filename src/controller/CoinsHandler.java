package controller;

import commons.EntityType;

public class CoinsHandler extends EntitiesHandler {

    public CoinsHandler() {
        super(EntityType.COIN);
    }

    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {
        //nothing to do
    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {
        //nothing to do
    }

    @Override
    protected void wallCollision(int currentEntity) {
        //nothing to do
    }

}
