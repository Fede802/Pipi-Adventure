package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import utils.GameDataConfig;

public class GameModel implements IGameModel {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static IGameModel instance;

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final MapGenerator MAP_GENERATOR = new MapGenerator();
    private final Player PLAYER = new Player(
            new EntityCoordinates.Builder(
                    GameDataConfig.getInstance().getPlayerStartMapX(),
                    GameDataConfig.getInstance().getPlayerStartMapY())
                    .build()
            );

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private GameModel(){}

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void updateMap() {
        MAP_GENERATOR.updateMap();
        PLAYER.updateBulletsMapIndex();
    }

    @Override
    public int getTileData(int mapIndex, int mapX, int mapY) {
        return MAP_GENERATOR.getTileData(mapIndex,mapX,mapY);
    }

    @Override
    public void changeDaytime() {
        MAP_GENERATOR.updateDaytime();
    }

    @Override
    public void moveEntities() {
        PLAYER.move();
        PLAYER.moveBullets();
        MAP_GENERATOR.moveEntities();
    }

    @Override
    public void setPlayerJumping(boolean jumping) {
        PLAYER.setJumping(jumping);
    }

    @Override
    public void playerJump() {
        PLAYER.jump();
    }

    @Override
    public void setPlayerFalling(boolean falling) {
        PLAYER.setFalling(falling);
    }

    @Override
    public void playerFall() {
        PLAYER.fall();
    }

    @Override
    public void shoot() {
        PLAYER.shoot();
    }

    @Override
    public int getEntityCount(EntityType entityType) {
        int count = 0;
        switch(entityType){
            case PLAYER -> count = 1;
            case COIN, ENEMY -> count = MAP_GENERATOR.entityCount(entityType);
            case BULLET -> count = PLAYER.bulletsCount();
        }
        return count;
    }

    @Override
    public void updateEntityStatus(EntityType entityType, int entityID) {
        switch(entityType){
            case PLAYER -> PLAYER.updateEntityStatus();
            case COIN, ENEMY -> MAP_GENERATOR.updateEntityStatus(entityType,entityID);
            case BULLET -> PLAYER.updateBulletStatus(entityID);
        }
    }

    @Override
    public EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus) {
        EntityCoordinates entity = null;
        switch(entityType){
            case PLAYER -> entity = PLAYER.getEntityCoordinates();
            case COIN, ENEMY -> entity = MAP_GENERATOR.getEntityCoordinates(entityType,entityID,entityStatus);
            case BULLET -> entity = PLAYER.getBulletCoordinates(entityID,entityStatus);
        }
        return entity;
    }

    @Override
    public AnimationData getEntityAnimation(EntityType entityType, int entityID) {
        AnimationData animation= null;
        switch(entityType){
            case PLAYER -> animation = PLAYER.getAnimation();
            case COIN, ENEMY -> animation = MAP_GENERATOR.getEntityAnimation(entityType,entityID);
            case BULLET -> animation = PLAYER.getBulletAnimation(entityID);
        }
        return animation;
    }

    @Override
    public void updateEntityAnimation(EntityType entityType, int entityID, AnimationData animation) {
        switch(entityType){
            case PLAYER -> PLAYER.updateAnimation(animation);
            case COIN, ENEMY -> MAP_GENERATOR.updateEntityAnimation(entityType,entityID,animation);
            case BULLET ->  PLAYER.updateBulletAnimation(entityID,animation);
        }
    }

    @Override
    public void updatePlayerAnimationOpacity(float opacity) {
        PLAYER.updateAnimationOpacity(opacity);
    }

    @Override
    public boolean isEntityDead(EntityType entityType, int entityID) {
        boolean dead = false;
        switch(entityType){
            case PLAYER -> dead = PLAYER.isDead();
            case COIN, ENEMY -> dead = MAP_GENERATOR.isEntityDead(entityType,entityID);
            case BULLET -> dead = PLAYER.isBulletDead(entityID);
        }
        return dead;
    }

    @Override
    public void changeEntitiesCoordinates(int renderingTileSize) {
        PLAYER.changeCoordinate(renderingTileSize);
        MAP_GENERATOR.changeEntitiesCoordinates(renderingTileSize);
    }

    @Override
    public void setup() {
        MAP_GENERATOR.generateMap();
        PLAYER.resetEntity();
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static IGameModel getInstance() {
        if (instance == null){
            instance = new GameModel();
        }
        return instance;
    }

}

