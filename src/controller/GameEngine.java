package controller;

import commons.*;
import model.GameData;
import model.GameModel;
import utils.GameConfig;
import utils.SoundManager;
import view.GameView;

public class GameEngine implements IGameEngine {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static GameEngine instance = null;

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final int CHANGE_DAYTIME_SCORE = GameConfig.getInstance().getChangeDaytimeScore();
    private final int BULLET_INCREMENT_SCORE = GameConfig.getInstance().getBulletIncrementScore();
    private final int TICK_TO_UPDATE_ANIMATION = GameConfig.getInstance().getTickToUpdateAnimation();
    private final PlayerHandler PLAYER_HANDLER = new PlayerHandler();
    private final EnemiesHandler ENEMIES_HANDLER = new EnemiesHandler();
    private final BulletsHandler BULLETS_HANDLER = new BulletsHandler();
    private final CoinsHandler COINS_HANDLER = new CoinsHandler();
    private final Pair<EntityCoordinates, AnimationData> RENDERING_PAIR = new Pair<>(null,null);
    private final Pair<EntityType,Integer> ENTITY = new Pair<>(null,null);
    private final SoundManager SHOOT_SOUND = new SoundManager("res/audio/bullet.wav", SoundManager.MUSIC);

    private int currentTick;
    private boolean updateAnimation = false;

    //debug purpose
    boolean wallCollision = true;
    boolean entityCollision = true;
    boolean infiniteBullets = false;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private GameEngine(){}

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void updateGameStatus() {
        updateAnimation = false;
        updateEntity();
        if(!PLAYER_HANDLER.isDying()) {
            GameModel.getInstance().moveEntities();
            PLAYER_HANDLER.jumpAndFall();
            if (PLAYER_HANDLER.mapUpdate())
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

    @Override
    public int getTileData(int mapIndex, int mapX, int mapY) {
        return GameModel.getInstance().getTileData(mapIndex,mapX,mapY);
    }

    @Override
    public double getMapTranslX() {
        return PLAYER_HANDLER.playerTotalTranslation();
    }

    @Override
    public int getTotalEntity() {
        return PLAYER_HANDLER.getEntityCount()+ BULLETS_HANDLER.getEntityCount()+ COINS_HANDLER.getEntityCount()+ ENEMIES_HANDLER.getEntityCount();
    }

    @Override
    public Pair<EntityCoordinates, AnimationData> getEntityForRendering(int entityID) {
        Pair<EntityType,Integer> type = findEntityType(entityID);
        AnimationData animationData;
        EntityCoordinates entity = GameModel.getInstance().getEntityCoordinates(type.getKey(),type.getValue(), EntityStatus.ALL);
        RENDERING_PAIR.updateKey(entity);
        animationData = GameModel.getInstance().getEntityAnimationData(type.getKey(),type.getValue());
            if(type.getKey() == EntityType.PLAYER){
                if(!((PLAYER_HANDLER.isJumping() || PLAYER_HANDLER.isFalling()) && animationData.getCurrentAnimationStep() == AnimationData.LAST_FRAME))
                    animationData.hasToUpdate(updateAnimation);
            }else if(!PLAYER_HANDLER.isDying()){
                    animationData.hasToUpdate(updateAnimation);
            }
            if(updateAnimation && type.getKey() == EntityType.PLAYER && PLAYER_HANDLER.isImmortal()) {
                PLAYER_HANDLER.updateImmortalityStep();
                animationData.switchOpacity();
                GameModel.getInstance().updatePlayerAnimationOpacity(animationData.getOpacity());
            }
        RENDERING_PAIR.updateValue(animationData);
        return RENDERING_PAIR;
    }

    @Override
    public void updateAnimationData(AnimationData value,int entityID) {
        Pair<EntityType,Integer> type = findEntityType(entityID);
        GameModel.getInstance().updateEntityAnimationData(type.getKey(),type.getValue(),value);
    }

    @Override
    public void jump() {
        if (!PLAYER_HANDLER.isFalling()&&!PLAYER_HANDLER.isJumping()&&!PLAYER_HANDLER.isDying()){
            PLAYER_HANDLER.setJumping(true);
            GameModel.getInstance().setPlayerJumping(true);
        }
    }

    //mixed with debug purpose
    @Override
    public void shoot() {
        if(GameData.getInstance().getCurrentBullets() > 0 || infiniteBullets){
            GameModel.getInstance().shoot();
            if(!infiniteBullets)
                GameData.getInstance().updateCurrentBullets(-1);
            SHOOT_SOUND.playOnce();
        }

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
    public void setupGame() {
        GameData.getInstance().resetData();
        GameView.getInstance().setupGameBar(
                GameData.getInstance().getCurrentLife(),
                GameData.getInstance().getCurrentMaxLife(),
                GameData.getInstance().getCurrentBullets()
        );
        GameView.getInstance().setupDaytime();
        GameView.getInstance().setGameRunning(true);
        GameModel.getInstance().setup();
        PLAYER_HANDLER.setup();
    }

    @Override
    public void notifySizeChanged(int renderedTileSize) {
        EntityCoordinates.setDefaultDimension(renderedTileSize);
        CollisionHandler.setRenderedTileSize(renderedTileSize);
        PlayerHandler.setRenderedTileSize(renderedTileSize);
        GameView.getInstance().notifySizeChanged(renderedTileSize);
        GameModel.getInstance().changeEntitiesCoordinates(renderedTileSize);
    }

    //debug purpose
    @Override
    public void switchImmortality() {
        if(!wallCollision && !entityCollision){
            wallCollision = true;
            entityCollision = true;
        }else{
            wallCollision = false;
            entityCollision = false;
        }
    }

    //debug purpose
    @Override
    public void switchWallCollision() {
        wallCollision = !wallCollision;
    }

    //debug purpose
    @Override
    public void switchEntityCollision() {
        entityCollision = !entityCollision;
    }

    //debug purpose
    @Override
    public void switchInfiniteBullets() {
        infiniteBullets = !infiniteBullets;
    }

    private void updateEntity() {
        boolean isDead;
        Pair<EntityType,Integer> type;
        int totalEntity =getTotalEntity();
        for(int entityID = 0; entityID < totalEntity; entityID++){
            type=findEntityType(entityID);
            isDead = GameModel.getInstance().isEntityDead(type.getKey(),type.getValue());
            if(isDead)
                if (type.getKey() == EntityType.PLAYER) {
                    GameView.getInstance().setGameOverData(GameData.getInstance().getCurrentScore(),GameData.getInstance().getRecordScore(),GameData.getInstance().getCurrentCoin());
                    GameStateHandler.getInstance().openGameOverPanel();
                } else {
                    GameModel.getInstance().updateEntityStatus(type.getKey(), type.getValue());
                }
        }
    }

    private void checkEntityCollision() {
        if(!PLAYER_HANDLER.isImmortal() && !PLAYER_HANDLER.isDying())
            //debug purpose
            if(entityCollision)
                PLAYER_HANDLER.checkEntityCollision(ENEMIES_HANDLER);
        PLAYER_HANDLER.checkEntityCollision(COINS_HANDLER);
        BULLETS_HANDLER.checkEntityCollision(ENEMIES_HANDLER);
    }

    private void checkMapCollision() {
        if(!PLAYER_HANDLER.isJumping() && !PLAYER_HANDLER.isFalling() && !PLAYER_HANDLER.isImmortal())
            //debug purpose
            if(wallCollision)
                PLAYER_HANDLER.rightCollision();
        BULLETS_HANDLER.rightCollision();
    }

    private Pair<EntityType,Integer> findEntityType(int entityID) {
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
        ENTITY.updateKey(type);
        ENTITY.updateValue(entityID);
        return ENTITY;
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }

}
