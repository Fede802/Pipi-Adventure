package model;

import commons.*;
import utils.GameDataConfig;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    //variable uses to fix approximation error due to double sum

    public static final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    private final ArrayList<MapSection> sectionList = new ArrayList<>();
    private final ArrayList<GameEntity> coins = new ArrayList<>();
    private final ArrayList<GameEntity> enemy = new ArrayList<>();
    private final ArrayList<GameEntity> gameEntities = new ArrayList<>();
    private final Random random = new Random();

    private ArrayList<MapSection> generatedMap = new ArrayList<>();

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

    public void generateMap(){
        generatedMap.clear();
        coins.clear();
        enemy.clear();
        generatedMap.add(sectionList.get(sectionList.size()-1));
        addEntities(6,0);
        for (int i = 1; i< MAP_LENGTH; i++){
            generatedMap.add(sectionList.get(3));
            addEntities(3,i);
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

    private void addEntities(int sectionIndex, int mapIndex){
        ArrayList<GameEntity> temp = sectionList.get(sectionIndex).getMapEntities();
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
    public void updateMap() {
        updateEntitiesMapIndex();
        generatedMap.remove(0);
        //-1 because last section is the start section
        int nextSection = random.nextInt(sectionList.size()-1);
        generatedMap.add(sectionList.get(nextSection));
        addEntities(nextSection,MAP_LENGTH-1);
//        generatedMap.add(new Pair<>(sectionList.get(1),sectionList.get(1).getMapEntities()));

    }

//todo
    public Animation getEntityAnimation(EntityType entityType, int entityID){
        return getEntity(entityType,entityID,EntityStatus.ALL).getAnimation();
    }

    public EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID, EntityStatus entityStatus){
        GameEntity e = getEntity(entityType,entityID,entityStatus);
        EntityCoordinates ec = null;
        if(e != null)
            ec = e.getEntityCoordinates();
        return ec;
    }

    private GameEntity getEntity(EntityType entityType, int entityID, EntityStatus entityStatus){
        ArrayList<GameEntity> temp = enemy;
        if(entityType == EntityType.COIN)
            temp = coins;
        GameEntity tempE = temp.get(entityID);
        if(entityStatus == EntityStatus.ALIVE && !temp.get(entityID).isAlive())
            tempE = null;
        return tempE;
    }

    public void updateEntitiesStatus(EntityType entityType,final int entityID,EntityStatus entityStatus){
        GameEntity temp = getEntity(entityType,entityID, EntityStatus.ALL);
        //todo temp could be null in theory but not in practice
        System.out.println("update entity AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        if(entityStatus == EntityStatus.DYING){
            temp.setAlive(false);
            temp.setDying(true);
            System.out.println("dying");
        }
        else{

            temp.setDying(false);
            temp.getAnimation().resetAnimation();
            System.out.println("dead");
        }

    }

    public int getTileData(final int mapIndex,final int mapX,final int mapY){
        return generatedMap.get(mapIndex).getCell(mapX,mapY);
    }
    public void changeCoordinate() {
        for(int i = 0; i < coins.size();i++){
            coins.get(i).changeCoordinate();
        }
        for(int i = 0; i < enemy.size();i++){
            enemy.get(i).changeCoordinate();
        }
    }

    public int entityCount(EntityType entityType) {
        int count = enemy.size();
        if(entityType == EntityType.COIN)
            count = coins.size();
        return count;
    }

    public void moveEntities() {
        for (int i = coins.size()-1; i>=0; i--){
            if(!coins.get(i).isAlive() && !coins.get(i).isDying())
                coins.remove(i);
            else{
                coins.get(i).move();
            }
        }
        for (int i = enemy.size()-1; i>=0; i--){
            if(!enemy.get(i).isAlive() && !enemy.get(i).isDying())
                enemy.remove(i);
            else{
                enemy.get(i).move();
            }
        }

    }

    public boolean isDead(EntityType entityType, int entityID) {
        ArrayList<GameEntity> temp = enemy;
        if(entityType == EntityType.COIN)
            temp = coins;
        return temp.get(entityID).isDead();
    }
}
