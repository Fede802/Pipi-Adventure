package controller;

import commons.EntityCoordinates;
import commons.EntityType;
import model.GameModel;
import utils.GameDataConfig;

public class BulletsHandler extends EntityHandler{
    public BulletsHandler() {
        super(EntityType.BULLET);
    }


    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {
        GameModel.getInstance().updateEntitiesStatus(EntityType.ENEMY,outerCurrentEntity);
    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {

    }
}
