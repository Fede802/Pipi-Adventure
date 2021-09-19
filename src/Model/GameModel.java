package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;

import java.util.ArrayList;

public class GameModel implements IGameModel{
    private static final MapGenerator MAP_GENERATOR = new MapGenerator();
    //    private static final GameStatus GAME_STATUS = new GameStatus();
    private static final Player PLAYER = new Player(new EntityCoordinates.Builder(2,12,EntityCoordinates.PLAYER_ID).build());

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
    }

    @Override
    public double getMapTraslX() {
        return MAP_GENERATOR.getMapTraslX();
    }

    @Override
    public void setMapTraslX(final double mapTraslX) {
        MAP_GENERATOR.setMapTraslX(mapTraslX);
    }

    @Override
    public void updateMapTraslX() {
        MAP_GENERATOR.updateMapTraslX(Player.PLAYER_VEL_X);
    }

    @Override
    public int getTileData(final int mapIndex, final int mapX, final int mapY) {
        return MAP_GENERATOR.getTileData(mapIndex,mapX,mapY);
    }

    @Override
    public int getSectionSize() {
        return MapSection.SECTION_SIZE;
    }

    @Override
    public int getMapLength() {
        return MapGenerator.MAP_LENGTH;
    }

    @Override
    public EntityCoordinates getPlayerCoordinates() {
        return PLAYER.getEntityCoordinates();
    }

    @Override
    public ArrayList<EntityCoordinates> getBullets() {
        return PLAYER.getBulletsCoordinate();
    }

    @Override
    public ArrayList<EntityCoordinates> getEntitiesCoordinates() {
        return MAP_GENERATOR.getEntitiesCoordinates();
    }

    @Override
    public ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesForRendering() {
        ArrayList<Pair<EntityCoordinates, Animation>> entitiesCoordinates = MAP_GENERATOR.getMapEntitiesCoordinates();
        entitiesCoordinates.add(new Pair<>(PLAYER.getEntityCoordinates(), PLAYER.getAnimation()));
        entitiesCoordinates.addAll(PLAYER.getBulletsForRendering());
        return entitiesCoordinates;
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
    public void updatePlayerMapPosition() {
        PLAYER.move();
    }

    @Override
    public void updateEntitiesMapPosition() {
        MAP_GENERATOR.updateEntitiesMapPosition();
    }

    @Override
    public void updateEntitiesStatus(int entityID,int parent) {
        if(parent == 0){
            PLAYER.updateBulletStatus(entityID);
        }else
            MAP_GENERATOR.updateEntitiesStatus(entityID);
    }

    @Override
    public void changeCoordinate() {
        PLAYER.changeCoordinate();
        MAP_GENERATOR.changeCoordinate();
    }

    @Override
    public void printPlayerInfo() {
        System.out.println("MapX" +PLAYER.getEntityCoordinates().getSTART_MAP_X());
        System.out.println("TraslX "+PLAYER.getEntityCoordinates().getTranslX());
        System.out.println("MapIndex "+PLAYER.getEntityCoordinates().getMapIndex());
        System.out.println("TraslY "+PLAYER.getEntityCoordinates().getTranslY());

    }

    @Override
    public void setup() {
        MAP_GENERATOR.generateMap();
        PLAYER.getEntityCoordinates().setTranslX(0);
        PLAYER.getEntityCoordinates().setTranslY(0);
    }


}
