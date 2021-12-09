package model;

import commons.*;
import utils.GameDataConfig;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator implements IMapGenerator {

    private final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    private final ArrayList<MapSection> SECTION_LIST = new ArrayList<>();
    private final ArrayList<GameEntity> COINS = new ArrayList<>();
    private final ArrayList<GameEntity> ENEMY = new ArrayList<>();
    private final Random RANDOM = new Random();
    private final ArrayList<MapSection> GENERATED_MAP = new ArrayList<>();

    private boolean mapGenerated;
    private boolean dayTime = true;

    public MapGenerator() {
        SECTION_LIST.add(new PlainSection1());
        SECTION_LIST.add(new PlainSection2());
        SECTION_LIST.add(new PlainSection3());
        SECTION_LIST.add(new PlainSection4());
        SECTION_LIST.add(new PlainSection5());
        SECTION_LIST.add(new PlainSection6());
        SECTION_LIST.add(new PlainStartSection());
        generateMap();
    }

    @Override
    public void generateMap() {
        mapGenerated = false;
        dayTime = true;
        GENERATED_MAP.clear();
        COINS.clear();
        ENEMY.clear();
        GENERATED_MAP.add(SECTION_LIST.get(SECTION_LIST.size()-1));
        addEntities(SECTION_LIST.size()-1,0);
        for (int i = 1; i< MAP_LENGTH; i++){
            GENERATED_MAP.add(SECTION_LIST.get(0));
            addEntities(0,i);
        }
        mapGenerated = true;
    }

    @Override
    public void updateMap() {
        updateEntitiesMapIndex();
        GENERATED_MAP.remove(0);
        //-1 because last section is the start section
        int nextSection = RANDOM.nextInt(SECTION_LIST.size()-1);
        GENERATED_MAP.add(SECTION_LIST.get(nextSection));
        addEntities(nextSection,MAP_LENGTH-1);
    }

    @Override
    public int getTileData(final int mapIndex,final int mapX,final int mapY) {
        return GENERATED_MAP.get(mapIndex).getCell(mapX,mapY);
    }

    @Override
    public void updateDayTime() {
        dayTime = !dayTime;
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
        for (int i = ENEMY.size()-1; i>=0; i--){
            if(ENEMY.get(i).getEntityStatus() == EntityStatus.DEAD)
                ENEMY.remove(i);
            else{
                ENEMY.get(i).move();
            }
        }
    }

    @Override
    public int entityCount(EntityType entityType) {
        int count = ENEMY.size();
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
    public void updateAnimationData(EntityType entityType, int entityID, AnimationData animation) {
        ArrayList<GameEntity> temp = ENEMY;
        if(entityType == EntityType.COIN)
            temp = COINS;
        temp.get(entityID).updateAnimationData(animation);
    }

    @Override
    public void updateEntitiesStatus(EntityType entityType,final int entityID) {
        getEntity(entityType,entityID, EntityStatus.ALL).updateEntityStatus();
    }

    @Override
    public boolean isDead(EntityType entityType, int entityID) {
        ArrayList<GameEntity> temp = ENEMY;
        if(entityType == EntityType.COIN)
            temp = COINS;
        return temp.get(entityID).isDead();
    }

    @Override
    public void changeCoordinate(int renderedTileSize) {
        for(int i = 0; i < COINS.size(); i++){
            COINS.get(i).changeCoordinate(renderedTileSize);
        }
        for(int i = 0; i < ENEMY.size(); i++){
            ENEMY.get(i).changeCoordinate(renderedTileSize);
        }
    }

    private void addEntities(int sectionIndex, int mapIndex) {
        ArrayList<GameEntity> temp;
        GameEntity gameEntity = null;
        if(dayTime) {
            temp = SECTION_LIST.get(sectionIndex).getMapEntities(MapSection.DAY);
            if (mapGenerated)
            gameEntity = SECTION_LIST.get(sectionIndex).spawnFlyingEnemy(MapSection.DAY);
        } else {
            temp = SECTION_LIST.get(sectionIndex).getMapEntities(MapSection.NIGHT);
            if (mapGenerated)
            gameEntity = SECTION_LIST.get(sectionIndex).spawnFlyingEnemy(MapSection.NIGHT);
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
                ENEMY.add(tempE);
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
        for(int i = ENEMY.size()-1; i>=0; i--){
            EntityCoordinates temp = ENEMY.get(i).getEntityCoordinates();
            temp.setMapIndex(temp.getMapIndex()-1);
            if(temp.getMapIndex()<0)
                ENEMY.remove(i);
        }

    }

    private GameEntity getEntity(EntityType entityType, int entityID, EntityStatus entityStatus) {
        ArrayList<GameEntity> temp = ENEMY;
        if(entityType == EntityType.COIN)
            temp = COINS;
        GameEntity tempE = temp.get(entityID);
        if(entityStatus == EntityStatus.ALIVE && temp.get(entityID).getEntityStatus() != EntityStatus.ALIVE)
            tempE = null;
        return tempE;
    }

}
