package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;
import commons.Pair;
import utils.GameDataConfig;

import java.util.ArrayList;

public class GameModel implements IGameModel{
    private static final MapGenerator MAP_GENERATOR = new MapGenerator();
    //    private static final GameStatus GAME_STATUS = new GameStatus();
    private static final Player PLAYER = new Player(new EntityCoordinates.Builder(GameDataConfig.getInstance().getPlayerStartMapX(), GameDataConfig.getInstance().getPlayerStartMapY()).build());

    private static GameModel instance = null;
    private GameModel(){}
    public static IGameModel getInstance() {
        if (instance == null){
            instance = new GameModel();
        }
        return instance;
    }

    @Override
    public void updateMap() {
        MAP_GENERATOR.updateMap();
        PLAYER.updateBulletsIndex();
    }

    @Override
    public int getTileData(final int mapIndex, final int mapX, final int mapY) {
        return MAP_GENERATOR.getTileData(mapIndex,mapX,mapY);
    }






    @Override
    public boolean isPlayerJumping() {
        return PLAYER.isJumping();
    }

    @Override
    public void setPlayerJumping(boolean isPlayerJumping) {
        PLAYER.setJumping(isPlayerJumping);
    }

    @Override
    public void playerJump() {
        PLAYER.jump();
    }

    @Override
    public void playerFall() {
        PLAYER.fall();
    }

    @Override
    public void shoot() {
        PLAYER.shoot();
        System.out.println("shoot");
    }

    @Override
    public EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID) {
        EntityCoordinates entity = null;
        switch(entityType){
            case PLAYER -> entity = PLAYER.getEntityCoordinates();
            case COIN, ENEMY -> entity = MAP_GENERATOR.getEntityCoordinates(entityType,entityID);
            case BULLET -> entity = PLAYER.getBulletCoordinate(entityID);
            case ALL -> {
                int bulletCount = getEntityCount(EntityType.BULLET,EntityType.ALL);
                if(entityID == 0){
                    entity = PLAYER.getEntityCoordinates();
                }else if(entityID <= bulletCount){
                    entityID--;
                    entity = PLAYER.getBulletCoordinate(entityID);
                }else{
                    entityID-=(bulletCount +1);
                    entity = MAP_GENERATOR.getEntityCoordinates(entityID);
                }
            }
        }
        return entity;
    }

    @Override
    public Animation getEntityAnimation(int entityID) {
        Animation animation= null;
        int bulletCount = getEntityCount(EntityType.BULLET,EntityType.ALL);
        if(entityID == 0){
            animation = PLAYER.getAnimation();
        }else if(entityID <= bulletCount){
            entityID--;
            animation = PLAYER.getBulletAnimation(entityID);
        }else{
            entityID-=(bulletCount +1);
            animation = MAP_GENERATOR.getEntityAnimation(entityID);
        }
        return animation;
    }

    @Override
    public int getEntityCount(EntityType entityType, EntityType entityStatus) {
        int count = 0;
        switch(entityType){
            case PLAYER -> count = 1;
            case COIN, ENEMY -> count = MAP_GENERATOR.entityCount(entityType,entityStatus);
            case BULLET -> count = PLAYER.bulletCount(entityStatus);
        }
        return count;
    }



    @Override
    public void updateEntitiesStatus(EntityType entityType, int entityID) {
        switch(entityType){
            //todo case player
            case COIN, ENEMY -> MAP_GENERATOR.updateEntitiesStatus(entityType,entityID);
            case BULLET -> PLAYER.updateBulletStatus(entityID);
        }
    }


    @Override
    public void changeCoordinate() {
        PLAYER.changeCoordinate();
        MAP_GENERATOR.changeCoordinate();
    }

    @Override
    public void setup() {
        MAP_GENERATOR.generateMap();
        PLAYER.getEntityCoordinates().setTranslX(0);
        PLAYER.getEntityCoordinates().setTranslY(0);
    }

    @Override
    public void moveEntity() {
        PLAYER.move();
        PLAYER.moveBullet();
        MAP_GENERATOR.moveEntities();
    }

}

