package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;
import utils.GameConfig;
import utils.GameDataConfig;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    //variable uses to fix approximation error due to double sum
    private final int SCALE = (int) Math.pow(10, 4);

    public static final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    public static double MAP_VEL_X = Player.PLAYER_VEL_X;

    private final ArrayList<MapSection> sectionList = new ArrayList<>();
    private final ArrayList<Animation> animations = new ArrayList<>();
    private final ArrayList<EntityCoordinates> entityCoordinates = new ArrayList<>();
    private final ArrayList<GameEntity> gameEntities = new ArrayList<>();
    private final Random random = new Random();

    private double mapTraslX;

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
        mapTraslX = 0 ;
        System.out.println("newMAp");
        setupMapEntity();
    }

    private void setupMapEntity(){
        entityCoordinates.clear();
        animations.clear();
        gameEntities.clear();
        for (int i = 0; i< MAP_LENGTH; i++){
            for(int j = 0; j < generatedMap.get(i).getValue().size(); j++) {
                GameEntity temp = generatedMap.get(i).getValue().get(j);
                temp.getEntityCoordinates().setMapIndex(i);
                gameEntities.add(temp);
                entityCoordinates.add(temp.getEntityCoordinates());
                animations.add(temp.getAnimation());
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

    public double getMapTraslX() {
        return mapTraslX;
    }

    public void setMapTraslX(final double mapTraslX) {
        this.mapTraslX = mapTraslX;
    }

    public void updateMapTraslX(double traslVar){
        this.mapTraslX= (double) Math.round((this.mapTraslX+=traslVar) * SCALE) / SCALE;
    }

    public void updateEntitiesMapPosition(){
        animations.clear();
        for (int i = 0; i< gameEntities.size(); i++){
//            System.out.println(i);
            //todo if change order non va capisci il motivo, forse al di fuori di questo fatto visto che il proiettile può uccidere lontano non è buono splittare mappa e entità così ci potrebbe stare sovrascrizione di valori
            gameEntities.get(i).move();
            animations.add(gameEntities.get(i).getAnimation());

        }
    }

    public ArrayList<Pair<EntityCoordinates,Animation>> getMapEntitiesCoordinates(){
        ArrayList<Pair<EntityCoordinates,Animation>> mapEntitiesCoordinates = new ArrayList<>();
        for (int j = 0; j < entityCoordinates.size(); j++){
            if(gameEntities.get(j).isAlive || gameEntities.get(j).isDying){
                mapEntitiesCoordinates.add(new Pair<>(entityCoordinates.get(j), animations.get(j)));
            }
        }
        return mapEntitiesCoordinates;
    }

    public ArrayList<EntityCoordinates> getEntitiesCoordinates(){
        ArrayList<EntityCoordinates> entityCoordinates = new ArrayList<>();
        for (int j = 0; j < this.entityCoordinates.size(); j++)
            if(gameEntities.get(j).isAlive)
                entityCoordinates.add(this.entityCoordinates.get(j));
            else
                entityCoordinates.add(null);
        return entityCoordinates;
    }

    public void updateEntitiesStatus(final int entityID){
        gameEntities.get(entityID).setAlive(false);
        gameEntities.get(entityID).setDying(true);
    }

    public int getTileData(final int mapIndex,final int mapX,final int mapY){
        return generatedMap.get(mapIndex).getKey().getCell(mapX,mapY);
    }

    public void changeCoordinate() {
        mapTraslX = mapTraslX / MAP_VEL_X * (GameDataConfig.getInstance().getRenderedTileSize()/Player.TILE_STEP);
        MAP_VEL_X = Player.PLAYER_VEL_X;
        for(int i = 0; i < gameEntities.size();i++){
            gameEntities.get(i).changeCoordinate();
        }
    }
}

