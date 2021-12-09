package utils;

public class GameConfig extends Config {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static final String GAME_PROPERTIES_URL = "Config/GameProperties.txt";
    private static GameConfig instance = null;

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

    public int getBulletIncrementScore() {
        return inputChecker(100,"bulletIncrementScore");
    }

    public int getRecordScore() {
        return inputChecker(0,"recordScore");
    }

    public void setRecordScore(int recordScore) {
        PROPERTIES.setProperty("recordScore",String.valueOf(recordScore));
    }

    public int getTotalCoin() {
        return inputChecker(0,"totalCoin");
    }

    public void setTotalCoin(int totalCoin){
        PROPERTIES.setProperty("totalCoin",String.valueOf(totalCoin));
    }

    public int getCurrentMaxBullet() {
        return inputChecker(0,"currentMaxBullet");
    }

    public void setCurrentMaxBullet(int currentMaxBullet) {
        PROPERTIES.setProperty("currentMaxBullet",String.valueOf(currentMaxBullet));
    }

    public int getCurrentMaxLife() {
        return inputChecker(0,"currentMaxLife");
    }

    public void setCurrentMaxLife(int currentMaxLife) {
        PROPERTIES.setProperty("currentMaxLife",String.valueOf(currentMaxLife));
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
