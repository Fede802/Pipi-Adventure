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

    private double translX;
    private double translY;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private EntityCoordinates(int mapIndex,int mapX, int mapY) {
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

    public double getTranslX() {
        return translX;
    }

    public void setTranslX(double translX) {
        this.translX = translX;
    }

    public void updateTranslX(double translXVariation) {
        this.translX = (double) Math.round((this.translX +translXVariation) * SCALE) / SCALE;
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

    public void updateTranslY(double translYVariation){
        this.translY = (double) Math.round((this.translY +translYVariation) * SCALE) / SCALE;
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

        private double translX;
        private double translY;

        public Builder(int startMapX, int startMapY) {
            this.START_MAP_X = startMapX;
            this.START_MAP_Y = startMapY;
            this.mapIndex = 0;
            this.width = defaultDimension;
            this.height = defaultDimension;
            this.translX = 0.0;
            this.translY = 0.0;
        }

        public EntityCoordinates build() {
            EntityCoordinates entityCoordinates = new EntityCoordinates(mapIndex,START_MAP_X,START_MAP_Y);
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

        public Builder setTranslX(double translX) {
            this.translX = translX;
            return this;
        }

        public Builder setTranslY(double translY) {
            this.translY = translY;
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
