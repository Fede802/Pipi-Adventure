package controller;

import commons.EntityCoordinates;
import model.GameModel;
import utils.GameDataConfig;

public class CollisionHandler {

    private static final int EMPTY_TILE_CODE = GameDataConfig.getInstance().getEmptyTileCode();
    private static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    private static int renderedTileSize;

    public static boolean collide(EntityCoordinates entity1, EntityCoordinates entity2) {
        double entity1X = (entity1.getMapIndex()* SECTION_SIZE +entity1.getMapX())*renderedTileSize+entity1.getTraslX();
        double entity2X = (entity2.getMapIndex()* SECTION_SIZE +entity2.getMapX())*renderedTileSize+entity2.getTraslX();
        double entity1Y = entity1.getMapY()*renderedTileSize+entity1.getTraslY();
        double entity2Y = entity2.getMapY()*renderedTileSize+entity2.getTraslY();
        return entity1X < entity2X+entity2.getWidth() && entity1X +entity1.getWidth() > entity2X &&
                entity1Y < entity2Y + entity2.getHeight() && entity1Y + entity1.getHeight() > entity2Y;
    }

    public static boolean bottomCollision(EntityCoordinates next) {
        boolean isColliding = false;
        int frontMapX = next.getMapX()+1;
        int frontMapIndex = next.getMapIndex();
        if(frontMapX == GameDataConfig.getInstance().getMapSectionSize()){
            frontMapX = 0;
            frontMapIndex++;
        }
        if(next.getTraslY() == 0){
            if(GameModel.getInstance().getTileData(next.getMapIndex(), next.getMapX(), next.getMapY()+1) != EMPTY_TILE_CODE)
                isColliding = true;
            if(next.getTraslX() != 0)
                if(GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()+1) != EMPTY_TILE_CODE)
                    isColliding = true;
        }
        return isColliding;
    }
//todo comment
    public static boolean rightCollision(EntityCoordinates next) {
        boolean isColliding = false;
//        if(next.getTranslX() == 0){
            int frontMapX = next.getMapX()+1;
            int frontMapIndex = next.getMapIndex();
            if(frontMapX == GameDataConfig.getInstance().getMapSectionSize()){
                frontMapX = 0;
                frontMapIndex++;
            }
            if(GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()) != EMPTY_TILE_CODE){
                isColliding = true;
            }
            double translY = next.getTraslY();
            if(translY > 0 && GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()+1) != EMPTY_TILE_CODE)
                isColliding = true;
            if(translY < 0 && GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()-1) != EMPTY_TILE_CODE)
                isColliding = true;

//        }
        return isColliding;
    }

    public static void setRenderedTileSize(int renderedTileSize) {
        CollisionHandler.renderedTileSize = renderedTileSize;
    }

}
