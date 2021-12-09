package controller;

import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;
import utils.SoundManager;
import view.GameView;

public class PlayerHandler extends EntitiesHandler {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static int renderedTileSize;
    private static final SoundManager COIN = new SoundManager("res/audio/coinpickup.wav", SoundManager.MUSIC);
    private static final SoundManager JUMP = new SoundManager("res/audio/Jump.wav", SoundManager.MUSIC);

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final int JUMP_STEP = 13;
    private final int IMMORTALITY_STEP=13;
    private final int PLAYER_START_MAP_X = GameDataConfig.getInstance().getPlayerStartMapX();

    private boolean isJumping = false;
    private boolean isFalling = false;
    private boolean immortal = false;
    private boolean dying = false;
    private int currentJumpStep;
    private int currentImmortalityStep;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public PlayerHandler() {
        super(EntityType.PLAYER);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        //used only with true value
        isJumping = jumping;
        if(isJumping)
            JUMP.playOnce();
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public void jumpAndFall() {
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

    public void updateImmortalityStep(){
        if(currentImmortalityStep == IMMORTALITY_STEP){
            immortal = false;
            currentImmortalityStep = 0;
        }else{
            currentImmortalityStep++;
        }

    }

    public double playerTotalTranslation(){
        EntityCoordinates player = getEntity();
        return (player.getMapX()+player.getMapIndex()*GameDataConfig.getInstance().getMapSectionSize()- PLAYER_START_MAP_X) * renderedTileSize + player.getTraslX();
    }

    public boolean mapUpdate() {
        boolean mapUpdate = false;
        EntityCoordinates current = getEntity();
        if(current.getMapX() == PLAYER_START_MAP_X && current.getTraslX() == 0){
            current.setMapIndex(0);
            mapUpdate = true;
        }
        return mapUpdate;
    }

    public EntityCoordinates getEntity() {
        return super.getEntity(0, EntityStatus.ALL);
    }

    public void setup() {
        this.currentJumpStep = 0;
        this.currentImmortalityStep = 0;
        this.isJumping = false;
        this.isFalling = false;
        this.dying = false;
        this.immortal = false;
    }

    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {
        if(GameData.getInstance().getCurrentLife() == 0) {
            GameModel.getInstance().updateEntityStatus(EntityType.PLAYER, currentEntity);
            GameView.getInstance().setGameRunning(false);
            dying = true;
        }else{
            GameData.getInstance().updateCurrentLife();

            immortal = true;
        }
    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {
        GameModel.getInstance().updateEntityStatus(EntityType.COIN,outerCurrentEntity);
        GameData.getInstance().updateCurrentCoin();
        COIN.playOnce();
    }

    @Override
    protected void wallCollision(int currentEntity) {
        if(GameData.getInstance().getCurrentLife() == 0) {
            GameModel.getInstance().updateEntityStatus(EntityType.PLAYER, currentEntity);
            GameView.getInstance().setGameRunning(false);
            dying = true;
        }else{
            GameData.getInstance().updateCurrentLife();
            immortal = true;
        }
    }

    private boolean bottomCollision(){
        return CollisionHandler.bottomCollision(getEntity());
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static void setRenderedTileSize(int renderedTileSize) {
        PlayerHandler.renderedTileSize = renderedTileSize;
    }

}
