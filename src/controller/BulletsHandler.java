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
        GameModel.getInstance().updateEntitiesStatus(EntityType.ENEMY,outerCurrentEntity);
        GameModel.getInstance().updateEntitiesStatus(EntityType.BULLET,currentEntity);
        System.out.println("COLLIDEEEEEEE");
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
