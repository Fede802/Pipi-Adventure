package utils;

public class GameConfig extends Config {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static final String GAME_PROPERTIES_URL = "Config/GameProperties.txt";
    private static GameConfig instance;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private GameConfig(){
        super(GAME_PROPERTIES_URL);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public int getScoreIncrement() {
        return inputChecker(2,"scoreIncrement");
    }

    public int getTickToUpdateAnimation() {
        return inputChecker(3,"tickToUpdateAnimation");
    }

    public int getUpdateTickDelay() {
        return inputChecker(40,"updateTickDelay");
    }

    public int getDissolutionStep() {
        return inputChecker(40,"dissolutionStep");
    }

    public int getChangeDaytimeScore() {
        return inputChecker(1000,"changeDaytimeScore");
    }

    public int getBulletsIncrementScore() {
        return inputChecker(100,"bulletsIncrementScore");
    }

    public int getRecordScore() {
        return inputChecker(0,"recordScore");
    }

    public void setRecordScore(int recordScore) {
        PROPERTIES.setProperty("recordScore",String.valueOf(recordScore));
    }

    public int getTotalCoins() {
        return inputChecker(0,"totalCoins");
    }

    public void setTotalCoins(int totalCoin){
        PROPERTIES.setProperty("totalCoins",String.valueOf(totalCoin));
    }

    public int getCurrentMaxBullets() {
        return inputChecker(0,"currentMaxBullets");
    }

    public void setCurrentMaxBullets(int currentMaxBullet) {
        PROPERTIES.setProperty("currentMaxBullets",String.valueOf(currentMaxBullet));
    }

    public int getCurrentMaxLives() {
        return inputChecker(0,"currentMaxLives");
    }

    public void setCurrentMaxLives(int currentMaxLives) {
        PROPERTIES.setProperty("currentMaxLives",String.valueOf(currentMaxLives));
    }

    public int getFlyingSpawnRange() {
        return inputChecker(10,"flyingSpawnRange");
    }

    private int inputChecker(int defaultValue, String varName) {
        String value = PROPERTIES.getProperty(varName);
        try{
            int tempValue = Integer.parseInt(value);
            if(tempValue >= 0)
                defaultValue = tempValue;
        }catch (NumberFormatException nfe){
            boolean valid = true;
            for (int i = 0; i < value.length() && valid; i++) {
                if(!Character.isDigit(value.charAt(i)))
                    valid = false;
            }
            if(valid)
                defaultValue = Integer.MAX_VALUE;
        }
        return defaultValue;
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static GameConfig getInstance() {
        if (instance == null)
            instance = new GameConfig();
        return instance;
    }

}
