package controller;

import commons.EntityCoordinates;
import commons.EntityType;
import model.GameModel;
import utils.GameDataConfig;

public class PlayerHandler extends EntityHandler{
    public static final int JUMP_STEP = 13;
    private int currentJumpStep;
    private int playerStartMapX = GameDataConfig.getInstance().getPlayerStartMapX();
    private boolean isJumping = false;
    public PlayerHandler() {
        super(EntityType.PLAYER);
    }

    public boolean mapUpdate() {
        boolean mapUpdate = false;
        EntityCoordinates current = getEntity(getCurrentEntity());

        if(current.getMapX() == playerStartMapX && current.getTranslX() == 0){
            current.setMapIndex(0);
            mapUpdate = true;
        }
        return mapUpdate;
    }

    @Override
    protected void collideWithEnemy(int currentEntity, int outerCurrentEntity) {

    }

    @Override
    protected void collideWithCoin(int currentEntity, int outerCurrentEntity) {
        GameModel.getInstance().updateEntitiesStatus(EntityType.COIN,outerCurrentEntity);
    }

    public boolean bottomCollision(){
        return CollisionHandler.bottomCollision(getNext());
    }

    public void jumpAndFall(){
        isJumping = GameModel.getInstance().isPlayerJumping();
        System.out.println(bottomCollision());
        if(isJumping){
            GameModel.getInstance().playerJump();
//            System.out.println(getNext().getTranslY() + "jump"+getNext().getMapY());
            currentJumpStep++;
            if(currentJumpStep == JUMP_STEP) {
                currentJumpStep = 0;
                GameModel.getInstance().setPlayerJumping(false);
            }
        }else if(!bottomCollision()){

            GameModel.getInstance().playerFall();
//            System.out.println(getNext().getTranslY() + "fall" +getNext().getMapY());
        }

    }

    public double playerTotalTranslation(){
        EntityCoordinates player = getEntity(getCurrentEntity());
        return (player.getMapX()+player.getMapIndex()*GameDataConfig.getInstance().getMapSectionSize()-playerStartMapX) * GameDataConfig.getInstance().getRenderedTileSize() + player.getTranslX();
    }
}
