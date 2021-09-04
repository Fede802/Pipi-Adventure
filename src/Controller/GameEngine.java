package Controller;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;
import Model.ApplicationStatus;
import Model.GameModel;
import Utils.Config;
import Utils.GameConfig;
import View.GameView;

import java.util.ArrayList;

public class GameEngine implements IGameEngine{
    private static final JumpAndFallHandler JUMP_AND_FALL_HANDLER = new JumpAndFallHandler();
    private static int prevTileSize = GameConfig.getInstance().getRenderedTileSize();
    private static GameEngine instance = null;
    private GameEngine(){}
    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }

    @Override
    public void updateGameStatus() {
        int currentTileSize = GameConfig.getInstance().getRenderedTileSize();
        if(prevTileSize != currentTileSize){
            GameModel.getInstance().changeCoordinate();
            prevTileSize = currentTileSize;
            System.out.println("update");
        }
        if (GameModel.getInstance().isPlayerDead())
            GameStateHandler.getInstance().gameOver();
        //increment map traslation and if necessary update generatedMap
        if(!GameModel.getInstance().isPlayerDying())
            updateMap();
        //check if player(+ bullets) has to jump or fall or collide with entity or map
        updatePlayer();
        //move entity and check if they collide with bullets or player
        updateEntity();
        CollisionHandler.entityCollision();
        ApplicationStatus.getInstance().setScore();
        GameView.getInstance().updateGameBar(ApplicationStatus.getInstance().getScore(),ApplicationStatus.getInstance().getCoin(),2,0);
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
    public double getMapTraslX() {
        return GameModel.getInstance().getMapTraslX();
    }

    @Override
    public void setJumping(boolean isJumping) {
        if(!CollisionHandler.playerBottomCollision()&&!GameModel.getInstance().isPlayerJumping())
            isJumping = false;
        GameModel.getInstance().setPlayerJumping(isJumping);
    }

    private void updateMap(){
//        System.out.println("MAP TRASLX "+GameModel.getInstance().getMapTraslX());
//        System.out.println("ROUND MAP TRASLX "+Math.round(GameModel.getInstance().getMapTraslX()));
        if(Math.round(GameModel.getInstance().getMapTraslX()) >= prevTileSize*GameModel.getInstance().getSectionSize()){
            GameModel.getInstance().setMapTraslX(0);
            GameModel.getInstance().updateMap();
        }
        GameModel.getInstance().updateMapTraslX();

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

