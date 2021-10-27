package model;

import commons.*;
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
    public void changeDaytime() {
        MAP_GENERATOR.updateDayTime();
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
    public EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus) {
        EntityCoordinates entity = null;
        switch(entityType){
            //todo player when is not alive has to return null as other method?
            case PLAYER -> entity = PLAYER.getEntityCoordinates();
            case COIN, ENEMY -> entity = MAP_GENERATOR.getEntityCoordinates(entityType,entityID,entityStatus);
            case BULLET -> entity = PLAYER.getBulletCoordinate(entityID,entityStatus);
        }
        return entity;
    }

    @Override
    public Animation getEntityAnimation(EntityType entityType, int entityID) {
        Animation animation= null;
        switch(entityType){
            case PLAYER -> animation = PLAYER.getAnimation();
            case COIN, ENEMY -> animation = MAP_GENERATOR.getEntityAnimation(entityType,entityID);
            case BULLET -> animation = PLAYER.getBulletAnimation(entityID);
        }
        return animation;
    }

    @Override
    public boolean isDead(EntityType entityType, int entityID) {
        boolean dead = false;
        switch(entityType){
            case PLAYER -> dead = PLAYER.isDead();
            case COIN, ENEMY -> dead = MAP_GENERATOR.isDead(entityType,entityID);
            case BULLET -> dead = PLAYER.isBulletDead(entityID);
        }
        return dead;
    }



    @Override
    public int getEntityCount(EntityType entityType) {
        int count = 0;
        switch(entityType){
            case PLAYER -> count = 1;
            case COIN, ENEMY -> count = MAP_GENERATOR.entityCount(entityType);
            case BULLET -> count = PLAYER.bulletCount();
        }
        return count;
    }



    @Override
    public void updateEntitiesStatus(EntityType entityType, int entityID) {
        switch(entityType){
            case PLAYER -> PLAYER.updateEntityStatus();
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
        PLAYER.getEntityCoordinates().setMapX(GameDataConfig.getInstance().getPlayerStartMapX());
        PLAYER.getEntityCoordinates().setMapY(GameDataConfig.getInstance().getPlayerStartMapY());
        PLAYER.getEntityCoordinates().setMapIndex(0);
        PLAYER.getEntityCoordinates().setTranslX(0);
        PLAYER.getEntityCoordinates().setTranslY(0);
        PLAYER.setup();
    }

    @Override
    public void moveEntity() {
        PLAYER.move();
        PLAYER.moveBullets();
        MAP_GENERATOR.moveEntities();
    }

}

