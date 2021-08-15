package Model;

public abstract class GameEntity {
    protected static final int VEL_X = 10;
    protected int traslX;
    protected int mapIndex;
    protected int mapJ;
    protected int mapI;
    protected boolean isAlive = true;
    protected String id;

    public GameEntity(int TRASL_X, String id,int mapJ,int mapI){
        this.traslX = traslX;
        this.id = id;
        this.mapJ = mapJ;
        this.mapI = mapI;
    }

    public abstract void moveRight();
    public boolean isAlive(){
        return isAlive;
    }
    public void checkCollision(){

    }

    public int getMapJ() {
        return mapJ;
    }

    public void setMapJ(int mapJ) {
        this.mapJ = mapJ;
    }

    public int getMapI() {
        return mapI;
    }

    public void setMapI(int mapI) {
        this.mapI = mapI;
    }
}
