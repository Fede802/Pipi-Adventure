package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GameConfig extends Config{

    private static final String GAME_PROPERTIES_URL = "Config/GameSaves.txt";
    private static final String GAME_VARIABLES_URL = "Config/GameVariables.txt";
    private static GameConfig instance = null;
    private final Properties gameProperties = new Properties(),gameVariables = new Properties();

    private int renderedTileSize;

    private GameConfig(){
        try {
            BufferedReader reader = Files.newBufferedReader(Path.of(GAME_PROPERTIES_URL));
            gameProperties.load(reader);
        } catch (IOException e) {
            System.out.println("Failed loading game configuration file");
        }
        try {
            BufferedReader reader = Files.newBufferedReader(Path.of(GAME_VARIABLES_URL));
            gameVariables.load(reader);
        } catch (IOException e) {
            System.out.println("Failed loading game variables file");
        }
    }
    public int getMinTileToRender(){
        return Integer.valueOf(gameVariables.getProperty("minTileToRender"));
    }
    public int getRenderedTileSize(){
        return renderedTileSize;
    }
    public void setRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

    public int getCurrentMaxLife(){
        return Integer.valueOf(gameVariables.getProperty("currentMaxLife"));
    }
    public void setCurrentMaxLife(final int currentMaxLife){
        gameVariables.setProperty("currentMaxLife",String.valueOf(currentMaxLife));
    }
    public int getMaxLife(){
        return Integer.valueOf(gameVariables.getProperty("maxLife"));
    }
    public int getCurrentMaxBullet(){
        return Integer.valueOf(gameVariables.getProperty("currentMaxBullet"));
    }
    public void setCurrentMaxBullet(final int currentMaxBullet){
        gameVariables.setProperty("currentMaxBullet",String.valueOf(currentMaxBullet));
    }
    public int getMaxBullet(){
        return Integer.valueOf(gameVariables.getProperty("maxBullet"));
    }

    public void saveData(){

        try {
            Files.write(Path.of(GAME_PROPERTIES_URL),makeData(gameProperties.toString()).getBytes());
        } catch (IOException e) {
            System.out.println("Failed saving game properties");
        }
        try {
            Files.write(Path.of(GAME_VARIABLES_URL),makeData(gameVariables.toString()).getBytes());
        } catch (IOException e) {
            System.out.println("Failed saving game variables properties");
        }
    }

    public static GameConfig getInstance() {
        if (instance == null)
            instance = new GameConfig();
        return instance;
    }
}
