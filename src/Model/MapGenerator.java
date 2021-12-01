package model;

import commons.*;
import utils.GameDataConfig;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator implements IMapGenerator{

    private final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    private final ArrayList<MapSection> sectionList = new ArrayList<>();
    private final ArrayList<GameEntity> coins = new ArrayList<>();
    private final ArrayList<GameEntity> enemy = new ArrayList<>();
    private final Random random = new Random();
    private boolean mapGenerated;

    private boolean dayTime = true;
    private final ArrayList<MapSection> generatedMap = new ArrayList<>();

    public MapGenerator(){
        sectionList.add(new PlainSection1());
        sectionList.add(new PlainSection2());
        sectionList.add(new PlainSection3());
        sectionList.add(new PlainSection4());
        sectionList.add(new PlainSection5());
        sectionList.add(new PlainSection6());
        sectionList.add(new PlainStartSection());
        generateMap();
    }

    @Override
    public void generateMap(){
        mapGenerated = false;
        dayTime = true;
        generatedMap.clear();
        coins.clear();
        enemy.clear();
        generatedMap.add(sectionList.get(sectionList.size()-1));
        addEntities(sectionList.size()-1,0);
        for (int i = 1; i< MAP_LENGTH; i++){
            generatedMap.add(sectionList.get(0));
            addEntities(0,i);
        }
        mapGenerated = true;
    }

    @Override
    public void updateMap() {
        updateEntitiesMapIndex();
        generatedMap.remove(0);
        //-1 because last section is the start section
        int nextSection = random.nextInt(sectionList.size()-1);
        generatedMap.add(sectionList.get(nextSection));
        addEntities(nextSection,MAP_LENGTH-1);
    }

    @Override
    public int getTileData(final int mapIndex,final int mapX,final int mapY){
        return generatedMap.get(mapIndex).getCell(mapX,mapY);
    }

    @Override
    public void updateDayTime() {
        dayTime = !dayTime;
    }

    @Override
    public void moveEntities() {
        for (int i = coins.size()-1; i>=0; i--){
            if(coins.get(i).getEntityStatus() == EntityStatus.DEAD)
                coins.remove(i);
            else{
                coins.get(i).move();
            }
        }
        for (int i = enemy.size()-1; i>=0; i--){
            if(enemy.get(i).getEntityStatus() == EntityStatus.DEAD)
                enemy.remove(i);
            else{
                enemy.get(i).move();
            }
        }
    }

    @Override
    public int entityCount(EntityType entityType) {
        int count = enemy.size();
        if(entityType == EntityType.COIN)
            count = coins.size();
        return count;
    }

    @Override
    public EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus){
        GameEntity e = getEntity(entityType,entityID,entityStatus);
        EntityCoordinates ec = null;
        if(e != null)
            ec = e.getEntityCoordinates();
        return ec;
    }

    @Override
    public AnimationData getEntityAnimation(EntityType entityType, int entityID){
        return getEntity(entityType,entityID,EntityStatus.ALL).getAnimation();
    }

    @Override
    public void updateAnimationData(EntityType entityType, int entityID, AnimationData animation) {
        ArrayList<GameEntity> temp = enemy;
        if(entityType == EntityType.COIN)
            temp = coins;
        temp.get(entityID).updateAnimationData(animation);
    }

    @Override
    public void updateEntitiesStatus(EntityType entityType,final int entityID){
        getEntity(entityType,entityID, EntityStatus.ALL).updateEntityStatus();
    }

    @Override
    public boolean isDead(EntityType entityType, int entityID) {
        ArrayList<GameEntity> temp = enemy;
        if(entityType == EntityType.COIN)
            temp = coins;
        return temp.get(entityID).isDead();
    }

    @Override
    public void changeCoordinate(int renderedTileSize) {
        for(int i = 0; i < coins.size();i++){
            coins.get(i).changeCoordinate(renderedTileSize);
        }
        for(int i = 0; i < enemy.size();i++){
            enemy.get(i).changeCoordinate(renderedTileSize);
        }
    }

    private void addEntities(int sectionIndex, int mapIndex){
        ArrayList<GameEntity> temp;
        GameEntity gameEntity = null;
        if(dayTime) {
            temp = sectionList.get(sectionIndex).getMapEntities(MapSection.DAY);
            if (mapGenerated)
            gameEntity = sectionList.get(sectionIndex).spawnFlyingEnemy(MapSection.DAY);
        } else {
            temp = sectionList.get(sectionIndex).getMapEntities(MapSection.NIGHT);
            if (mapGenerated)
            gameEntity = sectionList.get(sectionIndex).spawnFlyingEnemy(MapSection.NIGHT);
        }
        if (gameEntity != null){
            temp.add(gameEntity);
        }
        for(int j = 0; j< temp.size(); j++){
            GameEntity tempE= temp.get(j);
            tempE.getEntityCoordinates().setMapIndex(mapIndex);
            if(tempE.getType() == EntityType.COIN){
                coins.add(tempE);
            }else if(tempE.getType() == EntityType.ENEMY){
                enemy.add(tempE);
            }
        }
    }

    private void updateEntitiesMapIndex(){
        for(int i = coins.size()-1; i>=0; i--){
            EntityCoordinates temp = coins.get(i).getEntityCoordinates();
            temp.setMapIndex(temp.getMapIndex()-1);
            if(temp.getMapIndex()<0)
                coins.remove(i);
        }
        for(int i = enemy.size()-1; i>=0; i--){
            EntityCoordinates temp = enemy.get(i).getEntityCoordinates();
            temp.setMapIndex(temp.getMapIndex()-1);
            if(temp.getMapIndex()<0)
                enemy.remove(i);
        }

    }

    private GameEntity getEntity(EntityType entityType, int entityID, EntityStatus entityStatus){
        ArrayList<GameEntity> temp = enemy;
        if(entityType == EntityType.COIN)
            temp = coins;
        GameEntity tempE = temp.get(entityID);
        if(entityStatus == EntityStatus.ALIVE && temp.get(entityID).getEntityStatus() != EntityStatus.ALIVE)
            tempE = null;
        return tempE;
    }

}
