package model;

public interface IGameData {
    int getCurrentLife();
    void setCurrentLife(int currentLife);
    void updateCurrentLife();
    void updateCurrentLife(int lifeVariation);

    int getCurrentMaxLife();
    void updateCurrentMaxLife();

    int getCurrentMaxBullets();
    void updateCurrentMaxBullets();

    int getCurrentCoin();
    void setCurrentCoin(int currentCoin);
    void updateCurrentCoin();
    void updateCurrentCoin(int coinVariation);
    int getTotalCoin();
    void updateTotalCoin(int coinVariation);

    int getCurrentBullets();
    void setCurrentBullets(int currentBullets);
    void updateCurrentBullets();
    void updateCurrentBullets(int bulletsVariation);

    int getCurrentScore();
    void setCurrentScore(int currentScore);
    void updateCurrentScore();
    void updateCurrentScore(int scoreVariation);
    int getRecordScore();

    void resetData();
    void saveData();

}
