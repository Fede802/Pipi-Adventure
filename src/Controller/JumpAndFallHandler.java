package Controller;

import Commons.EntityCoordinates;
import Utils.SoundManager;

public class JumpAndFallHandler {
    public static final int JUMP_STEP = 13;

    private static int currentJumpStep;

    private static SoundManager jumpSound = new SoundManager("Resources/Audio/Jump.wav");

    public static void jumpAndFall(boolean isJumping, EntityCoordinates playerPosition){

        if (isJumping && currentJumpStep == 0){
            jumpSound.playOnce();
        }

        if(isJumping){
            GameEngine.getInstance().jump();
            currentJumpStep++;
            if(currentJumpStep == JUMP_STEP) {
                currentJumpStep = 0;
                GameEngine.getInstance().setJumping(false);
            }
        }else if(!CollisionHandler.bottomCollision(playerPosition)){
            GameEngine.getInstance().fall();
//            System.out.println("jumpandfall");
        }


    }
}
