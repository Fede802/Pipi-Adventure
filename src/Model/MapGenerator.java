package Model;



import Utils.CostantField;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {

    public static final int MAP_LENGHT = 5;
    private Random random;

    private final MapSection[] sectionList;

    private ArrayList <MapSection> generatedMap;

    public MapGenerator(){

        sectionList = new MapSection[4];

        sectionList[0] = new PlainSection1();
        sectionList[1] = new PlainSection2();
        sectionList[2] = new PlainSection3();
        sectionList[3] = new PlainSection4();

        generatedMap = new ArrayList<MapSection>();

        generateMap();
        random = new Random();
    }

    private void generateMap(){

        for (int i = 0; i< CostantField.SIZE_OF_GENERATED_MAP; i++){
            generatedMap.add(sectionList[0]);
        }
    }

    public void updateMap(){

        generatedMap.remove(0);
        generatedMap.add(sectionList[random.nextInt(4)]);
        System.out.println(random.nextInt(4));
//        generatedMap.add(sectionList[1]);
    }

    public int getTileData(int mapIndex, int iIndex, int jIndex){

        return generatedMap.get(mapIndex).getCell(15-iIndex,jIndex);
    }

}
