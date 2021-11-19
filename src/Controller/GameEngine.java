package controller;

import commons.*;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;
import view.GameView;

public class GameEngine implements IGameEngine{

    private static final int CHANGE_DAYTIME_SCORE = 1000;
    private final int BULLET_INCREMENT_SCORE = 100;
    private final int TICK_TO_UPDATE_ANIMATION = 3;
    private PlayerHandler playerHandler = new PlayerHandler();
    private EnemyHandler enemyHandler = new EnemyHandler();
    private BulletsHandler bulletsHandler = new BulletsHandler();
    private CoinHandler coinHandler = new CoinHandler();
    private Pair<EntityCoordinates, AnimationData> renderingPair = new Pair<>(null,null);
    private Pair<EntityType,Integer> entity = new Pair<>(null,null);
    private int currentTick;
    private boolean shoot = true;
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
//        if (shoot){
//            shoot();
//            shoot = false;
//        }
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
            if (GameData.getInstance().getCurrentScore() % CHANGE_DAYTIME_SCORE == 0) {
                GameView.getInstance().updateDayTime();
                GameModel.getInstance().changeDaytime();
            }
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
                    System.out.println("dead");
                }


        }
    }

    private void checkEntityCollision() {
        //todo problem when dying, dovrebbe essere fixato
        if(!playerHandler.isImmortal() && !playerHandler.isDying())
            playerHandler.checkEntityCollision(enemyHandler);
        playerHandler.checkEntityCollision(coinHandler);
        bulletsHandler.checkEntityCollision(enemyHandler);
    }

    private void checkMapCollision() {
        if(!playerHandler.isJumping() && !playerHandler.isFalling() && !playerHandler.isImmortal())
            playerHandler.rigthCollision();
        bulletsHandler.rigthCollision();
    }



    @Override
    public Pair<EntityCoordinates, AnimationData> getEntityForRendering(int entityID) {

        Pair<EntityType,Integer> type = findEntityType(entityID);
        AnimationData animationData;
        EntityCoordinates entity = GameModel.getInstance().getEntityCoordinates(type.getKey(),type.getValue(), EntityStatus.ALL);
        renderingPair.updateKey(entity);
        animationData = GameModel.getInstance().getEntityAnimation(type.getKey(),type.getValue());
            if(type.getKey() == EntityType.PLAYER){
                if(!((playerHandler.isJumping() || playerHandler.isFalling()) && animationData.getCurrentNumLoop() == 1))
                    animationData.hasToUpdate(updateAnimation);
            }else if(!playerHandler.isDying()){
//                animationData = GameModel.getInstance().getEntityAnimation(type.getKey(),type.getValue());
////                if(!playerHandler.isJumping() && !playerHandler.isFalling() && type.getKey() != EntityType.PLAYER)
                    animationData.hasToUpdate(updateAnimation);
            }
//            else
//                animationData = GameModel.getInstance().getEntityAnimation(type.getKey(),type.getValue());
            if(updateAnimation && type.getKey() == EntityType.PLAYER && playerHandler.isImmortal()) {
                animationData.switchOpacity();
                playerHandler.updateImmortalityStep();
            }
//            System.out.println("update");
        renderingPair.updateValue(animationData);
        return renderingPair;
    }



    @Override
    public int getTileData(int mapIndex, int mapX, int mapY) {
        return GameModel.getInstance().getTileData(mapIndex,mapX,mapY);
    }



    @Override
    public void setJumping(boolean isJumping) {
        if (!playerHandler.isFalling()&&!playerHandler.isJumping()&&!playerHandler.isDying()&&isJumping){
            GameModel.getInstance().setPlayerJumping(true);
        }
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
        GameView.getInstance().setupGameBar(
                GameData.getInstance().getCurrentLife(),
                GameData.getInstance().getCurrentMaxLife(),
                GameData.getInstance().getCurrentBullets()
        );
        GameView.getInstance().setupDaytime();
        GameView.getInstance().isGameRunning(true);
        GameModel.getInstance().setup();
        playerHandler.setup();
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

    @Override
    public void updateTotalCoin(int price) {
        GameData.getInstance().updateTotalCoin(price);
    }

    @Override
    public void updateCurrentLife() {
        GameData.getInstance().updateCurrentMaxLife();
    }

    @Override
    public void updateCurrentBullets() {
        GameData.getInstance().updateCurrentMaxBullets();
    }

    @Override
    public void saveDataConfig() {
        GameData.getInstance().saveData();
    }

//    @Override
//    public int getPlayerId() {
//        int id = -1;
//        if(!playerHandler.isDying())
//            id = coinHandler.getEntityNum()+enemyHandler.getEntityNum();
//        return id;
//    }

    @Override
    public void setResources() {
        GameView.getInstance().getResources();
    }

    @Override
    public void updateAnimationData(AnimationData value,int entityID) {
        Pair<EntityType,Integer> type = findEntityType(entityID);
        GameModel.getInstance().updateAnimationData(type.getKey(),type.getValue(),value);
    }
}
