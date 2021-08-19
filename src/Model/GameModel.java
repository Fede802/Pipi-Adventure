package Model;

import View.GameView;
import View.IGameView;

public class GameModel implements IGameModel{

    private static GameModel instance = null;
    private MapGenerator generator;
    private GameModel(){
        generator = new MapGenerator();
    }
    public static IGameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }

    @Override
    public double getMapTralsX() {
        return generator.getTraslX();
    }

    @Override
    public void setMapTraslX(int mapTraslX) {
        generator.setTraslX(mapTraslX);
    }


    @Override
    public int[] getEntityPosition() {
        return new int[0];
    }

    @Override
    public void setEntityPosition(int[] entityPosition) {

    }

    @Override
    public double getEntityTraslY() {
        return 0;
    }

    @Override
    public void setEntityTraslY(double entityTraslY) {

    }

    @Override
    public void updateGameStatus() {
        generator.setTraslX(getMapTralsX()+MapGenerator.VEL_X);
        generator.playerCollision();
    }

    @Override
    public void updateMap() {
        generator.updateMap();
    }

    @Override
    public int getTileData(int mapIndex, int iIndex, int jIndex) {
        return generator.getTileData(mapIndex,iIndex,jIndex);
    }

    @Override
    public int[] getPlayerPosition() {
        return generator.getPlayerPosition();
    }

    @Override
    public void jump() {
        generator.jump();
    }

    @Override
    public void jumping() {
        generator.jumper();
    }

}
