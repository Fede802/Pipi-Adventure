package controller;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;
import model.GameModel;
import model.GameStatus;
import utils.GameConfig;
import utils.GameDataConfig;
import view.GameView;


import java.util.ArrayList;

public class GameEngine implements IGameEngine {
    private static final JumpAndFallHandler JUMP_AND_FALL_HANDLER = new JumpAndFallHandler();
    private static int currentTileSize = GameDataConfig.getInstance().getRenderedTileSize();
    private static final int IMMORTALITY_STEP = 15;
    private int currentImmortalityStep = 0;
    private static GameEngine instance = null;
    private GameEngine(){}
    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }
    @Override
    public void notifySizeChanged() {
        currentTileSize = GameDataConfig.getInstance().getRenderedTileSize();
        GameView.getInstance().notifySizeChanged();
        GameModel.getInstance().changeCoordinate();
    }

    @Override
    public void updateGameStatus() {

        updateMap();
        //check if player(+ bullets) has to jump or fall or collide with entity or map
        updatePlayer();
        //move entity and check if they collide with bullets or player
        updateEntity();
        System.out.println(GameModel.getInstance().getMapTraslX());
        CollisionHandler.entityCollision();

    }

    @Override
    public ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesForRendering() {
        return GameModel.getInstance().getEntitiesForRendering();
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

    @Override
    public void shoot() {
        System.out.println("shoot");
        GameModel.getInstance().shoot();
    }

    @Override
    public void setupGame() {
        GameStatus.getInstance().updateScore();
        GameModel.getInstance().setup();
        System.out.println(GameModel.getInstance().getMapTraslX());
    }

    private void updateMap(){
        int mapLength = currentTileSize*GameModel.getInstance().getSectionSize();
        GameModel.getInstance().updateMapTraslX();
            if(GameModel.getInstance().getMapTraslX() >= mapLength){
                GameModel.getInstance().setMapTraslX(GameModel.getInstance().getMapTraslX()-mapLength);
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
