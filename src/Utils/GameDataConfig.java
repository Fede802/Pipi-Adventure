package utils;

public class GameDataConfig extends Config{
    private static final String GAME_DATA_PROPERTIES_URL = "Config/GameDataProperties.txt";
    private static GameDataConfig instance = null;
    private int renderedTileSize;

    private GameDataConfig(){
        super(GAME_DATA_PROPERTIES_URL);
    }

    public int getMapSectionSize(){
        return Integer.valueOf(properties.getProperty("mapSectionSize"));
    }

    public int getDefaultImageTileSize(){
        return Integer.valueOf(properties.getProperty("defaultImageTileSize"));
    }

    public int getMapLength(){
        return Integer.valueOf(properties.getProperty("mapLength"));
    }

    public int getDefaultTilesetTileSize(){
        return Integer.valueOf(properties.getProperty("defaultTilesetTileSize"));
    }

    public int getDefaultNumRowsOfTileset(){
        return Integer.valueOf(properties.getProperty("defaultNumRowsOfTileset"));
    }

    public int getDefaultNumColumnsOfTileset(){
        return Integer.valueOf(properties.getProperty("defaultNumColumnsOfTileset"));
    }

    public int getMaxLife(){
        return Integer.valueOf(properties.getProperty("maxLife"));
    }

    public int getMinTileToRender(){
        return Integer.parseInt(properties.getProperty("minTileToRender"));
    }

    public int getRenderedTileSize() {
        return renderedTileSize;
    }
    public void setRenderedTileSize(int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

    public int getPlayerStartMapX(){
        return Integer.parseInt(properties.getProperty("playerStartMapX"));
    }
    public int getPlayerStartMapY(){
        return Integer.parseInt(properties.getProperty("playerStartMapY"));
    }



    public static GameDataConfig getInstance() {
        if (instance == null)
            instance = new GameDataConfig();
        return instance;
    }
}