package Model;

public class Player extends GameEntity{


    private final static int MAX_Y = 50;
    private final static int VEL_Y = 8;
    private int traslY;
    private int prevMapJ = mapJ;
    private int currentJumpStep;
    private final static int JUMP_STEP = 18;
    private boolean isJumping = false;

    public Player(int TRASL_X, String id, int mapJ, int mapI, MapGenerator generator) {
        super(TRASL_X, id, mapJ, mapI, generator);
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
        if(isJumping) {
            if (currentJumpStep < JUMP_STEP / 2) {
                traslY += VEL_Y;
                if(traslY > 40) {
                    prevMapJ = mapJ;
                    mapJ++;
                    traslY-=40;
                }

                System.out.println("MAPJ: "+mapJ);
            } else {
                traslY -= VEL_Y;

                if(traslY < 40) {
//                    prevMapJ = mapJ;
                    mapJ--;
                    traslY+=40;
                }
            }
            currentJumpStep++;
            checkCollision();
            System.out.println("coll");
            if (currentJumpStep == JUMP_STEP) {
                currentJumpStep = 0;
                isJumping = false;
            }
        }
    }

    public int getTraslY() {
        return traslY;
    }

    public void setTraslY(int traslY) {
        this.traslY = traslY;
    }

    @Override
    public void checkCollision() {
        if (generator.getTileData(0,mapI,mapJ-1) != 34 && prevMapJ!= mapJ){
            prevMapJ = mapJ;
            traslY = 0;
            isJumping = false;
        }
    }
}
