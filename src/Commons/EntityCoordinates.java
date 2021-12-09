package commons;

public class EntityCoordinates {

    //    --------------------------------------------------------
    //                       STATIC FIELDS
    //    --------------------------------------------------------

    //variable uses to fix approximation error due to double sum
    private static final int SCALE = (int) Math.pow(10, 4);
    private static int defaultDimension;

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private int mapX;
    private int mapY;

    private int mapIndex;

    private double width;
    private double height;

    private double traslX;
    private double traslY;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private EntityCoordinates(final int mapIndex, final int mapX, final int mapY) {
        this.mapIndex = mapIndex;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public int getMapY() {
        return mapY;
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

    public void updateTraslX(double traslXVariation) {
        this.traslX = (double) Math.round((this.traslX +traslXVariation) * SCALE) / SCALE;
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
        this.traslY = (double) Math.round((this.traslY +traslYVariation) * SCALE) / SCALE;
    }

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static void setDefaultDimension(int defaultDimension) {
        EntityCoordinates.defaultDimension = defaultDimension;
    }

    //    --------------------------------------------------------
    //                      BUILDER CLASS
    //    --------------------------------------------------------

    public static class Builder {

        private final int START_MAP_X;
        private final int START_MAP_Y;

        private int mapIndex;

        private double width;
        private double height;

        private double traslX;
        private double traslY;

        public Builder(int startMapX, int startMapY) {
            this.START_MAP_X = startMapX;
            this.START_MAP_Y = startMapY;
            this.mapIndex = 0;
            this.width = defaultDimension;
            this.height = defaultDimension;
            this.traslX = 0.0;
            this.traslY = 0.0;
        }

        public EntityCoordinates build() {
            EntityCoordinates entityCoordinates = new EntityCoordinates(mapIndex,START_MAP_X,START_MAP_Y);
            entityCoordinates.width = width;
            entityCoordinates.height = height;
            entityCoordinates.traslX = traslX;
            entityCoordinates.traslY = traslY;
            return entityCoordinates;
        }

        public Builder setStartMapIndex(int startMapIndex) {
            this.mapIndex = startMapIndex;
            return this;
        }

        public Builder setTraslX(double traslX) {
            this.traslX = traslX;
            return this;
        }

        public Builder setTraslY(double traslY) {
            this.traslY = traslY;
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
