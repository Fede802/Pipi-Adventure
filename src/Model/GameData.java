package model;

import utils.GameConfig;
import utils.GameDataConfig;

public class GameData implements IGameData {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static IGameData instance;

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final int SCORE_INCREMENT = GameConfig.getInstance().getScoreIncrement();

    private int currentMaxLives = GameConfig.getInstance().getCurrentMaxLives();
    private int currentLives;
    private int currentMaxBullets = GameConfig.getInstance().getCurrentMaxBullets();
    private int currentBullets;
    private int totalCoins = GameConfig.getInstance().getTotalCoins();
    private int currentCoins;
    private int recordScore = GameConfig.getInstance().getRecordScore();
    private int currentScore;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private GameData(){
        int temp = GameDataConfig.getInstance().getMinBullets();
        if(currentMaxBullets < temp)
            currentMaxBullets = temp;
        temp = GameDataConfig.getInstance().getMaxBullets();
        if(currentMaxBullets > temp)
            currentMaxBullets = temp;
        currentBullets = currentMaxBullets;
        temp = GameDataConfig.getInstance().getMaxLives();
        if(currentMaxLives > temp)
            currentMaxLives = temp;
        currentLives = currentMaxLives;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public int getCurrentMaxLives() {
        return currentMaxLives;
    }

    @Override
    public void updateCurrentMaxLives() {
        currentMaxLives++;
    }

    @Override
    public int getCurrentLives() {
        return currentLives;
    }

    @Override
    public void setCurrentLives(int currentLives) {
        if(currentLives > currentMaxLives)
            currentLives = currentMaxLives;
        this.currentLives = currentLives;
    }

    @Override
    public void updateCurrentLives() {
        currentLives--;
    }

    @Override
    public void updateCurrentLives(int livesVariation) {
        this.currentLives +=livesVariation;
        if(currentLives > currentMaxLives)
            currentLives = currentMaxLives;
    }

    @Override
    public int getCurrentMaxBullets() {
        return currentMaxBullets;
    }

    @Override
    public void updateCurrentMaxBullets() {
        currentMaxBullets++;
    }

    @Override
    public int getCurrentBullets() {
        return currentBullets;
    }

    @Override
    public void setCurrentBullets(int currentBullets) {
        if(currentBullets > currentMaxBullets)
            currentBullets = currentMaxBullets;
        this.currentBullets = currentBullets;
    }

    @Override
    public void updateCurrentBullets() {
        if(currentBullets < currentMaxBullets)
            currentBullets++;
    }
    @Override
    public void updateCurrentBullets(int bulletsVariation) {
        this.currentBullets += bulletsVariation;
        if(currentBullets > currentMaxBullets)
            currentBullets = currentMaxBullets;
    }

    @Override
    public int getTotalCoins() {
        return totalCoins;
    }

    @Override
    public void updateTotalCoins(int coinsVariation) {
        totalCoins -=coinsVariation;
    }

    @Override
    public int getCurrentCoins() {
        return currentCoins;
    }

    @Override
    public void setCurrentCoins(int currentCoins) {
        totalCoins -= this.currentCoins;
        this.currentCoins = currentCoins;
        totalCoins += currentCoins;
    }

    @Override
    public void updateCurrentCoins() {
        if(currentCoins != Integer.MAX_VALUE)
            currentCoins++;
        if(totalCoins != Integer.MAX_VALUE)
            totalCoins++;
    }

    @Override
    public void updateCurrentCoins(int coinsVariation) {
        currentCoins +=coinsVariation;
        totalCoins +=coinsVariation;
    }

    @Override
    public int getRecordScore() {
        if(currentScore > recordScore)
            recordScore = currentScore;
        return recordScore;
    }

    @Override
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    public void updateCurrentScore() {
        if(Integer.MAX_VALUE-SCORE_INCREMENT > currentScore)
            this.currentScore+=SCORE_INCREMENT;
        else
            currentScore = Integer.MAX_VALUE;
    }

    @Override
    public void updateCurrentScore(int scoreVariation) {
        currentScore+=scoreVariation;
    }

    @Override
    public void resetData() {
        currentScore = 0;
        currentCoins = 0;
        currentBullets = currentMaxBullets;
        currentLives = currentMaxLives;
    }

    @Override
    public void saveData() {
        GameConfig.getInstance().setCurrentMaxLives(currentMaxLives);
        GameConfig.getInstance().setCurrentMaxBullets(currentMaxBullets);
        GameConfig.getInstance().setTotalCoins(totalCoins);
        GameConfig.getInstance().setRecordScore(recordScore);
        System.out.println("Saving game data");
        GameConfig.getInstance().saveData();
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static IGameData getInstance() {
        if (instance == null){
            instance = new GameData();
        }
        return instance;
    }
}
