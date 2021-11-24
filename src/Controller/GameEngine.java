package controller;

import commons.*;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;
import view.GameView;

public class GameEngine implements IGameEngine{
    //todo config
    private  final int CHANGE_DAYTIME_SCORE = 1000;
    private final int BULLET_INCREMENT_SCORE = 100;
    private final int TICK_TO_UPDATE_ANIMATION = 3;
    private final PlayerHandler playerHandler = new PlayerHandler();
    private final EnemyHandler enemyHandler = new EnemyHandler();
    private final BulletsHandler bulletsHandler = new BulletsHandler();
    private final CoinHandler coinHandler = new CoinHandler();
    private final Pair<EntityCoordinates, AnimationData> renderingPair = new Pair<>(null,null);
    private final Pair<EntityType,Integer> entity = new Pair<>(null,null);
    private int currentTick;
    private boolean updateAnimation = false;
    //todo will use?
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
        updateEntity();
        if(!playerHandler.isDying()) {
            GameModel.getInstance().moveEntities();
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
                    GameView.getInstance().setGameOverData(GameData.getInstance().getCurrentScore(),GameData.getInstance().getRecordScore(),GameData.getInstance().getCurrentCoin());
                    GameStateHandler.getInstance().gameOver();
                } else {
                    GameModel.getInstance().updateEntitiesStatus(type.getKey(), type.getValue());
                }


        }
    }

    private void checkEntityCollision() {
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
        animationData = GameModel.getInstance().getEntityAnimationData(type.getKey(),type.getValue());
            if(type.getKey() == EntityType.PLAYER){
                if(!((playerHandler.isJumping() || playerHandler.isFalling()) && animationData.getCurrentAnimationStep() == AnimationData.LAST_FRAME))
                    animationData.hasToUpdate(updateAnimation);
            }else if(!playerHandler.isDying()){
                    animationData.hasToUpdate(updateAnimation);
            }
            if(updateAnimation && type.getKey() == EntityType.PLAYER && playerHandler.isImmortal()) {
                animationData.switchOpacity();
                playerHandler.updateImmortalityStep();
            }
        renderingPair.updateValue(animationData);
        return renderingPair;
    }



    @Override
    public int getTileData(int mapIndex, int mapX, int mapY) {
        return GameModel.getInstance().getTileData(mapIndex,mapX,mapY);
    }



    @Override
    public void jump() {
        if (!playerHandler.isFalling()&&!playerHandler.isJumping()&&!playerHandler.isDying()){
            playerHandler.setJumping(true);
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
