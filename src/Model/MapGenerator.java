package model;

import commons.AnimationData;
import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import utils.GameDataConfig;
import java.util.ArrayList;
import java.util.Random;

public class MapGenerator implements IMapGenerator {

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    private final ArrayList<MapSection> SECTIONS_LIST = new ArrayList<>();
    private final ArrayList<GameEntity> COINS = new ArrayList<>();
    private final ArrayList<GameEntity> ENEMIES = new ArrayList<>();
    private final Random RANDOM = new Random();
    private final ArrayList<MapSection> GENERATED_MAP = new ArrayList<>();

    private boolean mapGenerated;
    private boolean daytime = true;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public MapGenerator() {
        SECTIONS_LIST.add(new PlainSection1());
        SECTIONS_LIST.add(new PlainSection2());
        SECTIONS_LIST.add(new PlainSection3());
        SECTIONS_LIST.add(new PlainSection4());
        SECTIONS_LIST.add(new PlainSection5());
        SECTIONS_LIST.add(new PlainSection6());
        SECTIONS_LIST.add(new PlainStartSection());
        generateMap();
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void generateMap() {
        mapGenerated = false;
        daytime = true;
        GENERATED_MAP.clear();
        COINS.clear();
        ENEMIES.clear();
        GENERATED_MAP.add(SECTIONS_LIST.get(SECTIONS_LIST.size()-1));
        addEntities(SECTIONS_LIST.size()-1,0);
        for (int i = 1; i< MAP_LENGTH; i++){
            GENERATED_MAP.add(SECTIONS_LIST.get(0));
            addEntities(0,i);
        }
        mapGenerated = true;
    }

    @Override
    public void updateMap() {
        updateEntitiesMapIndex();
        GENERATED_MAP.remove(0);
        //-1 because last section is the start section
        int nextSection = RANDOM.nextInt(SECTIONS_LIST.size()-1);
        GENERATED_MAP.add(SECTIONS_LIST.get(nextSection));
        addEntities(nextSection,MAP_LENGTH-1);
    }

    @Override
    public int getTileData(int mapIndex, int mapX, int mapY) {
        return GENERATED_MAP.get(mapIndex).getCell(mapX,mapY);
    }

    @Override
    public void updateDaytime() {
        daytime = !daytime;
    }

    @Override
    public void moveEntities() {
        //if entity is dead remove and and if not move it
        for (int i = COINS.size()-1; i>=0; i--){
            if(COINS.get(i).getEntityStatus() == EntityStatus.DEAD)
                COINS.remove(i);
            else{
                COINS.get(i).move();
            }
        }
        for (int i = ENEMIES.size()-1; i>=0; i--){
            if(ENEMIES.get(i).getEntityStatus() == EntityStatus.DEAD)
                ENEMIES.remove(i);
            else{
                ENEMIES.get(i).move();
            }
        }
    }

    @Override
    public int entityCount(EntityType entityType) {
        int count = ENEMIES.size();
        if(entityType == EntityType.COIN)
            count = COINS.size();
        return count;
    }

    @Override
    public EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus) {
        GameEntity e = getEntity(entityType,entityID,entityStatus);
        EntityCoordinates ec = null;
        if(e != null)
            ec = e.getEntityCoordinates();
        return ec;
    }

    @Override
    public AnimationData getEntityAnimation(EntityType entityType, int entityID) {
        return getEntity(entityType,entityID,EntityStatus.ALL).getAnimation();
    }

    @Override
    public void updateEntityAnimation(EntityType entityType, int entityID, AnimationData animation) {
        ArrayList<GameEntity> temp = ENEMIES;
        if(entityType == EntityType.COIN)
            temp = COINS;
        temp.get(entityID).updateAnimation(animation);
    }

    @Override
    public void updateEntityStatus(EntityType entityType, int entityID) {
        getEntity(entityType,entityID, EntityStatus.ALL).updateEntityStatus();
    }

    @Override
    public boolean isEntityDead(EntityType entityType, int entityID) {
        ArrayList<GameEntity> temp = ENEMIES;
        if(entityType == EntityType.COIN)
            temp = COINS;
        return temp.get(entityID).isDead();
    }

    @Override
    public void changeEntitiesCoordinates(int renderingTileSize) {
        for(int i = 0; i < COINS.size(); i++){
            COINS.get(i).changeCoordinate(renderingTileSize);
        }
        for(int i = 0; i < ENEMIES.size(); i++){
            ENEMIES.get(i).changeCoordinate(renderingTileSize);
        }
    }

    private void addEntities(int sectionIndex, int mapIndex) {
        ArrayList<GameEntity> temp;
        GameEntity gameEntity = null;
        if(daytime) {
            temp = SECTIONS_LIST.get(sectionIndex).getMapEntities(MapSection.DAY);
            if (mapGenerated)
            gameEntity = SECTIONS_LIST.get(sectionIndex).spawnFlyingEnemy(MapSection.DAY);
        } else {
            temp = SECTIONS_LIST.get(sectionIndex).getMapEntities(MapSection.NIGHT);
            if (mapGenerated)
            gameEntity = SECTIONS_LIST.get(sectionIndex).spawnFlyingEnemy(MapSection.NIGHT);
        }
        if (gameEntity != null){
            temp.add(gameEntity);
        }
        for(int j = 0; j< temp.size(); j++){
            GameEntity tempE= temp.get(j);
            tempE.getEntityCoordinates().setMapIndex(mapIndex);
            if(tempE.getType() == EntityType.COIN){
                COINS.add(tempE);
            }else if(tempE.getType() == EntityType.ENEMY){
                ENEMIES.add(tempE);
            }
        }
    }

    private void updateEntitiesMapIndex() {
        for(int i = COINS.size()-1; i>=0; i--){
            EntityCoordinates temp = COINS.get(i).getEntityCoordinates();
            temp.setMapIndex(temp.getMapIndex()-1);
            if(temp.getMapIndex()<0)
                COINS.remove(i);
        }
        for(int i = ENEMIES.size()-1; i>=0; i--){
            EntityCoordinates temp = ENEMIES.get(i).getEntityCoordinates();
            temp.setMapIndex(temp.getMapIndex()-1);
            if(temp.getMapIndex()<0)
                ENEMIES.remove(i);
        }

    }

    private GameEntity getEntity(EntityType entityType, int entityID, EntityStatus entityStatus) {
        ArrayList<GameEntity> temp = ENEMIES;
        if(entityType == EntityType.COIN)
            temp = COINS;
        GameEntity tempE = temp.get(entityID);
        if(entityStatus == EntityStatus.ALIVE && temp.get(entityID).getEntityStatus() != EntityStatus.ALIVE)
            tempE = null;
        return tempE;
    }

}
