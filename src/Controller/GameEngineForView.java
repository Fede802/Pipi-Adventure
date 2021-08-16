package Controller;

import Model.GameModel;
import Utils.CostantField;
import View.GameView;
import View.IGameView;

public class GameEngineForView implements IGameEngineForView{
    private static GameEngineForView instance = null;
    private GameEngineForView(){}
    public static GameEngineForView getInstance() {
        if (instance == null)
            instance = new GameEngineForView();
        return instance;
    }

    @Override
    public void updateGameStatus() {
        GameModel.getInstance().updateGameStatus();
    }

    @Override
    public double getMapTraslation() {
        return GameModel.getInstance().getMapTralsX();
    }

    @Override
    public void setMapTraslation() {
        GameModel.getInstance().jumping();
        GameModel.getInstance().updateGameStatus();
        if(GameModel.getInstance().getMapTralsX() == CostantField.RENDERED_TILE_SIZE *CostantField.NUM_SECTION_COLUMN) {
            GameModel.getInstance().setMapTraslX(0);
            System.out.println("ok");
            GameModel.getInstance().updateMap();
        }
    }

    @Override
    public int[] getEntityPosition() {
        return new int[0];
    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void restartGame() {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public int getTileData(int mapIndex, int iIndex, int jIndex) {
        return GameModel.getInstance().getTileData(mapIndex,iIndex,jIndex);
    }

    @Override
    public void jump() {
        GameModel.getInstance().jump();
    }

    @Override
    public int[] getPlayerPosition() {
        return GameModel.getInstance().getPlayerPosition();
    }
}
