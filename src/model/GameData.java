package model;

import utils.GameConfig;

public class GameData implements IGameData{
    private static IGameData instance = null;
    private static final int SCORE_INCREMENT = 2;
    private static final int MAX_LIFE = GameConfig.getInstance().getMaxLife();
    private static final int MAX_BULLETS = GameConfig.getInstance().getMaxBullet();
    private int currentMaxLife = GameConfig.getInstance().getCurrentMaxLife();
    private int currentMaxBullets = GameConfig.getInstance().getCurrentMaxBullet();
    private int recordScore = GameConfig.getInstance().getRecordScore();
    private int totalCoin = GameConfig.getInstance().getTotalCoin();
    private int currentScore;
    private int currentCoin;
    private int currentLife = currentMaxLife;
    private int currentBullets = currentMaxBullets;


    private GameData(){}
    public static IGameData getInstance() {
        if (instance == null){
            instance = new GameData();
        }
        return instance;
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
        currentCoin++;
        totalCoin++;
    }

    @Override
    public void updateCurrentCoin(int coinVariation) {
        currentCoin+=coinVariation;
        totalCoin+=coinVariation;
    }

    @Override
    public int getTotalCoin() {
        return totalCoin;
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
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    public void updateCurrentScore() {
        this.currentScore+=SCORE_INCREMENT;
    }

    @Override
    public void updateCurrentScore(int scoreVariation) {
        currentScore+=scoreVariation;
    }

    @Override
    public int getRecordScore() {
        if(currentScore > recordScore)
            recordScore = currentScore;
        return recordScore;
    }

    @Override
    public void resetData() {
        currentScore = 0;
        currentCoin = 0;
        currentBullets = currentMaxBullets;
        currentLife = currentMaxLife;
    }
}
