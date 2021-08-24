package Model;





import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {

    public static final int MAP_LENGHT = 5;
    public static final int MAP_VEL_X = 5;
    public static final int NUM_MAP_SECTION = 5;

    private final MapSection[] sectionList = new MapSection[NUM_MAP_SECTION];
    private final Random random = new Random();

    private int mapTraslX;
    private ArrayList<MapSection> generatedMap;

    public MapGenerator(){
        sectionList[0] = new PlainSection1();
        sectionList[1] = new PlainSection2();
        sectionList[2] = new PlainSection3();
        sectionList[3] = new PlainSection4();
        sectionList[4] = new PlainStartSection();
        generatedMap = new ArrayList<MapSection>();
        generateMap();
    }

    private void generateMap(){
        generatedMap.add(sectionList[4]);
        for (int i = 1; i< MAP_LENGHT; i++){
            generatedMap.add(sectionList[0]);
        }
    }
    public void updateMap() {
        generatedMap.remove(0);
        //TODO later, if the player could start from MapX 0 and move don't know if could be useful a start section
        //-1 because last section is the start section
        generatedMap.add(sectionList[random.nextInt(NUM_MAP_SECTION-1)]);
        System.out.println(random.nextInt(MAP_LENGHT));
    }

    public int getMapTraslX() {
        return mapTraslX;
    }

    public void setMapTraslX(final int mapTraslX) {
        this.mapTraslX = mapTraslX;
    }

    public int getTileData(final int mapIndex,final int mapX,final int mapY){
        return generatedMap.get(mapIndex).getCell(mapX,mapY);
    }
}

