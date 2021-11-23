package model;

public interface IGameData {

    int getCurrentMaxLife();
    void updateCurrentMaxLife();

    int getCurrentLife();
    void setCurrentLife(int currentLife);
    void updateCurrentLife();
    void updateCurrentLife(int lifeVariation);

    int getCurrentMaxBullets();
    void updateCurrentMaxBullets();

    int getCurrentBullets();
    void setCurrentBullets(int currentBullets);
    void updateCurrentBullets();
    void updateCurrentBullets(int bulletsVariation);

    int getTotalCoin();
    void updateTotalCoin(int coinVariation);

    int getCurrentCoin();
    void setCurrentCoin(int currentCoin);
    void updateCurrentCoin();
    void updateCurrentCoin(int coinVariation);

    int getRecordScore();

    int getCurrentScore();
    void setCurrentScore(int currentScore);
    void updateCurrentScore();
    void updateCurrentScore(int scoreVariation);

    void resetData();
    void saveData();

}
