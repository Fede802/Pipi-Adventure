package Model;

import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {

    public static final int MAP_LENGHT = 5;
    private Random random;

    private final MapSection[] sectionList;

    private ArrayList <MapSection> generatedMap;

    public MapGenerator(){

        sectionList = new MapSection[2];

        sectionList[0] = new PlainSection1();
        sectionList[1] = new PlainSection2();

        generatedMap = new ArrayList<MapSection>();

        generateMap();
        random = new Random();
    }

    private void generateMap(){

        for (int i =0; i<MAP_LENGHT;i++){
            generatedMap.add(sectionList[0]);
        }
    }

    public void updateMap(){

        generatedMap.remove(0);
        generatedMap.add(sectionList[random.nextInt(2)]);
    }

}
