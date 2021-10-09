package controller;

import commons.EntityType;

public class CoinHandler extends EntityHandler{
    public CoinHandler() {
        super(EntityType.COIN);
    }

    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {

    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {

    }
}
