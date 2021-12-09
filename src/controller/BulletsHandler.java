package controller;

import commons.EntityType;
import model.GameModel;

public class BulletsHandler extends EntitiesHandler {

    public BulletsHandler() {
        super(EntityType.BULLET);
    }

    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {
        GameModel.getInstance().updateEntitiesStatus(EntityType.ENEMY,outerCurrentEntity);
        GameModel.getInstance().updateEntitiesStatus(EntityType.BULLET,currentEntity);
    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {
        //nothing to do
    }

    @Override
    protected void wallCollision(int currentEntity) {
        GameModel.getInstance().updateEntitiesStatus(EntityType.BULLET,currentEntity);
    }

}
