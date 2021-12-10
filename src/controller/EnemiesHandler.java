package controller;

import commons.EntityType;

public class EnemiesHandler extends EntitiesHandler {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public EnemiesHandler() {
        super(EntityType.ENEMY);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
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
