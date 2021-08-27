package Model;

public class Player extends GameEntity{


    public static final int PLAYER_VEL_Y = 5;

    private boolean isJumping = false;

    public Player(String id, int mapSection, int startMapX, int startMapY) {
        super(id, mapSection, startMapX, startMapY);
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    @Override
    public void move() {
        //TODO later start step?
    }
    //TODO later, find a way to get rendered tile size
    public void jump(){

        traslY-=PLAYER_VEL_Y;
        if(traslY == -40){
            mapY--;
            traslY = 0;
        }
//        if(traslY == 0){
//            traslY=0;
//        }
    }
    public void fall(){
        traslY+=PLAYER_VEL_Y;

        if(traslY == PLAYER_VEL_Y){
            mapY++;
            traslY = -35;
        }
//        System.out.println(traslY);

    }
    public int[] getPlayerInfo(){
        return new int[]{mapIndex,mapX,traslX,mapY,traslY};
    }
    public void setPlayerInfo(final int mapIndex, final int mapX, final int traslX, final int mapY, final int traslY){
        //TODO rivedi
        setMapIndex(mapIndex);
        setMapX(mapX);
        setTraslX(traslX);
        setMapY(mapY);
        setTraslY(traslY);
    }
    @Override
    public void setTraslX(int traslX){
        //TODO later, find a way to get rendered tile size

        this.traslX = traslX;
        if(this.traslX >= 40){
            this.traslX -= 40;
            if(mapX == MapSection.SECTION_SIZE-1){
                mapIndex++;
                mapX = 0;
            }else{
                mapX++;
            }
            if(mapX == 2 && this.traslX == 0){
                mapIndex--;
            }
        }
//        System.out.println("TraslX: "+traslX+"\nMapX: "+mapX+"\nMapIndex: "+mapIndex);

    }
}

