package controller;

import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import model.GameModel;

public abstract class EntitiesHandler {

    protected EntityType entityType;

    public EntitiesHandler(EntityType entityType){
        this.entityType = entityType;
    }

    public int getEntityCount(){
        return GameModel.getInstance().getEntityCount(entityType);
    }

    protected EntityCoordinates getEntity(int entityNum, EntityStatus entityStatus) {
        return GameModel.getInstance().getEntityCoordinates(entityType,entityNum,entityStatus);
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

    protected void collideWithPlayer(int currentEntity, int outerCurrentEntity) {
        //nothing to do
    }

    protected void collideWithBullet(int currentEntity, int outerCurrentEntity) {
        //nothing to do
    }

    public void rightCollision() {
        for (int i = getEntityCount() - 1; i >= 0; i--) {
            EntityCoordinates curr = getEntity(i, EntityStatus.ALIVE);
            if (curr != null && CollisionHandler.rightCollision(curr))
                wallCollision(i);

        }
    }

    protected abstract void wallCollision(int currentEntity);

    protected abstract void collideWithCoin(int currentEntity, int outerCurrentEntity);

    protected abstract void collideWithEnemy(int currentEntity, int outerCurrentEntity);

}


