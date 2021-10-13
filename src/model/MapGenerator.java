package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;
import commons.Pair;
import utils.GameDataConfig;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    //variable uses to fix approximation error due to double sum

    public static final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    private final ArrayList<MapSection> sectionList = new ArrayList<>();
    private final ArrayList<GameEntity> gameEntities = new ArrayList<>();
    private final Random random = new Random();

    private ArrayList<Pair<MapSection,ArrayList<GameEntity>>> generatedMap = new ArrayList<>();

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
        generatedMap.add(new Pair<>(sectionList.get(sectionList.size()-1),sectionList.get(sectionList.size()-1).getMapEntities()));
        for (int i = 1; i< MAP_LENGTH; i++){
            generatedMap.add(new Pair<>(sectionList.get(0),sectionList.get(0).getMapEntities()));
        }
        setupMapEntity();
    }

    private void setupMapEntity(){
        gameEntities.clear();
        for (int i = 0; i< MAP_LENGTH; i++){
            for(int j = 0; j < generatedMap.get(i).getValue().size(); j++) {
                GameEntity temp = generatedMap.get(i).getValue().get(j);
                if(!temp.isAlive() && !temp.isDying()){
                    generatedMap.get(i).getValue().remove(j);
                }else{
                    temp.getEntityCoordinates().setMapIndex(i);
                    gameEntities.add(temp);
                }
            }

        }
    }

    public void updateMap() {
        generatedMap.remove(0);
        //-1 because last section is the start section
        int nextSection = random.nextInt(sectionList.size()-1);
        generatedMap.add(new Pair<>(sectionList.get(nextSection),sectionList.get(nextSection).getMapEntities()));
//        generatedMap.add(new Pair<>(sectionList.get(1),sectionList.get(1).getMapEntities()));
        setupMapEntity();
    }


    public Animation getEntityAnimation(int entityID){
        return gameEntities.get(entityID).getAnimation();
    }

    public EntityCoordinates getEntityCoordinates(int entityID){
        return gameEntities.get(entityID).getEntityCoordinates();
    }
    public EntityCoordinates getEntityCoordinates(EntityType entityType, int entityID){
        int count = 0;

        for(int i = 0; i < gameEntities.size();i++){
            if(gameEntities.get(i).getType() == entityType){

                if(count == entityID){
                    count = i;
                    i = gameEntities.size();
                }else{
                    count++;
                }
            }
//            if(count == entityID){
//                count = i;
//                i = gameEntities.size();
//            }
        }
        return gameEntities.get(count).getEntityCoordinates();
    }

    public void updateEntitiesStatus(EntityType entityType,final int entityID){
        int count = 0;

        for(int i = 0; i < gameEntities.size();i++){
            if(gameEntities.get(i).getType() == entityType){

                if(count == entityID){
                    count = i;
                    i = gameEntities.size();
                }else{count++;}
            }
//            if(count == entityID){
//                count = i;
//                i = gameEntities.size();
//            }
        }
        gameEntities.get(count).setAlive(false);
        gameEntities.get(count).setDying(true);
    }

    public int getTileData(final int mapIndex,final int mapX,final int mapY){
        return generatedMap.get(mapIndex).getKey().getCell(mapX,mapY);
    }
    public void changeCoordinate() {
        for(int i = 0; i < gameEntities.size();i++){
            gameEntities.get(i).changeCoordinate();
        }
    }

    public int entityCount(EntityType entityType, EntityType entityStatus) {
        int count = 0;
        for(int i = 0; i < gameEntities.size(); i++){
            if(gameEntities.get(i).getType() == entityType)
                if(!(entityStatus == EntityType.ALIVE && !gameEntities.get(i).isAlive()))
                    count++;
        }
        return count;
    }

    public void moveEntities() {
        for (int i = 0; i< gameEntities.size(); i++){
            if(!gameEntities.get(i).isAlive() && !gameEntities.get(i).isDying())
                gameEntities.remove(i);
            else{
                gameEntities.get(i).move();
            }
        }
            //todo if change order non va capisci il motivo, forse al di fuori di questo fatto visto che il proiettile può uccidere lontano non è buono splittare mappa e entità così ci potrebbe stare sovrascrizione di valori
    }
}
