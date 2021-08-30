package Model;

public class GameStatus {
    private int gameCoin;
    private int gameScore;
    private int life = 3;
    //TODO modify bullets and life
    private int bullets = 0;
    public GameStatus(){

    }
    public void reset(){
        gameCoin = 0;
        gameScore = 0;
        life = 3;
    }

    public void addCoin(){
        gameCoin++;
    }
    public void updateScore(){
        gameScore++;
    }
    public int getScore(){
        return gameScore;
    }
    public int getCoin(){
        return gameCoin;
    }
    public void looseLife(){
        life--;
    }
    public int getLife(){
        return life;
    }
}
