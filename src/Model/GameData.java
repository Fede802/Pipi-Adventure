package model;

import utils.GameConfig;
import utils.GameDataConfig;

public class GameData implements IGameData {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static IGameData instance = null;

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final int SCORE_INCREMENT = GameConfig.getInstance().getScoreIncrement();

    private int currentMaxLife = GameConfig.getInstance().getCurrentMaxLife();
    private int currentLife;
    private int currentMaxBullets = GameConfig.getInstance().getCurrentMaxBullet();
    private int currentBullets;
    private int totalCoin = GameConfig.getInstance().getTotalCoin();
    private int currentCoin;
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
        if(currentMaxLife > temp)
            currentMaxLife = temp;
        currentLife = currentMaxLife;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public int getCurrentMaxLife() {
        return currentMaxLife;
    }

    @Override
    public void updateCurrentMaxLife() {
        currentMaxLife++;
    }

    @Override
    public int getCurrentLife() {
        return currentLife;
    }

    @Override
    public void setCurrentLife(int currentLife) {
        if(currentLife > currentMaxLife)
            currentLife = currentMaxLife;
        this.currentLife = currentLife;
    }

    @Override
    public void updateCurrentLife() {
        currentLife--;
    }

    @Override
    public void updateCurrentLife(int lifeVariation) {
        this.currentLife+=lifeVariation;
        if(currentLife > currentMaxLife)
            currentLife = currentMaxLife;
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
    public int getTotalCoin() {
        return totalCoin;
    }

    @Override
    public void updateTotalCoin(int coinVariation) {
        totalCoin-=coinVariation;
    }

    @Override
    public int getCurrentCoin() {
        return currentCoin;
    }

    @Override
    public void setCurrentCoin(int currentCoin) {
        totalCoin -= this.currentCoin;
        this.currentCoin = currentCoin;
        totalCoin += currentCoin;
    }

    @Override
    public void updateCurrentCoin() {
        if(currentCoin != Integer.MAX_VALUE)
            currentCoin++;
        if(totalCoin != Integer.MAX_VALUE)
            totalCoin++;
    }

    @Override
    public void updateCurrentCoin(int coinVariation) {
        currentCoin+=coinVariation;
        totalCoin+=coinVariation;
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
        currentCoin = 0;
        currentBullets = currentMaxBullets;
        currentLife = currentMaxLife;
    }

    @Override
    public void saveData() {
        GameConfig.getInstance().setCurrentMaxLife(currentMaxLife);
        GameConfig.getInstance().setCurrentMaxBullet(currentMaxBullets);
        GameConfig.getInstance().setTotalCoin(totalCoin);
        GameConfig.getInstance().setRecordScore(recordScore);
        //todo sout
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
