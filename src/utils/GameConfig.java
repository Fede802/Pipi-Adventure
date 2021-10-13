package utils;
public class GameConfig extends Config{

    private static final String GAME_PROPERTIES_URL = "Config/GameProperties.txt";
    private static GameConfig instance = null;


    private GameConfig(){
        super(GAME_PROPERTIES_URL);
    }

    public int getCurrentMaxLife(){
        return Integer.valueOf(properties.getProperty("currentMaxLife"));
    }
    public int getCurrentMaxBullet(){
        return Integer.valueOf(properties.getProperty("currentMaxBullet"));
    }

    public static GameConfig getInstance() {
        if (instance == null)
            instance = new GameConfig();
        return instance;
    }

}
