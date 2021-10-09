package model;

import utils.GameDataConfig;

import java.util.ArrayList;

public abstract class MapSection {
    //TODO ADD SPAWNER CLASS TO SPAWN ENTITIES
    public static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
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
