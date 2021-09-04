package Commons;

import Model.MapSection;
import Utils.GameConfig;

public class EntityCoordinates {

    private static final int scale = (int) Math.pow(10, 1);

    private final int START_MAP_X;
    private final int START_MAP_Y;
    private final int ID;
    private int mapIndex;

    private double width;
    private double traslX;

    private double height;
    private double traslY;

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

    public double getTraslX() {
        return traslX;
    }

    public void setTraslX(double traslX) {
        this.traslX = traslX;
    }
    public void updateTraslX(double traslXVariation){
        this.traslX= (double) Math.round((this.traslX+traslXVariation) * scale) / scale;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getTraslY() {
        return traslY;
    }

    public void setTraslY(double traslY) {
        this.traslY = traslY;
    }
    public void updateTraslY(double traslYVariation){
        this.traslY= (double) Math.round((this.traslY+traslYVariation) * scale) / scale;
    }

    public boolean collide(EntityCoordinates e){
        //todo shifta le x di mapIndex*size

        int tileSize = GameConfig.getInstance().getRenderedTileSize();
        int size = tileSize* MapSection.SECTION_SIZE;
        return e.START_MAP_X*tileSize+e.traslX+e.mapIndex*size < START_MAP_X*tileSize+traslX+mapIndex*size+width &&
                e.START_MAP_X*tileSize+e.traslX+e.mapIndex*size+e.width > START_MAP_X*tileSize+traslX+mapIndex*size
        &&
                e.START_MAP_Y*tileSize+e.traslY <= START_MAP_Y*tileSize+traslY+height &&
                e.START_MAP_Y*tileSize+e.traslY + e.height>= START_MAP_Y*tileSize+traslY;

    }

    public static class Builder{
        private final int START_MAP_X;
        private final int START_MAP_Y;
        private final int ID;

        private int START_MAP_INDEX;

        private double width;
        private double traslX;

        private double height;
        private double traslY;

        public Builder(final int START_MAP_X, final int START_MAP_Y, final int ID){
            this.START_MAP_INDEX = 0;
            this.START_MAP_X = START_MAP_X;
            this.START_MAP_Y = START_MAP_Y;
            this.ID = ID;
            this.width = GameConfig.getInstance().getRenderedTileSize();
            this.traslX = 0.0;
            this.height = GameConfig.getInstance().getRenderedTileSize();
            this.traslY = 0.0;
        }

        public EntityCoordinates build(){
            EntityCoordinates entityCoordinates = new EntityCoordinates(START_MAP_INDEX,START_MAP_X,START_MAP_Y,ID);
            entityCoordinates.width = width;
            entityCoordinates.traslX = traslX;
            entityCoordinates.height = height;
            entityCoordinates.traslY = traslY;
            return entityCoordinates;
        }

        public Builder setStartMapIndex(int startMapIndex) {
            this.START_MAP_INDEX = startMapIndex;
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
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }
        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }
    }
}

