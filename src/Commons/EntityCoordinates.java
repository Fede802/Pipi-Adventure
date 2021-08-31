package Commons;

import Utils.Config;

public class EntityCoordinates {
    //TODO maybe id not needed
    private final int START_MAP_X;
    private final int START_MAP_Y;
    private final int ID;
    private int mapIndex;

    private int mapX;
    private int traslX;

    private int mapY;
    private int traslY;

    private EntityCoordinates(final int START_MAP_X, final int START_MAP_Y, final int ID){
        this.ID = ID;
        this.START_MAP_X = START_MAP_X;
        this.START_MAP_Y = START_MAP_Y;
    }

    public int getID() {
        return ID;
    }

    public int getSTART_MAP_X() {
        return START_MAP_X;
    }

    public int getSTART_MAP_Y() {
        return START_MAP_Y;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getTraslX() {
        return traslX;
    }

    public void setTraslX(int traslX) {
        this.traslX = traslX;
    }

    public void updateTraslX(int traslXVariation){
        this.traslX+=traslXVariation;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public int getTraslY() {
        return traslY;
    }

    public void setTraslY(int traslY) {
        this.traslY = traslY;
    }
    public boolean intersects(EntityCoordinates r) {
        int size = Config.getInstance().getRenderedTileSize();
        int x1 = this.mapX*size+this.traslX;//+this.mapIndex*16*size;
        int y1 = this.mapY*size+this.traslY;
        int x2 = r.mapX*size+r.traslX;//+r.mapIndex*16*size;
        int y2 = r.mapY*size+r.traslY;
        return x2 < x1 + size && x2 + size > x1 && y2 < y1 + size && y2 + size > y1;
    }

    public static class Builder{
        private final int START_MAP_X;
        private final int START_MAP_Y;
        private final int ID;
        private int mapIndex;

        private int mapX;
        private int traslX;

        private int mapY;
        private int traslY;

        public Builder(final int START_MAP_X, final int START_MAP_Y, final int ID){
            this.ID = ID;
            this.START_MAP_X = START_MAP_X;
            this.START_MAP_Y = START_MAP_Y;
            this.mapIndex = 0;
            this.mapX = START_MAP_X;
            this.traslX = 0;
            this.mapY = START_MAP_Y;
            this.traslY = 0;
        }

        public EntityCoordinates build(){
            EntityCoordinates entityCoordinates = new EntityCoordinates(START_MAP_X,START_MAP_Y,ID);
            entityCoordinates.mapIndex = mapIndex;
            entityCoordinates.mapX = mapX;
            entityCoordinates.traslX = traslX;
            entityCoordinates.mapY = mapY;
            entityCoordinates.traslY = traslY;
            return entityCoordinates;
        }

        public Builder setMapIndex(int mapIndex) {
            this.mapIndex = mapIndex;
            return this;
        }

        public Builder setTraslX(int traslX) {
            this.traslX = traslX;
            return this;
        }
        public Builder setTraslY(int traslY) {
            this.traslY = traslY;
            return this;
        }

        public Builder setMapX(int mapX) {
            this.mapX = mapX;
            return this;
        }
    }
}
