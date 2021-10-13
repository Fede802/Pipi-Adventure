package controller;

import commons.EntityCoordinates;
import commons.EntityType;
import model.GameModel;

public abstract class EntityHandler {
    protected EntityType entityType;
    protected int entityNum = -1;
    protected int currentEntity;
    public EntityHandler(EntityType entityType){
        this.entityType = entityType;
    }

    public int getEntityNum(EntityType entityStatus){
        return GameModel.getInstance().getEntityCount(entityType,entityStatus);

    }

    public boolean hasNext(EntityType entityStatus){
        boolean hasNext = true;
        if(entityNum == -1){
            entityNum = getEntityNum(entityStatus);
            currentEntity = entityNum;
        }

        if(currentEntity == 0){
            hasNext = false;
            entityNum = -1;
        }
        return hasNext;
    }
    public EntityCoordinates getNext(){
        this.currentEntity--;
        return GameModel.getInstance().getEntityCoordinates(entityType,this.currentEntity);
    }
    public EntityCoordinates getEntity(int entityNum){
        return GameModel.getInstance().getEntityCoordinates(entityType,entityNum);
    }
    public void checkEntityCollision(EntityHandler entityHandler){
        int count = 0;
        while(hasNext(EntityType.ALIVE)){
            EntityCoordinates curr = getNext();
            while(entityHandler.hasNext(EntityType.ALIVE)){
                if(CollisionHandler.collide(curr,entityHandler.getNext())){
                    switch (entityHandler.entityType){
                        case COIN -> collideWithCoin(getCurrentEntity(),entityHandler.getCurrentEntity());
                        case PLAYER -> collideWithPlayer(getCurrentEntity(),entityHandler.getCurrentEntity());
                        case ENEMY -> collideWithEnemy(getCurrentEntity(),entityHandler.getCurrentEntity());
                        case BULLET -> collideWithBullet(getCurrentEntity(),entityHandler.getCurrentEntity());
                    }
                }
                System.out.println(count);
                count++;
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            count = 0;

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


    protected int getCurrentEntity(){
        return currentEntity;
    }
}
