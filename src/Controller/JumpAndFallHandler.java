package Controller;

public class JumpAndFallHandler {
    public static final int JUMP_STEP = 13;

    private static int currentJumpStep;

    public static void jumpAndFall(boolean isJumping, int[] playerPosition){

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
