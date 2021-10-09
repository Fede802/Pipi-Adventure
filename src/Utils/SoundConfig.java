package utils;

public class SoundConfig extends Config{
    private static final String SOUND_PROPERTIES_URL = "Config/SoundProperties.txt";
    private static SoundConfig instance = null;


    private SoundConfig(){
        super(SOUND_PROPERTIES_URL);
    }

    public boolean isAudioActive(){
        return properties.getProperty("isAudioActive","true").equals("true");
    }
    public void setAudioActive(final boolean isAudioActive){
        properties.setProperty("isAudioActive",String.valueOf(isAudioActive));
    }


    public static SoundConfig getInstance() {
        if (instance == null)
            instance = new SoundConfig();
        return instance;
    }
}

