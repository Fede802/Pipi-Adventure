package controller;

import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;

public class BulletsHandler extends EntityHandler{
    public BulletsHandler() {
        super(EntityType.BULLET);
    }


    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {
        GameModel.getInstance().updateEntitiesStatus(EntityType.ENEMY,outerCurrentEntity, EntityStatus.DYING);
        GameModel.getInstance().updateEntitiesStatus(EntityType.BULLET,currentEntity,EntityStatus.DYING);
    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {
        //nothing to do
    }
}
