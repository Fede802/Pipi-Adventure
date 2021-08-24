package Model;

public abstract class GameEntity {

    protected final String ID;
    protected final int START_MAP_X;
    protected final int START_MAP_Y;

    protected int mapX;
    protected int mapY;
    protected int mapIndex;
    protected boolean isAlive = true;
    protected int traslX;
    protected int traslY;
    //TODO later maybe isAlive not needed for coin?
    //TODO later maybe mapGenerator attribute needed?
    public GameEntity(final String id, final int mapIndex, final int startMapX, final int startMapY){
        this.ID = id;
        this.mapIndex = mapIndex;
        this.START_MAP_X = this.mapX = startMapX;
        this.START_MAP_Y = this.mapY = startMapY;
    }
    //TODO later everywhere, setters args needs final?
    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getTraslX() {
        return traslX;
    }

    public void setTraslX(int traslX) {
        this.traslX = traslX;
    }

    public int getTraslY() {
        return traslY;
    }

    public void setTraslY(int traslY) {
        this.traslY = traslY;
    }
    public abstract void move();
    //TODO later also jump there?
    //TODO later deathMove method?
}
