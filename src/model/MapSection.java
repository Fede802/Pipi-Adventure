package model;

import commons.EntityCoordinates;
import commons.EntityType;
import utils.GameDataConfig;

import java.util.ArrayList;
import java.util.Random;

public abstract class MapSection {
    //TODO ADD SPAWNER CLASS TO SPAWN ENTITIES
    public static final int DAY = 0;
    public static final int NIGHT = 1;
    public static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    protected ArrayList<GameEntity> mapEntities = new ArrayList<>();
    protected int yspawngap = 2;

    protected int[][] map;

    private Random random = new Random();

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

    public GameEntity spawnFlyingEnemy(int daytime) {
        GameEntity temp = null;
        if (random.nextInt(10) == 0) {
            int step = random.nextInt(2);
            if (daytime == DAY) {
                temp = new Bee(new EntityCoordinates.Builder(6, 10-2*step).build());
            }else {
                temp = new Bat(new EntityCoordinates.Builder(6, 10-2*step).build());
            }
        }
        return temp;
    }
}
