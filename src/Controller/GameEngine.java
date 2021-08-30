package Controller;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;
import Model.GameModel;
import Utils.Config;
import View.GameView;

import java.util.ArrayList;

public class GameEngine implements IGameEngine{
    private static final JumpAndFallHandler JUMP_AND_FALL_HANDLER = new JumpAndFallHandler();
    private static GameEngine instance = null;
    private GameEngine(){}
    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }

    @Override
    public void updateGameStatus() {
        //increment map traslation and if necessary update generatedMap
        updateMap();
        //check if player(+ bullets) has to jump or fall or collide with entity or map
        updatePlayer();
        //move entity and check if they collide with bullets or player
        updateEntity();
        CollisionHandler.entityCollision();
        GameModel.getInstance().updateScore();
        GameView.getInstance().updateGameBar(GameModel.getInstance().getScore(),GameModel.getInstance().getCoin(),GameModel.getInstance().getLife(),0);
        //TODO deadly collision detection -> gameOver or add coin or !isAlive entity
        //TODO update score
    }

    @Override
    public ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesCoordinates() {
        return GameModel.getInstance().getEntitiesCoordinates();
    }

    @Override
    public int getTileData(final int mapIndex, final int mapX, final int mapY) {
        return GameModel.getInstance().getTileData(mapIndex,mapX,mapY);
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
    public void setJumping(boolean isJumping) {
        if(!CollisionHandler.playerBottomCollision()&&!GameModel.getInstance().isPlayerJumping())
            isJumping = false;
        GameModel.getInstance().setPlayerJumping(isJumping);
    }

    private void updateMap(){
        GameModel.getInstance().updateMapTraslX();
        if(GameModel.getInstance().getMapTraslX() >= Config.getInstance().getRenderedTileSize()*GameModel.getInstance().getSectionSize()){
            GameModel.getInstance().setMapTraslX(0);
            GameModel.getInstance().updateMap();
        }
    }

    private void updatePlayer(){
        GameModel.getInstance().updatePlayerMapPosition();
        JUMP_AND_FALL_HANDLER.jumpAndFall();
        //CollisionHandler.playerRigthCollision -> dead
        //
    }

    private void updateEntity(){
        GameModel.getInstance().updateEntitiesMapPosition();
    }
}
