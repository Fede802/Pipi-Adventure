package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SoundConfig extends Config{
    private static final String AUDIO_PROPERTIES_URL = "Config/SoundProperties.txt";
    private static SoundConfig instance = null;
    private final Properties audioProperties = new Properties();

    private SoundConfig(){
        try {
            BufferedReader reader = Files.newBufferedReader(Path.of(AUDIO_PROPERTIES_URL));
            audioProperties.load(reader);
        } catch (IOException e) {
            System.out.println("Failed loading audio configuration file");
        }
    }

    public boolean isAudioActive(){
        return audioProperties.getProperty("isAudioActive","true").equals("true");
    }
    public void setAudioActive(final boolean isAudioActive){
        audioProperties.setProperty("isAudioActive",String.valueOf(isAudioActive));
    }
    @Override
    public void saveData(){
        try {
            Files.write(Path.of(AUDIO_PROPERTIES_URL),makeData(audioProperties.toString()).getBytes());
        } catch (IOException e) {
            System.out.println("Failed saving audio properties");
        }
    }

    public static SoundConfig getInstance() {
        if (instance == null)
            instance = new SoundConfig();
        return instance;
    }
}
