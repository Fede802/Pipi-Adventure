package utils;

public class GameDataConfig extends Config {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static final String GAME_DATA_PROPERTIES_URL = "Config/GameDataProperties.txt";
    private static GameDataConfig instance;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private GameDataConfig(){
        super(GAME_DATA_PROPERTIES_URL);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public int getMapSectionSize(){
        return Integer.parseInt(PROPERTIES.getProperty("mapSectionSize"));
    }

    public int getMapLength(){
        return Integer.parseInt(PROPERTIES.getProperty("mapLength"));
    }

    public int getDefaultTilesetTileSize() {
        return Integer.parseInt(PROPERTIES.getProperty("defaultTilesetTileSize"));
    }

    public int getDefaultNumRowsOfTileset() {
        return Integer.parseInt(PROPERTIES.getProperty("defaultNumRowsOfTileset"));
    }

    public int getDefaultNumColumnsOfTileset() {
        return Integer.parseInt(PROPERTIES.getProperty("defaultNumColumnsOfTileset"));
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

    public int getMaxLives(){
        return Integer.parseInt(PROPERTIES.getProperty("maxLives"));
    }

    public int getMaxBullets(){
        return Integer.parseInt(PROPERTIES.getProperty("maxBullets"));
    }

    public int getMinBullets(){
        return Integer.parseInt(PROPERTIES.getProperty("minBullets"));
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static GameDataConfig getInstance() {
        if (instance == null)
            instance = new GameDataConfig();
        return instance;
    }

}