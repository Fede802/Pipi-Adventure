package utils;

public class SoundConfig extends Config {

    private static final String SOUND_PROPERTIES_URL = "Config/SoundProperties.txt";
    private static SoundConfig instance = null;

    private SoundConfig(){
        super(SOUND_PROPERTIES_URL);
    }

    public boolean isSoundActive(){
        return PROPERTIES.getProperty("isSoundActive").equals("true");
    }

    public void setSoundActive(final boolean isSoundActive) {
        PROPERTIES.setProperty("isSoundActive",String.valueOf(isSoundActive));
        saveData();
    }

    public boolean isMusicActive(){
        return PROPERTIES.getProperty("isMusicActive").equals("true");
    }

    public void setMusicActive(final boolean isMusicActive) {
        PROPERTIES.setProperty("isMusicActive",String.valueOf(isMusicActive));
        saveData();
    }

    public static SoundConfig getInstance() {
        if (instance == null)
            instance = new SoundConfig();
        return instance;
    }

}

