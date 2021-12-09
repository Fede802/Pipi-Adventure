package utils;

public class GameDataConfig extends Config {

    private static final String GAME_DATA_PROPERTIES_URL = "Config/GameDataProperties.txt";
    private static GameDataConfig instance = null;

    private GameDataConfig(){
        super(GAME_DATA_PROPERTIES_URL);
    }

    public int getMapSectionSize(){
        return Integer.valueOf(PROPERTIES.getProperty("mapSectionSize"));
    }

    public int getMapLength(){
        return Integer.valueOf(PROPERTIES.getProperty("mapLength"));
    }

    public int getDefaultTilesetTileSize(){
        return Integer.valueOf(PROPERTIES.getProperty("defaultTilesetTileSize"));
    }

    public int getDefaultNumRowsOfTileset() {
        return Integer.valueOf(PROPERTIES.getProperty("defaultNumRowsOfTileset"));
    }

    public int getDefaultNumColumnsOfTileset() {
        return Integer.valueOf(PROPERTIES.getProperty("defaultNumColumnsOfTileset"));
    }

    public int getMinTileToRender(){
        return Integer.parseInt(PROPERTIES.getProperty("minTileToRender"));
    }

    public int getPlayerStartMapX(){
        return Integer.parseInt(PROPERTIES.getProperty("playerStartMapX"));
    }

    public int getPlayerStartMapY(){
        return Integer.parseInt(PROPERTIES.getProperty("playerStartMapY"));
    }

    public int getEmptyTileCode(){
        return Integer.parseInt(PROPERTIES.getProperty("emptyTileCode"));
    }

    public int getMaxLife(){
        return Integer.valueOf(PROPERTIES.getProperty("maxLife"));
    }

    public int getMaxBullet(){
        return Integer.valueOf(PROPERTIES.getProperty("maxBullets"));
    }

    public int getMinBullet(){
        return Integer.valueOf(PROPERTIES.getProperty("minBullets"));
    }

    public static GameDataConfig getInstance() {
        if (instance == null)
            instance = new GameDataConfig();
        return instance;
    }

}