package controller;

import commons.*;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;
import view.GameView;

import java.util.ArrayList;

public class GameEngine implements IGameEngine{

    private final int BULLET_INCREMENT_SCORE = 100;
    private final int TICK_TO_UPDATE_ANIMATION = 5;
    private PlayerHandler playerHandler = new PlayerHandler();
    private EnemyHandler enemyHandler = new EnemyHandler();
    private BulletsHandler bulletsHandler = new BulletsHandler();
    private CoinHandler coinHandler = new CoinHandler();
    private Pair<EntityCoordinates,Animation> renderingPair = new Pair<>(null,null);
    private int currentTick;
    private boolean updateAnimation = false;
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
        updateAnimation = false;
        GameModel.getInstance().moveEntity();
        playerHandler.jumpAndFall();
        if(playerHandler.mapUpdate())
            GameModel.getInstance().updateMap();
        GameData.getInstance().updateCurrentScore();
        if(GameData.getInstance().getCurrentScore() % BULLET_INCREMENT_SCORE == 0)
            GameData.getInstance().updateCurrentBullets();
        checkMapCollision();
        checkEntityCollision();
        GameView.getInstance().updateGameBar(GameData.getInstance().getCurrentScore(),
                GameData.getInstance().getCurrentCoin(),
                GameData.getInstance().getCurrentLife(),
                GameData.getInstance().getCurrentBullets());
        if(currentTick == TICK_TO_UPDATE_ANIMATION){
            currentTick = 0;
            updateAnimation = true;
        }else{
            currentTick++;
        }
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
        Animation animation;
        EntityCoordinates entity;
        boolean isAlive = false;
        EntityType type = EntityType.COIN;
        int coinCount = GameModel.getInstance().getEntityCount(EntityType.COIN);
        int enemyCount = GameModel.getInstance().getEntityCount(EntityType.ENEMY);
        if(entityID < coinCount){
        }else if(entityID < coinCount+enemyCount){
            entityID-=coinCount;
            type = EntityType.ENEMY;
        }else if(entityID == coinCount+enemyCount){
            type = EntityType.PLAYER;
        }else{
            entityID-=(coinCount+enemyCount+1);
            type = EntityType.BULLET;
        }
        isAlive = GameModel.getInstance().isAlive(type,entityID);
        animation = GameModel.getInstance().getEntityAnimation(type,entityID);
        entity = GameModel.getInstance().getEntityCoordinates(type,entityID, EntityStatus.ALL);
        renderingPair.updateKey(entity);
//        if(!isAlive)
//        System.out.println(false);
//        if(animation.finish())
//            System.out.println(true);
//        if(animation.finish() && !isAlive){
//            GameModel.getInstance().updateEntitiesStatus(type,entityID,EntityStatus.DEAD);
//            System.out.println("dead");
//        }
        if(updateAnimation){
            animation.update();
//            System.out.println("update");
        }

        renderingPair.updateValue(animation);
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
        if(GameData.getInstance().getCurrentBullets() > 0){
            GameModel.getInstance().shoot();
            GameData.getInstance().updateCurrentBullets(-1);
        }
    }

    @Override
    public void setupGame() {
        GameData.getInstance().resetData();
        GameView.getInstance().updateGameBar(GameData.getInstance().getCurrentScore(),
                GameData.getInstance().getCurrentCoin(),
                GameData.getInstance().getCurrentLife(),
                GameData.getInstance().getCurrentBullets());
        GameModel.getInstance().setup();
    }

    @Override
    public void notifySizeChanged() {
        currentTileSize = GameDataConfig.getInstance().getRenderedTileSize();
        GameView.getInstance().notifySizeChanged();
        GameModel.getInstance().changeCoordinate();
    }



    @Override
    public int getTotalEntity() {
        return playerHandler.getEntityNum()+bulletsHandler.getEntityNum()+coinHandler.getEntityNum()+enemyHandler.getEntityNum();
    }
}
