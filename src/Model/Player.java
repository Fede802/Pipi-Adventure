package Model;

public class Player extends GameEntity{

    private final static int MAX_Y = 50;
    private final static int VEL_Y = 10;
    private int traslY;
    private int currentJumpStep;
    private final static int JUMP_STEP = 10;
    private boolean isJumping = false;

    public Player(int TRASL_X, String id, int mapJ, int mapI) {
        super(TRASL_X, id, mapJ, mapI);
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    @Override
    public void moveRight() {

    }

    public void jump(){
        if(isJumping)
        if(currentJumpStep < JUMP_STEP/2){
            traslY-=VEL_Y;
        }else{
            traslY+=VEL_Y;
        }
        currentJumpStep++;
        if (currentJumpStep == JUMP_STEP){
            currentJumpStep = 0;
        }
    }

}
