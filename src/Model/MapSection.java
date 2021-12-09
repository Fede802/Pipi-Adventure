package model;

import commons.EntityCoordinates;
import commons.RenderingType;
import utils.GameDataConfig;

import java.util.ArrayList;
import java.util.Random;

public abstract class MapSection {
    //TODO ADD SPAWNER CLASS TO SPAWN ENTITIES
    public static final int DAY = 0;
    public static final int NIGHT = 1;
    public static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();

    protected final ArrayList<GameEntity> MAP_ENTITIES = new ArrayList<>();
    protected final int Y_SPAWN_GAP = 2;

    protected int[][] map;

    private Random random = new Random();

    public MapSection(){
        map = new int[SECTION_SIZE][SECTION_SIZE];
    }

    public int getCell(final int mapX,final int mapY){
        return map[mapY][mapX];
    }

    public ArrayList<GameEntity> getMapEntities(int daytime){
        MAP_ENTITIES.clear();
        spawnEntities(daytime);
        return MAP_ENTITIES;
    }

    public GameEntity spawnFlyingEnemy(int daytime) {
        GameEntity temp = null;
        if (random.nextInt(10) == 0) {
            int step = random.nextInt(2);
            if (daytime == DAY) {
                temp = new FlyingEnemy(RenderingType.BEE,new EntityCoordinates.Builder(6, 10- Y_SPAWN_GAP *step).build());
            }else {
                temp = new FlyingEnemy(RenderingType.BAT,new EntityCoordinates.Builder(6, 10- Y_SPAWN_GAP *step).build());
            }
        }
        return temp;
    }

    protected abstract void spawnEntities(int daytime);
}
