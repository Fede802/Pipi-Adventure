package model;

import utils.GameDataConfig;

import java.util.ArrayList;

public abstract class MapSection {
    //TODO ADD SPAWNER CLASS TO SPAWN ENTITIES
    public static final int DAY = 0;
    public static final int NIGHT = 1;
    public static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    protected ArrayList<GameEntity> mapEntities = new ArrayList<>();

    protected int[][] map;

    public MapSection(){
        map = new int[SECTION_SIZE][SECTION_SIZE];
    }

    public int getCell(final int mapX,final int mapY){
        return map[mapY][mapX];
    }

    public ArrayList<GameEntity> getMapEntities(int daytime){
        mapEntities.clear();
        spawnEntities(daytime);
        return mapEntities;
    }
    //todo add day/night spawn
    protected abstract void spawnEntities(int daytime);
}
