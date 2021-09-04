package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;
import Utils.Config;
import Utils.GameConfig;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    private final int scale = (int) Math.pow(10, 1);
    public static final int MAP_LENGHT = 5;
    public static double MAP_VEL_X = GameConfig.getInstance().getRenderedTileSize()/5.0;


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
        generatedMap.add(new Pair<>(sectionList.get(6),sectionList.get(6).getMapEntities()));
        for (int i = 1; i< MAP_LENGHT; i++){
            generatedMap.add(new Pair<>(sectionList.get(i),sectionList.get(i).getMapEntities()));
        }
        setupMapEntity();
    }

    private void setupMapEntity(){
        entityCoordinates.clear();
        animations.clear();
        gameEntities.clear();
        for (int i = 0; i< MAP_LENGHT; i++){
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
        generatedMap.add(new Pair<>(sectionList.get(0),sectionList.get(0).getMapEntities()));
        setupMapEntity();
    }

    public double getMapTraslX() {
        return mapTraslX;
    }

    public void setMapTraslX(final double mapTraslX) {
        this.mapTraslX = mapTraslX;
    }

    public void updateMapTraslX(){
        this.mapTraslX= (double) Math.round((this.mapTraslX+=MAP_VEL_X) * scale) / scale;
    }
    public void updateEntitiesMapPosition(){
        animations.clear();
        for (int i = 0; i< gameEntities.size(); i++){
            //todo if change order non va capisci il motivo, forse al di fuori di questo fatto visto che il proiettile può uccidere lontano non è buono splittare mappa e entità così ci potrebbe stare sovrascrizione di valori
            gameEntities.get(i).move();
            animations.add(gameEntities.get(i).getAnimation());
            if(!gameEntities.get(i).isAlive && !gameEntities.get(i).isDying){
                gameEntities.remove(i);
                entityCoordinates.remove(i);
            }
        }
    }
    public ArrayList<Pair<EntityCoordinates,Animation>> getMapEntitiesCoordinates(){
        ArrayList<Pair<EntityCoordinates,Animation>> mapEntitiesCoordinates = new ArrayList<>();
        for (int j = 0; j < entityCoordinates.size(); j++){
            mapEntitiesCoordinates.add(new Pair<>(entityCoordinates.get(j), animations.get(j)));
        }
        return mapEntitiesCoordinates;
    }

    public ArrayList<Pair<Integer, EntityCoordinates>> getEntities(){
        ArrayList<Pair<Integer,EntityCoordinates>> entityCoordinates = new ArrayList<>();
        for (int j = 0; j < this.entityCoordinates.size(); j++)
            if(gameEntities.get(j).isAlive)
                entityCoordinates.add(new Pair<>(j,this.entityCoordinates.get(j)));
            else
                entityCoordinates.add(new Pair<>(j,null));
        return entityCoordinates;
    }

    public void updateEntitiesStatus(final int entityID){
        if(gameEntities.get(entityID).isAlive()){
            gameEntities.get(entityID).setAlive(false);
            gameEntities.get(entityID).setDying(true);
        }
    }

    public int getTileData(final int mapIndex,final int mapX,final int mapY){
        return generatedMap.get(mapIndex).getKey().getCell(mapX,mapY);
    }

    public void changeCoordinate() {
        mapTraslX = mapTraslX / MAP_VEL_X * (GameConfig.getInstance().getRenderedTileSize()/5.0);
        MAP_VEL_X = GameConfig.getInstance().getRenderedTileSize() / 5.0;
        for(int i = 0; i < gameEntities.size(); i++){

        }
    }
}
