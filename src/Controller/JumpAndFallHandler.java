package Controller;


import Model.GameModel;

public class JumpAndFallHandler {

    public static final int JUMP_STEP = 13;
    private int currentJumpStep;
    private boolean isJumping = false;
    public JumpAndFallHandler(){}

    public void jumpAndFall(){
        isJumping = GameModel.getInstance().isPlayerJumping();
        if(isJumping){
            GameModel.getInstance().playerJump();
            currentJumpStep++;
            if(currentJumpStep == JUMP_STEP) {
                currentJumpStep = 0;
                GameModel.getInstance().setPlayerJumping(false);
            }
        }else if(!CollisionHandler.playerBottomCollision()){
            GameModel.getInstance().playerFall();
        }
    }

}
