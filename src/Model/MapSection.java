package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MapSection {

    public static final int SECTION_SIZE = 16;
    protected ArrayList<GameEntity> mapEntities;

    protected int[][] map;

    public MapSection(){
        map = new int[SECTION_SIZE][SECTION_SIZE];
    }

    public int getCell(final int mapX,final int mapY){
        return map[mapY][mapX];
    }

    public ArrayList<GameEntity> getMapEntities(){
        mapEntities = new ArrayList<>();
        spawnEntities();
        return mapEntities;
    }

    protected abstract void spawnEntities();

}
