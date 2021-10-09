package controller;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;
import commons.Pair;
import model.GameModel;
import utils.GameDataConfig;
import view.GameView;

import java.util.ArrayList;

public class GameEngine implements IGameEngine{

    private PlayerHandler playerHandler = new PlayerHandler();
    private EnemyHandler enemyHandler = new EnemyHandler();
    private BulletsHandler bulletsHandler = new BulletsHandler();
    private CoinHandler coinHandler = new CoinHandler();
    private Pair<EntityCoordinates,Animation> renderingPair = new Pair<>(null,null);
    private static int currentTileSize = GameDataConfig.getInstance().getRenderedTileSize();

    private static GameEngine instance = null;
    private GameEngine(){}
    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }
    @Override
    public void updateGameStatus() {
        GameModel.getInstance().moveEntity();
        playerHandler.jumpAndFall();
        if(playerHandler.mapUpdate())
            GameModel.getInstance().updateMap();
//    debug();
        checkMapCollision();
        checkEntityCollision();
    }

    private void checkEntityCollision() {
        playerHandler.checkEntityCollision(coinHandler);
        bulletsHandler.checkEntityCollision(enemyHandler);
    }

    private void checkMapCollision() {
        //bullet and player with map
    }



    @Override
    public Pair<EntityCoordinates, Animation> getEntityForRendering(int entityID) {
        renderingPair.updateKey(GameModel.getInstance().getEntityCoordinates(EntityType.ALL,entityID));
        renderingPair.updateValue(GameModel.getInstance().getEntityAnimation(entityID));
        return renderingPair;
    }



    @Override
    public int getTileData(int mapIndex, int mapX, int mapY) {
        return GameModel.getInstance().getTileData(mapIndex,mapX,mapY);
    }



    @Override
    public void setJumping(boolean isJumping) {
        if(!playerHandler.bottomCollision()&&!GameModel.getInstance().isPlayerJumping())
            isJumping = false;
        GameModel.getInstance().setPlayerJumping(isJumping);
    }

    @Override
    public double getMapTranslX() {
        return playerHandler.playerTotalTranslation();
    }

    @Override
    public void shoot() {
        GameModel.getInstance().shoot();
    }

    @Override
    public void setupGame() {
        GameModel.getInstance().setup();
    }

    @Override
    public void notifySizeChanged() {
        currentTileSize = GameDataConfig.getInstance().getRenderedTileSize();
        GameView.getInstance().notifySizeChanged();
        GameModel.getInstance().changeCoordinate();
    }

    @Override
    public void debug() {
        int count = 0;
        System.out.println("debug");
        while(playerHandler.hasNext(EntityType.ALL) && count < 1000000){

            System.out.println(count);
            count++;
            while(coinHandler.hasNext(EntityType.ALL) && count < 1000000){
                playerHandler.getNext();
                coinHandler.getNext();
                System.out.println(count);
                count++;

            }

        }

    }

    @Override
    public int getTotalEntity() {
        return playerHandler.getEntityNum(EntityType.ALL)+bulletsHandler.getEntityNum(EntityType.ALL)+coinHandler.getEntityNum(EntityType.ALL)+enemyHandler.getEntityNum(EntityType.ALL);
    }
}
