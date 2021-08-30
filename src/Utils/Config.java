package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;

public class Config {
    private static final String AUDIO_PROPERTIES_URL = "Config/SoundProperties.txt";
    private static final String GAME_PROPERTIES_URL = "Config/GameSaves.txt";
    private static final String GAME_VARIABLES_URL = "Config/GameVariables.txt";
    private static Config instance = null;
    private final Properties audioProperties = new Properties(),gameProperties = new Properties(),gameVariables = new Properties();

    private Config(){
        try {
            BufferedReader reader = Files.newBufferedReader(Path.of(AUDIO_PROPERTIES_URL));
            audioProperties.load(reader);
        } catch (IOException e) {
            System.out.println("Failed loading audio configuration file");
        }
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

    public int getBestScore(){
        return Integer.valueOf(gameProperties.getProperty("bestScore"));
    }

    public void setBestScore(int bestScore){
        gameProperties.setProperty("bestScore",String.valueOf(bestScore));
    }

    public int getTotalCoin(){
        return Integer.valueOf(gameProperties.getProperty("totalCoin"));
    }

    public void setTotalCoin(int totalCoin){
        gameProperties.setProperty("totalCoin",String.valueOf(totalCoin));
    }

    public boolean isAudioActive(){
        return audioProperties.getProperty("isAudioActive","true").equals("true");
    }

    public void setAudioActive(boolean isAudioActive){
        audioProperties.setProperty("isAudioActive",String.valueOf(isAudioActive));
    }

    public int getAudioVolume(){
        return Integer.valueOf(audioProperties.getProperty("audioVolume"));
    }

    public void setAudioVolume(int audioVolume){
        audioProperties.setProperty("audioVolume",String.valueOf(audioVolume));
    }

    public int getRenderedTileSize(){
        return Integer.valueOf(gameVariables.getProperty("renderedTileSize"));
    }
    public void setRenderedTileSize(int renderedTileSize){
        gameVariables.setProperty("renderedTileSize",String.valueOf(renderedTileSize));
    }

    public void saveData(){
        try {
            Files.write(Path.of(AUDIO_PROPERTIES_URL),makeData(audioProperties.toString()).getBytes());
        } catch (IOException e) {
            System.out.println("Failed saving audio properties");
        }
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

    private String makeData(String str){
        String data = "";
        str = str.replace("{","");
        str = str.replace("}","");
        str = str.trim();
        str = str.replaceAll(" ","");
        String[] tempData = str.split(",");
        for(int i = 0; i < tempData.length; i++)
            data+=tempData[i]+"\n";
        return data;
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }
}
