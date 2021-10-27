package controller;

import commons.*;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;
import view.GameView;

import java.util.ArrayList;

public class GameEngine implements IGameEngine{

    private static final int CHANGE_DAYTIME_SCORE = 2000;
    private final int BULLET_INCREMENT_SCORE = 100;
    private final int TICK_TO_UPDATE_ANIMATION = 5;
    private PlayerHandler playerHandler = new PlayerHandler();
    private EnemyHandler enemyHandler = new EnemyHandler();
    private BulletsHandler bulletsHandler = new BulletsHandler();
    private CoinHandler coinHandler = new CoinHandler();
    private Pair<EntityCoordinates,Animation> renderingPair = new Pair<>(null,null);
    private Pair<EntityType,Integer> entity = new Pair<>(null,null);
    private int currentTick;
    private boolean updateAnimation = false;
    private static int currentTileSize = GameDataConfig.getInstance().getRenderedTileSize();
    long startTime;
    boolean a = true;
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
        updateEntity();
        if(!playerHandler.isDying()) {
            GameModel.getInstance().moveEntity();
            playerHandler.jumpAndFall();
            if (playerHandler.mapUpdate())
                GameModel.getInstance().updateMap();
            GameData.getInstance().updateCurrentScore();
            if (GameData.getInstance().getCurrentScore() % BULLET_INCREMENT_SCORE == 0)
                GameData.getInstance().updateCurrentBullets();
            if (GameData.getInstance().getCurrentScore() % CHANGE_DAYTIME_SCORE == 0)
                GameView.getInstance().updateDayTime();
            checkMapCollision();
            checkEntityCollision();
            GameView.getInstance().updateGameBar(GameData.getInstance().getCurrentScore(),
                    GameData.getInstance().getCurrentCoin(),
                    GameData.getInstance().getCurrentLife(),
                    GameData.getInstance().getCurrentBullets());
        }
        if(currentTick == TICK_TO_UPDATE_ANIMATION){
            currentTick = 0;
            updateAnimation = true;
        }else{
            currentTick++;
        }

    }
    private Pair<EntityType,Integer> findEntityType(int entityID){
        int coinCount = GameModel.getInstance().getEntityCount(EntityType.COIN);
        int enemyCount = GameModel.getInstance().getEntityCount(EntityType.ENEMY);
        EntityType type;
        if(entityID < coinCount){
            type = EntityType.COIN;
        }else if(entityID < coinCount+enemyCount){
            entityID-=coinCount;
            type = EntityType.ENEMY;
        }else if(entityID == coinCount+enemyCount){
            type = EntityType.PLAYER;
        }else{
            entityID-=(coinCount+enemyCount+1);
            type = EntityType.BULLET;
        }
        entity.updateKey(type);
        entity.updateValue(entityID);
        return entity;
    }
    private void updateEntity() {
        boolean isDead;
        Pair<EntityType,Integer> type;
        int totalEntity =getTotalEntity();
        for(int entityID = 0; entityID < totalEntity; entityID++){
            type=findEntityType(entityID);
            isDead = GameModel.getInstance().isDead(type.getKey(),type.getValue());
            if(isDead)
                if (type.getKey() == EntityType.PLAYER) {
                    //TODO save best score
                    GameView.getInstance().setGameOverData(GameData.getInstance().getCurrentScore(),GameData.getInstance().getRecordScore(),GameData.getInstance().getCurrentCoin());
                    GameStateHandler.getInstance().gameOver();
                } else {
                    GameModel.getInstance().updateEntitiesStatus(type.getKey(), type.getValue());
                }


        }
    }

    private void checkEntityCollision() {
        //todo problem when dying
        if(!playerHandler.isImmortal())
            playerHandler.checkEntityCollision(enemyHandler);
        playerHandler.checkEntityCollision(coinHandler);
        bulletsHandler.checkEntityCollision(enemyHandler);
    }

    private void checkMapCollision() {
        if(!playerHandler.isJumping() && !playerHandler.isFalling() && !playerHandler.isImmortal())
            playerHandler.rigthCollision();

        bulletsHandler.rigthCollision();
        //bullet and player with map
    }



    @Override
    public Pair<EntityCoordinates, Animation> getEntityForRendering(int entityID) {

        Pair<EntityType,Integer> type = findEntityType(entityID);
        Animation animation = GameModel.getInstance().getEntityAnimation(type.getKey(),type.getValue());
        EntityCoordinates entity = GameModel.getInstance().getEntityCoordinates(type.getKey(),type.getValue(), EntityStatus.ALL);
        renderingPair.updateKey(entity);
        if(updateAnimation){
            if(!playerHandler.isDying() || type.getKey() == EntityType.PLAYER)
            animation.update();
            if(type.getKey() == EntityType.PLAYER && playerHandler.isImmortal()) {
                animation.switchOpacity();
                playerHandler.updateImmortalityStep();
            }
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
        GameView.getInstance().isGameRunning(true);
        GameModel.getInstance().setup();
        playerHandler.setDying(false);
        playerHandler.setImmortal(false);
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
