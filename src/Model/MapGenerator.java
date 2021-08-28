package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pairs;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapGenerator {
    public static final int MAP_LENGHT = 5;
    public static final int MAP_VEL_X = 5;

    private final ArrayList<MapSection> sectionList = new ArrayList<>();
    private final ArrayList<Animation> animations = new ArrayList<>();
    private final Random random = new Random();

    private int mapTraslX;
    private ArrayList<Pair<MapSection,ArrayList<GameEntity>>> generatedMap = new ArrayList<>();


    public MapGenerator(){
        sectionList.add(new PlainSection1());
        sectionList.add(new PlainSection2());
        sectionList.add(new PlainSection3());
        sectionList.add(new PlainSection4());
        sectionList.add(new PlainStartSection());
        generateMap();
    }
    private void generateMap(){
        generatedMap.add(new Pair<>(sectionList.get(4),sectionList.get(4).getMapEntities()));
        for (int i = 1; i< MAP_LENGHT; i++){
            generatedMap.add(new Pair<>(sectionList.get(0),sectionList.get(0).getMapEntities()));
        }
        updateEntityMapIndex();
    }

    private void updateEntityMapIndex(){
        for (int i = 0; i< MAP_LENGHT; i++){
            for(int j = 0; j < generatedMap.get(i).getValue().size(); j++) {
                generatedMap.get(i).getValue().get(j).getEntityCoordinates().setMapIndex(i);
                animations.add(generatedMap.get(i).getValue().get(j).getAnimation());
            }
        }
    }

    public void updateMap() {
        generatedMap.remove(0);
        //TODO later, if the player could start from MapX 0 and move don't know if could be useful a start section
        //-1 because last section is the start section
        int nextSection = random.nextInt(sectionList.size()-1);
        generatedMap.add(new Pair<>(sectionList.get(nextSection),sectionList.get(nextSection).getMapEntities()));
        updateEntityMapIndex();
    }

    public int getMapTraslX() {
        return mapTraslX;
    }

    public void setMapTraslX(final int mapTraslX) {
        this.mapTraslX = mapTraslX;
    }

    public int getTileData(final int mapIndex,final int mapX,final int mapY){
        return generatedMap.get(mapIndex).getKey().getCell(mapX,mapY);
    }

    public ArrayList<Pairs<EntityCoordinates,Animation>> getMapEntitiesCoordinates(){
        ArrayList<Pairs<EntityCoordinates,Animation>> mapEntitiesCoordinates = new ArrayList<>();
        for (int i = 0; i< MAP_LENGHT; i++){
            ArrayList<GameEntity> sectionEntity = generatedMap.get(i).getValue();
            for (GameEntity entity : sectionEntity) {
                mapEntitiesCoordinates.add(new Pairs<>(entity.getEntityCoordinates(), entity.getAnimation()));
            }
        }
        return mapEntitiesCoordinates;
    }


    public void updateMapEntity() {

        for (int i = 0; i< MAP_LENGHT; i++){
            for(int j = 0; j < generatedMap.get(i).getValue().size(); j++) {
                generatedMap.get(i).getValue().get(j).move();
                if(generatedMap.get(i).getValue().get(j).isRemovable){
                    generatedMap.get(i).getValue().remove(j);
                }
            }
        }
    }



    public void collision(GameEntity gameEntity){
        for (int i = 0; i< MAP_LENGHT; i++){
            for(int j = 0; j < generatedMap.get(i).getValue().size(); j++) {
                if(generatedMap.get(i).getValue().get(j).collide(gameEntity)) {
                    generatedMap.get(i).getValue().get(j).setAlive(false);
                    System.out.println("DEAD");
                }
            }
        }
    }
}
