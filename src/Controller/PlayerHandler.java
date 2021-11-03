package controller;

import commons.EntityCoordinates;
import commons.EntityStatus;
import commons.EntityType;
import model.GameData;
import model.GameModel;
import utils.GameDataConfig;
import view.GameView;

public class PlayerHandler extends EntityHandler{
    public static final int JUMP_STEP = 13;
    private int currentJumpStep;
    private int playerStartMapX = GameDataConfig.getInstance().getPlayerStartMapX();
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
    private final int IMMORTALITY_STEP=12;
    private int currentImmortalityStep;

    public void updateImmortalityStep(){
        currentImmortalityStep++;
        if(currentImmortalityStep == IMMORTALITY_STEP){
            immortal = false;
            currentImmortalityStep = 0;
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
        System.out.println("enemy collision");
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
        System.out.println("COOOOOOLLIIIII");
        GameModel.getInstance().updateEntitiesStatus(EntityType.COIN,outerCurrentEntity);
        GameData.getInstance().updateCurrentCoin();

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
        isJumping = GameModel.getInstance().isPlayerJumping();
//        System.out.println(bottomCollision());
        if(isJumping){
            GameModel.getInstance().playerJump();
//            System.out.println(getNext().getTranslY() + "jump"+getNext().getMapY());
            currentJumpStep++;
            if(currentJumpStep == JUMP_STEP) {
                currentJumpStep = 0;
                isFalling = true;
                GameModel.getInstance().setPlayerJumping(false);
            }
        }else if(!bottomCollision()){

            GameModel.getInstance().playerFall();
//            System.out.println(getNext().getTranslY() + "fall" +getNext().getMapY());
        }else if(isFalling && bottomCollision()){
            isFalling = false;
        }

    }

    public double playerTotalTranslation(){
        EntityCoordinates player = getEntity();
        return (player.getMapX()+player.getMapIndex()*GameDataConfig.getInstance().getMapSectionSize()-playerStartMapX) * GameDataConfig.getInstance().getRenderedTileSize() + player.getTranslX();
    }

    public void setImmortal(boolean b) {
        immortal = b;
    }
}
