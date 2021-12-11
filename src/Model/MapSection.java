package model;

import commons.EntityCoordinates;
import commons.RenderingType;
import utils.GameConfig;
import utils.GameDataConfig;
import java.util.ArrayList;
import java.util.Random;

public abstract class MapSection {

    //TODO ADD SPAWNER CLASS TO SPAWN ENTITIES

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    public static final int DAY = 0;
    public static final int NIGHT = 1;
    public static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final int SPAWN_RANGE = GameConfig.getInstance().getFlyingSpawnRange();
    private final Random RANDOM = new Random();

    protected final ArrayList<GameEntity> MAP_ENTITIES = new ArrayList<>();
    protected final int Y_SPAWN_GAP = 2;

    protected int[][] map;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    protected MapSection(){
        //eventually load map from file
        map = new int[SECTION_SIZE][SECTION_SIZE];
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public int getCell(int mapX, int mapY){
        return map[mapY][mapX];
    }

    public ArrayList<GameEntity> getMapEntities(int daytime){
        MAP_ENTITIES.clear();
        spawnEntities(daytime);
        return MAP_ENTITIES;
    }

    public GameEntity spawnFlyingEnemy(int daytime) {
        GameEntity temp = null;
        if (RANDOM.nextInt(SPAWN_RANGE) == 0) {
            int step = RANDOM.nextInt(2);
            if (daytime == DAY) {
                temp = new FlyingEnemy(RenderingType.BEE,new EntityCoordinates.Builder(6, 10- Y_SPAWN_GAP *step).build());
            }else {
                temp = new FlyingEnemy(RenderingType.BAT,new EntityCoordinates.Builder(6, 10- Y_SPAWN_GAP *step).build());
            }
        }
        return temp;
    }

    //    --------------------------------------------------------
    //                      ABSTRACT METHOD
    //    --------------------------------------------------------

    protected abstract void spawnEntities(int daytime);

}
