package commons;

import utils.GameConfig;
import utils.GameDataConfig;

public class EntityCoordinates {

    private static final int scale = (int) Math.pow(10, 4);

    public static final int PLAYER_ID = 0;
    public static final int ENEMY_ID = 1;
    public static final int COIN_ID = 2;
    public static final int BULLET_ID = 3;

    private final int START_MAP_X;
    private final int START_MAP_Y;
    private final int ID;
    private int mapIndex;

    private double width;
    private double height;

    private double translX;
    private double translY;

    private EntityCoordinates(final int mapIndex, final int START_MAP_X, final int START_MAP_Y, final int ID){
        this.mapIndex = mapIndex;
        this.START_MAP_X = START_MAP_X;
        this.START_MAP_Y = START_MAP_Y;
        this.ID = ID;
    }

    public int getSTART_MAP_X() {
        return START_MAP_X;
    }

    public int getSTART_MAP_Y() {
        return START_MAP_Y;
    }

    public int getID() {
        return ID;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public void setMapIndex(final int mapIndex){
        this.mapIndex = mapIndex;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getTranslX() {
        return translX;
    }

    public void setTranslX(double translX) {
        this.translX = translX;
    }

    public void updateTraslX(double traslXVariation){
        this.translX = (double) Math.round((this.translX +traslXVariation) * scale) / scale;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getTranslY() {
        return translY;
    }

    public void setTranslY(double translY) {
        this.translY = translY;
    }

    public void updateTraslY(double traslYVariation){
        this.translY = (double) Math.round((this.translY +traslYVariation) * scale) / scale;
    }

    public boolean collide(EntityCoordinates e){

        //TODO update tileSize and size when change coordinates

        int tileSize = GameDataConfig.getInstance().getRenderedTileSize();
        int size = tileSize* GameDataConfig.getInstance().getMapSectionSize();
        return e.START_MAP_X*tileSize+e.translX +e.mapIndex*size < START_MAP_X*tileSize+ translX +mapIndex*size+width &&
                e.START_MAP_X*tileSize+e.translX +e.mapIndex*size+e.width > START_MAP_X*tileSize+ translX +mapIndex*size &&
                e.START_MAP_Y*tileSize+e.translY < START_MAP_Y*tileSize+ translY +height &&
                e.START_MAP_Y*tileSize+e.translY + e.height> START_MAP_Y*tileSize+ translY;

    }

    public static class Builder{
        private final int START_MAP_X;
        private final int START_MAP_Y;
        private final int ID;

        private int mapIndex;

        private double width;
        private double height;

        private double translX;
        private double translY;

        public Builder(final int START_MAP_X, final int START_MAP_Y, final int ID){
            this.START_MAP_X = START_MAP_X;
            this.START_MAP_Y = START_MAP_Y;
            this.ID = ID;
            this.mapIndex = 0;
            this.width = GameDataConfig.getInstance().getRenderedTileSize();
            this.height = GameDataConfig.getInstance().getRenderedTileSize();
            this.translX = 0.0;
            this.translY = 0.0;
        }

        public EntityCoordinates build(){
            EntityCoordinates entityCoordinates = new EntityCoordinates(mapIndex,START_MAP_X,START_MAP_Y,ID);
            entityCoordinates.width = width;
            entityCoordinates.height = height;
            entityCoordinates.translX = translX;
            entityCoordinates.translY = translY;
            return entityCoordinates;
        }

        public Builder setStartMapIndex(int startMapIndex) {
            this.mapIndex = startMapIndex;
            return this;
        }

        public Builder setTraslX(double traslX) {
            this.translX = traslX;
            return this;
        }

        public Builder setTraslY(double traslY) {
            this.translY = traslY;
            return this;
        }

        public Builder setWidth(double width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(double height) {
            this.height = height;
            return this;
        }
    }
}
