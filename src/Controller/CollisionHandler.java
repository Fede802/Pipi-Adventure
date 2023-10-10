package controller;

import commons.EntityCoordinates;
import model.GameModel;
import utils.GameDataConfig;

public class CollisionHandler {

    //    --------------------------------------------------------
    //                       STATIC FIELDS
    //    --------------------------------------------------------

    private static final int EMPTY_TILE_CODE = GameDataConfig.getInstance().getEmptyTileCode();
    private static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    private static int renderingTileSize;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private CollisionHandler(){}

    //    --------------------------------------------------------
    //                      STATIC METHODS
    //    --------------------------------------------------------

    public static void setRenderingTileSize(int renderingTileSize) {
        CollisionHandler.renderingTileSize = renderingTileSize;
    }

    public static boolean collide(EntityCoordinates entity1, EntityCoordinates entity2) {
        double entity1X = (entity1.getMapIndex()* SECTION_SIZE +entity1.getMapX())* renderingTileSize +entity1.getTranslX();
        double entity2X = (entity2.getMapIndex()* SECTION_SIZE +entity2.getMapX())* renderingTileSize +entity2.getTranslX();
        double entity1Y = entity1.getMapY()* renderingTileSize +entity1.getTranslY();
        double entity2Y = entity2.getMapY()* renderingTileSize +entity2.getTranslY();
        return entity1X < entity2X+entity2.getWidth() && entity1X +entity1.getWidth() > entity2X &&
                entity1Y < entity2Y + entity2.getHeight() && entity1Y + entity1.getHeight() > entity2Y;
    }

    public static boolean bottomCollision(EntityCoordinates entity) {
        boolean isColliding = false;
        int frontMapX = entity.getMapX()+1;
        int frontMapIndex = entity.getMapIndex();
        if(frontMapX == GameDataConfig.getInstance().getMapSectionSize()){
            frontMapX = 0;
            frontMapIndex++;
        }
        if(entity.getTranslY() == 0){
            if(GameModel.getInstance().getTileData(entity.getMapIndex(), entity.getMapX(), entity.getMapY()+1) != EMPTY_TILE_CODE)
                isColliding = true;
            if(entity.getTranslX() != 0)
                if(GameModel.getInstance().getTileData(frontMapIndex, frontMapX, entity.getMapY()+1) != EMPTY_TILE_CODE)
                    isColliding = true;
        }
        return isColliding;
    }

    public static boolean rightCollision(EntityCoordinates entity) {
        boolean isColliding = false;
        int frontMapX = entity.getMapX()+1;
        int frontMapIndex = entity.getMapIndex();
        if(frontMapX == GameDataConfig.getInstance().getMapSectionSize()){
            frontMapX = 0;
            frontMapIndex++;
        }
        if(GameModel.getInstance().getTileData(frontMapIndex, frontMapX, entity.getMapY()) != EMPTY_TILE_CODE){
            isColliding = true;
        }
        double translY = entity.getTranslY();
        if(translY > 0 && GameModel.getInstance().getTileData(frontMapIndex, frontMapX, entity.getMapY()+1) != EMPTY_TILE_CODE)
            isColliding = true;
        if(translY < 0 && GameModel.getInstance().getTileData(frontMapIndex, frontMapX, entity.getMapY()-1) != EMPTY_TILE_CODE)
            isColliding = true;
        return isColliding;
    }

}
