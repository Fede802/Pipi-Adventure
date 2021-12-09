package controller;

import commons.EntityType;
import model.GameModel;

public class BulletsHandler extends EntitiesHandler {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public BulletsHandler() {
        super(EntityType.BULLET);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {
        GameModel.getInstance().updateEntityStatus(EntityType.ENEMY,outerCurrentEntity);
        GameModel.getInstance().updateEntityStatus(EntityType.BULLET,currentEntity);
    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {
        //nothing to do
    }

    @Override
    protected void wallCollision(int currentEntity) {
        GameModel.getInstance().updateEntityStatus(EntityType.BULLET,currentEntity);
    }

}
