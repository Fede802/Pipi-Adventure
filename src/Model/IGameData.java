package model;

public interface IGameData {

    int getCurrentMaxLives();
    void updateCurrentMaxLives();

    int getCurrentLives();
    void setCurrentLives(int currentLives);
    void updateCurrentLives();
    void updateCurrentLives(int livesVariation);

    int getCurrentMaxBullets();
    void updateCurrentMaxBullets();

    int getCurrentBullets();
    void setCurrentBullets(int currentBullets);
    void updateCurrentBullets();
    void updateCurrentBullets(int bulletsVariation);

    int getTotalCoins();
    void updateTotalCoins(int coinsVariation);

    int getCurrentCoins();
    void setCurrentCoins(int currentCoins);
    void updateCurrentCoins();
    void updateCurrentCoins(int coinVariation);

    int getRecordScore();

    int getCurrentScore();
    void setCurrentScore(int currentScore);
    void updateCurrentScore();
    void updateCurrentScore(int scoreVariation);

    void resetData();
    void saveData();

}
