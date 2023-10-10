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
    protected void collideWithEnemy(int entity, int externalEntity) {
        GameModel.getInstance().updateEntityStatus(EntityType.ENEMY,externalEntity);
        GameModel.getInstance().updateEntityStatus(EntityType.BULLET,entity);
    }

    @Override
    protected void collideWithCoin(int entity, int externalEntity) {
        //nothing to do
    }

    @Override
    protected void wallCollision(int entity) {
        GameModel.getInstance().updateEntityStatus(EntityType.BULLET,entity);
    }

}
