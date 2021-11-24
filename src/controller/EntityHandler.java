package controller;

import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import model.GameModel;

public abstract class EntityHandler {
    protected EntityType entityType;
    public EntityHandler(EntityType entityType){
        this.entityType = entityType;
    }

    public int getEntityNum(){
        return GameModel.getInstance().getEntityCount(entityType);
    }


    public EntityCoordinates getEntity(int entityNum, EntityStatus entityStatus){
        return GameModel.getInstance().getEntityCoordinates(entityType,entityNum,entityStatus);
    }
    public void checkEntityCollision(EntityHandler entityHandler){
        for(int i = getEntityNum()-1; i >= 0 ; i--){
            EntityCoordinates curr = getEntity(i,EntityStatus.ALIVE);
            EntityCoordinates currEnemy;
            if(curr == null)
                continue;
            for(int j =entityHandler.getEntityNum()-1; j >= 0 ; j-- ){
                currEnemy = entityHandler.getEntity(j,EntityStatus.ALIVE);
                if(currEnemy == null)
                    continue;
                if(CollisionHandler.collide(curr,currEnemy)){
                    switch (entityHandler.entityType){
                        case COIN -> collideWithCoin(i,j);
                        case PLAYER -> collideWithPlayer(i,j);
                        case ENEMY -> collideWithEnemy(i,j);
                        case BULLET -> collideWithBullet(i,j);
                    }
                }
            }
        }
    }

    protected void collideWithBullet(int currentEntity, int outerCurrentEntity){
        //nothing to do
    };

    protected abstract void collideWithEnemy(int currentEntity, int outerCurrentEntity);

    protected void collideWithPlayer(int currentEntity, int outerCurrentEntity){
        //nothing to do
    };

    protected abstract void collideWithCoin(int currentEntity, int outerCurrentEntity);

    public void rigthCollision() {
        for (int i = getEntityNum() - 1; i >= 0; i--) {
            EntityCoordinates curr = getEntity(i, EntityStatus.ALIVE);
            if (curr != null && CollisionHandler.rigthCollision(curr))
                wallCollision(i);

        }
    }
    protected abstract void wallCollision(int currentEntity);
}


