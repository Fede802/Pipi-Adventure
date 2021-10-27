package controller;

import commons.EntityType;

public class EnemyHandler extends EntityHandler {
    public EnemyHandler() {
        super(EntityType.ENEMY);
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
