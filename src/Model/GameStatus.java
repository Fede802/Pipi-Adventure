package model;

import utils.GameConfig;

public class GameStatus implements IGameStatus{

    private static final int SCORE_INCREMENT = 1;

    private int coin;
    private int score;

    private static IGameStatus instance = null;
    private GameStatus(){}
    public static IGameStatus getInstance() {
        if (instance == null){
            instance = new GameStatus();
        }
        return instance;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void updateScore() {
        score+=SCORE_INCREMENT;
    }

    @Override
    public int getCoin() {
        return coin;
    }

    @Override
    public void updateCoin() {
        coin++;
    }

    @Override
    public void updateData() {
//        GameConfig.getInstance().setTotalCoin(GameConfig.getInstance().getTotalCoin()+coin);
//        coin = 0;
//        if(score > GameConfig.getInstance().getBestScore())
//            GameConfig.getInstance().setBestScore(score);
//        score = 0;
    }
}
