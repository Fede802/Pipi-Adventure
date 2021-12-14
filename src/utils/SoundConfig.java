package utils;

public class SoundConfig extends Config {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static final String SOUND_PROPERTIES_URL = "Config/SoundProperties.txt";
    private static SoundConfig instance;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private SoundConfig(){
        super(SOUND_PROPERTIES_URL);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public boolean isSoundActive(){
        return PROPERTIES.getProperty("isSoundActive").equals("true");
    }

    public void setSoundActive(boolean isSoundActive) {
        PROPERTIES.setProperty("isSoundActive",String.valueOf(isSoundActive));
        saveData();
    }

    public boolean isMusicActive(){
        return PROPERTIES.getProperty("isMusicActive").equals("true");
    }

    public void setMusicActive(boolean isMusicActive) {
        PROPERTIES.setProperty("isMusicActive",String.valueOf(isMusicActive));
        saveData();
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static SoundConfig getInstance() {
        if (instance == null)
            instance = new SoundConfig();
        return instance;
    }

}

