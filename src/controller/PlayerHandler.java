package controller;

import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;
import utils.SoundManager;
import view.GameView;

public class PlayerHandler extends EntityHandler{
    public static final int JUMP_STEP = 13;
    private int currentJumpStep;

    public static void setRenderedTileSize(int renderedTileSize) {
        PlayerHandler.renderedTileSize = renderedTileSize;
    }

    private static int renderedTileSize;
    private int playerStartMapX = GameDataConfig.getInstance().getPlayerStartMapX();

    public void setJumping(boolean jumping) {
        isJumping = jumping;
        if(isJumping)
            JUMP.playOnce();
    }

    private boolean isJumping = false;

    public boolean isJumping() {
        return isJumping;
    }

    public boolean isFalling() {
        return isFalling;
    }

    private boolean isFalling = false;
    private boolean immortal = false;

    public boolean isDying() {
        return dying;
    }


    public void setDying(boolean dying) {
        this.dying = dying;
    }

    private boolean dying = false;
    private final int IMMORTALITY_STEP=13;

    private final SoundManager COIN = new SoundManager("res/audio/coinpickup.wav", SoundManager.MUSIC);
    private final SoundManager JUMP = new SoundManager("res/audio/Jump.wav", SoundManager.MUSIC);

    public void setup() {
        this.currentJumpStep = 0;
        this.currentImmortalityStep = 0;
        this.isJumping = false;
        this.isFalling = false;
        this.dying = false;
        this.immortal = false;
    }

    private int currentImmortalityStep;

    public void updateImmortalityStep(){
        if(currentImmortalityStep == IMMORTALITY_STEP){
            immortal = false;
            currentImmortalityStep = 0;
        }else{
            currentImmortalityStep++;
        }

    }
    public boolean isImmortal() {
        return immortal;
    }

    public PlayerHandler() {
        super(EntityType.PLAYER);
    }

    public EntityCoordinates getEntity() {
        return super.getEntity(0, EntityStatus.ALL);
    }

    public boolean mapUpdate() {
        boolean mapUpdate = false;
        EntityCoordinates current = getEntity();
        if(current.getMapX() == playerStartMapX && current.getTranslX() == 0){
            current.setMapIndex(0);
            mapUpdate = true;
        }
        return mapUpdate;
    }

    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {
        if(GameData.getInstance().getCurrentLife() == 0) {
            GameModel.getInstance().updateEntitiesStatus(EntityType.PLAYER, currentEntity);
            GameView.getInstance().isGameRunning(false);
            dying = true;
        }else{
            GameData.getInstance().updateCurrentLife();

            immortal = true;
        }
    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {
        GameModel.getInstance().updateEntitiesStatus(EntityType.COIN,outerCurrentEntity);
        GameData.getInstance().updateCurrentCoin();
        COIN.playOnce();
    }

    @Override
    protected void wallCollision(int currentEntity) {
        if(GameData.getInstance().getCurrentLife() == 0) {
            GameModel.getInstance().updateEntitiesStatus(EntityType.PLAYER, currentEntity);
            GameView.getInstance().isGameRunning(false);
            dying = true;
        }else{
            GameData.getInstance().updateCurrentLife();
            immortal = true;
        }
    }

    public boolean bottomCollision(){
        return CollisionHandler.bottomCollision(getEntity());
    }

    public void jumpAndFall(){


        if(isJumping){
            GameModel.getInstance().playerJump();
            currentJumpStep++;
            if(currentJumpStep == JUMP_STEP) {
                currentJumpStep = 0;
                isJumping = false;
                isFalling = true;
            }
        }else if(!bottomCollision()){
            if(!isFalling){
                isFalling = true;
                GameModel.getInstance().setPlayerFalling(true);
            }
            GameModel.getInstance().playerFall();
        }else if(isFalling && bottomCollision()){
            isFalling = false;
            GameModel.getInstance().setPlayerFalling(false);
        }

    }

    public double playerTotalTranslation(){
        EntityCoordinates player = getEntity();
        return (player.getMapX()+player.getMapIndex()*GameDataConfig.getInstance().getMapSectionSize()-playerStartMapX) * renderedTileSize + player.getTranslX();
    }

    public void setImmortal(boolean b) {
        immortal = b;
    }

}
