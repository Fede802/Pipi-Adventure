package Controller;

import Model.GameModel;
import View.GameView;

public class GameEngine implements IGameEngine{

    private static GameEngine instance = null;
    private GameEngine(){}
    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }

    @Override
    public int getSectionSize() {
        return GameModel.getInstance().getSectionSize();
    }

    @Override
    public int getMapLength() {
        return GameModel.getInstance().getMapLength();
    }

    @Override
    public int getMapTraslX() {
        return GameModel.getInstance().getMapTraslX();
    }

    @Override
    public int getTileData(final int mapIndex,final int mapX,final int mapY) {
        return GameModel.getInstance().getTileData(mapIndex,mapX,mapY);
    }

    @Override
    public void updateGameStatus() {
        GameModel.getInstance().updateGameStatus();
        if(GameModel.getInstance().getMapTraslX() == GameView.getInstance().getRenderedTileSize()*GameModel.getInstance().getSectionSize()) {
            GameModel.getInstance().setMapTraslX(0);
            GameModel.getInstance().updateMap();
        }
        JumpAndFallHandler.jumpAndFall(isJumping(),getPlayerInfo());
    }

    @Override
    public int[] getPlayerInfo() {
        return GameModel.getInstance().getPlayerInfo();
    }
    @Override
    public void setPlayerInfo(final int mapIndex, final int mapX, final int traslX, final int mapY, final int traslY) {
        GameModel.getInstance().setPlayerInfo(mapIndex,mapX,traslX,mapY,traslY);
    }

    @Override
    public void setJumping(boolean jumping) {
        if(!CollisionHandler.bottomCollision(GameModel.getInstance().getPlayerInfo())&&!isJumping())
            jumping = false;
        GameModel.getInstance().setJumping(jumping);
    }

    @Override
    public boolean isJumping() {
        return GameModel.getInstance().isJumping();
    }

    @Override
    public void jump() {
        GameModel.getInstance().jump();
    }

    @Override
    public void fall() {
        GameModel.getInstance().fall();
    }
}
