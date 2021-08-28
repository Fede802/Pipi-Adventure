package Commons;

public class EntityCoordinates {
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
    }
}
