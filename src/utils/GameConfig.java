package utils;
public class GameConfig extends Config {

    private static final String GAME_PROPERTIES_URL = "Config/GameProperties.txt";
    private static GameConfig instance = null;

    private GameConfig(){
        super(GAME_PROPERTIES_URL);
    }

    public int getChangeDaytimeScore(){
        return Integer.parseInt(PROPERTIES.getProperty("changeDaytimeScore"));
    }

    public int getBulletIncrementScore(){
        return Integer.parseInt(PROPERTIES.getProperty("bulletIncrementScore"));
    }

    public int getTickToUpdateAnimation(){
        return Integer.parseInt(PROPERTIES.getProperty("tickToUpdateAnimation"));
    }

    public int getScoreIncrement() {
        return Integer.parseInt(PROPERTIES.getProperty("scoreIncrement"));
    }

    public int getUpdateTickDelay() {
        return Integer.parseInt(PROPERTIES.getProperty("updateTickDelay"));
    }

    public int getDissolutionStep() {
        return Integer.parseInt(PROPERTIES.getProperty("dissolutionStep"));
    }

    public int getRecordScore(){
        return Integer.valueOf(PROPERTIES.getProperty("recordScore"));
    }

    public void setRecordScore(int recordScore){
        PROPERTIES.setProperty("recordScore",String.valueOf(recordScore));
    }

    public int getTotalCoin(){
        int totalCoin = 0;
        String value = PROPERTIES.getProperty("totalCoin");
        try{
            totalCoin = Integer.parseInt(PROPERTIES.getProperty("totalCoin"));

        }catch (NumberFormatException nfe){
            boolean valid = true;
            for (int i = 0; i < value.length() && valid; i++) {
                if(!Character.isDigit(value.charAt(i)))
                    valid = false;
            }
            if(valid)
                totalCoin = Integer.MAX_VALUE;
        }
        return totalCoin;
    }

    public void setTotalCoin(int totalCoin){
        PROPERTIES.setProperty("totalCoin",String.valueOf(totalCoin));
    }

    public int getCurrentMaxBullet(){
        return Integer.valueOf(PROPERTIES.getProperty("currentMaxBullet"));
    }

    public void setCurrentMaxBullet(int currentMaxBullet){
        PROPERTIES.setProperty("currentMaxBullet",String.valueOf(currentMaxBullet));
    }

    public int getCurrentMaxLife(){
        return Integer.valueOf(PROPERTIES.getProperty("currentMaxLife"));
    }

    public void setCurrentMaxLife(int currentMaxLife){
        PROPERTIES.setProperty("currentMaxLife",String.valueOf(currentMaxLife));
    }

    public static GameConfig getInstance() {
        if (instance == null)
            instance = new GameConfig();
        return instance;
    }

}
