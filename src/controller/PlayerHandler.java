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

    private static final SoundManager COIN = new SoundManager("res/audio/coinpickup.wav", SoundManager.MUSIC);
    private static final SoundManager JUMP = new SoundManager("res/audio/Jump.wav", SoundManager.MUSIC);
    private static int renderingTileSize;

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final int JUMP_STEP = 13;
    private final int IMMORTALITY_STEP=13;
    private final int PLAYER_START_MAP_X = GameDataConfig.getInstance().getPlayerStartMapX();

    private boolean jumping;
    private boolean falling;
    private boolean immortal;
    private boolean dying;
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
        return jumping;
    }

    public void setJumping(boolean jumping) {
        //used only with true value
        this.jumping = jumping;
        if(this.jumping)
            JUMP.playOnce();
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
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
        if(jumping){
            GameModel.getInstance().playerJump();
            currentJumpStep++;
            if(currentJumpStep == JUMP_STEP) {
                currentJumpStep = 0;
                jumping = false;
                falling = true;
            }
        }else if(!bottomCollision()){
            if(!falling){
                falling = true;
                GameModel.getInstance().setPlayerFalling(true);
            }
            GameModel.getInstance().playerFall();
        }else if(falling && bottomCollision()){
            falling = false;
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
        return (player.getMapX()+player.getMapIndex()*GameDataConfig.getInstance().getMapSectionSize()- PLAYER_START_MAP_X) * renderingTileSize + player.getTranslX();
    }

    public boolean mapUpdate() {
        boolean mapUpdate = false;
        EntityCoordinates current = getEntity();
        if(current.getMapX() == PLAYER_START_MAP_X && current.getTranslX() == 0){
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
        this.jumping = false;
        this.falling = false;
        this.dying = false;
        this.immortal = false;
    }

    @Override
    protected void collideWithEnemy(int entity, int externalEntity) {
        if(GameData.getInstance().getCurrentLives() == 0) {
            GameModel.getInstance().updateEntityStatus(EntityType.PLAYER, entity);
            GameView.getInstance().setGameRunning(false);
            dying = true;
        }else{
            GameData.getInstance().updateCurrentLives();
            immortal = true;
        }
    }

    @Override
    protected void collideWithCoin(int entity, int externalEntity) {
        GameModel.getInstance().updateEntityStatus(EntityType.COIN, externalEntity);
        GameData.getInstance().updateCurrentCoins();
        COIN.playOnce();
    }

    @Override
    protected void wallCollision(int entity) {
        if(GameData.getInstance().getCurrentLives() == 0) {
            GameModel.getInstance().updateEntityStatus(EntityType.PLAYER, entity);
            GameView.getInstance().setGameRunning(false);
            dying = true;
        }else{
            GameData.getInstance().updateCurrentLives();
            immortal = true;
        }
    }

    private boolean bottomCollision(){
        return super.bottomCollision(0);
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static void setRenderingTileSize(int renderingTileSize) {
        PlayerHandler.renderingTileSize = renderingTileSize;
    }

}
