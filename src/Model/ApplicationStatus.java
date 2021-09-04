package Model;

import Controller.GameEngine;

public class ApplicationStatus implements IApplicationStatus{
    private final GameStatus gameStatus= new GameStatus();
    private static IApplicationStatus instance = null;
    private ApplicationStatus(){}
    public static IApplicationStatus getInstance() {
        if (instance == null)
            instance = new ApplicationStatus();
        return instance;
    }

    @Override
    public int getScore() {
        return gameStatus.getScore();
    }

    @Override
    public void setScore() {
        gameStatus.setScore(gameStatus.getScore()+1);
    }

    @Override
    public int getCoin() {
        return gameStatus.getCoin();
    }

    @Override
    public void setCoin(int coin) {
        gameStatus.setCoin(gameStatus.getCoin()+1);
    }
}
