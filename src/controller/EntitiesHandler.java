package controller;

import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import model.GameModel;

public abstract class EntitiesHandler {

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    protected EntityType entityType;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public EntitiesHandler(EntityType entityType){
        this.entityType = entityType;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public int getEntityCount(){
        return GameModel.getInstance().getEntityCount(entityType);
    }

    public void checkEntityCollision(EntitiesHandler entitiesHandler) {
        for(int i = getEntityCount()-1; i >= 0 ; i--){
            EntityCoordinates curr = getEntity(i,EntityStatus.ALIVE);
            EntityCoordinates currEnemy;
            if(curr != null)
                for(int j = entitiesHandler.getEntityCount()-1; j >= 0 ; j-- ){
                    currEnemy = entitiesHandler.getEntity(j,EntityStatus.ALIVE);
                    if(currEnemy != null)
                        if(CollisionHandler.collide(curr,currEnemy)){
                            switch (entitiesHandler.entityType){
                                case COIN -> collideWithCoin(i,j);
                                case PLAYER -> collideWithPlayer(i,j);
                                case ENEMY -> collideWithEnemy(i,j);
                                case BULLET -> collideWithBullet(i,j);
                    }
                }
            }
        }
    }

    public void rightCollision() {
        for (int i = getEntityCount() - 1; i >= 0; i--) {
            EntityCoordinates curr = getEntity(i, EntityStatus.ALIVE);
            if (curr != null && CollisionHandler.rightCollision(curr))
                wallCollision(i);

        }
    }

    protected boolean bottomCollision(int entityNum){
        return CollisionHandler.bottomCollision(getEntity(entityNum,EntityStatus.ALIVE));
    }

    protected EntityCoordinates getEntity(int entityNum, EntityStatus entityStatus) {
        return GameModel.getInstance().getEntityCoordinates(entityType,entityNum,entityStatus);
    }

    protected void collideWithPlayer(int entity, int externalEntity) {
        //nothing to do
    }

    protected void collideWithBullet(int entity, int externalEntity) {
        //nothing to do
    }

    //    --------------------------------------------------------
    //                      ABSTRACT METHODS
    //    --------------------------------------------------------

    protected abstract void wallCollision(int entity);

    protected abstract void collideWithCoin(int entity, int externalEntity);

    protected abstract void collideWithEnemy(int entity, int externalEntity);

}


