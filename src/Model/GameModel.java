package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pairs;
import javafx.util.Pair;


import java.util.ArrayList;
import java.util.Map;

public class GameModel implements IGameModel{

    private static GameModel instance = null;
    private final MapGenerator mapGenerator = new MapGenerator();
    private final Player player = new Player(new EntityCoordinates.Builder(2,12,GameEntity.PLAYER_ID).setMapIndex(0).build());
    private GameModel(){}
    public static IGameModel getInstance() {
        if (instance == null){
            instance = new GameModel();
            System.out.println("model");
        }
        return instance;
    }

    @Override
    public int getSectionSize() {
        return MapSection.SECTION_SIZE;
    }

    @Override
    public int getMapLength() {
        return MapGenerator.MAP_LENGHT;
    }

    @Override
    public int getMapTraslX() {
        return mapGenerator.getMapTraslX();
    }

    @Override
    public int getTileData(final int mapIndex,final int mapX,final int mapY) {
        return mapGenerator.getTileData(mapIndex,mapX,mapY);
    }

    @Override
    public void setMapTraslX(final int mapTraslX) {
        mapGenerator.setMapTraslX(mapTraslX);
    }

    @Override
    public void updateGameStatus() {
        mapGenerator.setMapTraslX(mapGenerator.getMapTraslX()+MapGenerator.MAP_VEL_X);
        player.update(player.getEntityCoordinates().getTraslX()+MapGenerator.MAP_VEL_X);
        mapGenerator.updateMapEntity();
        mapGenerator.collision(player);
    }

    @Override
    public void updateMap() {
        mapGenerator.updateMap();
    }

    @Override
    public ArrayList<Pairs<EntityCoordinates,Animation>> getEntityCoordinates() {
        ArrayList<Pairs<EntityCoordinates,Animation>> mapEntities = mapGenerator.getMapEntitiesCoordinates();
        mapEntities.add(new Pairs<>(player.getEntityCoordinates(),player.getAnimation()));
        return mapEntities;
    }

    @Override
    public EntityCoordinates getPlayerCoordinates() {
        return player.getEntityCoordinates();
    }



    @Override
    public void setJumping(boolean jumping) {
        player.setJumping(jumping);
    }

    @Override
    public boolean isJumping() {
        return player.isJumping();
    }

    @Override
    public void jump() {
        player.jump();
    }

    @Override
    public void fall() {
        player.fall();
    }

//    @Override
//    public int[] getPlayerInfo() {
//        return player.getPlayerInfo();
//    }

//    @Override
//    public void setPlayerInfo(final int mapIndex, final int mapX, final int traslX, final int mapY, final int traslY) {
//        player.setPlayerInfo(mapIndex,mapX,traslX,mapY,traslY);
//    }

//    @Override
//    public void setJumping(final boolean jumping) {
//        player.setJumping(jumping);
//    }
//
//    @Override
//    public boolean isJumping() {
//        return player.isJumping();
//    }
//
//    @Override
//    public void jump() {
//        player.jump();
//    }
//
//    @Override
//    public void fall() {
//        player.fall();
//    }
}