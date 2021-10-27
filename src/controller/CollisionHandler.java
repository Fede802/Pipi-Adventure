package controller;

import commons.EntityCoordinates;
import model.GameModel;
import utils.GameDataConfig;

public class CollisionHandler {
    private static int sectionSize = GameDataConfig.getInstance().getMapSectionSize();

    public static boolean collide(EntityCoordinates entity1, EntityCoordinates entity2) {
        double entity1X = (entity1.getMapIndex()*sectionSize+entity1.getMapX())*GameDataConfig.getInstance().getRenderedTileSize()+entity1.getTranslX();
        double entity2X = (entity2.getMapIndex()*sectionSize+entity2.getMapX())*GameDataConfig.getInstance().getRenderedTileSize()+entity2.getTranslX();
        double entity1Y = entity1.getMapY()*GameDataConfig.getInstance().getRenderedTileSize()+entity1.getTranslY();
        double entity2Y = entity2.getMapY()*GameDataConfig.getInstance().getRenderedTileSize()+entity2.getTranslY();
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
        if(next.getTranslY() == 0){
            //TODO add emptyTileKeyCode and maybe all the keyCode
            if(GameModel.getInstance().getTileData(next.getMapIndex(), next.getMapX(), next.getMapY()+1) != 34)
                isColliding = true;
            if(next.getTranslX() != 0)
                if(GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()+1) != 34)
                    isColliding = true;
        }
        return isColliding;
    }

    public static boolean rigthCollision(EntityCoordinates next){
        boolean isColliding = false;
        if(next.getTranslX() == 0){
            int frontMapX = next.getMapX()+1;
            int frontMapIndex = next.getMapIndex();
            if(frontMapX == GameDataConfig.getInstance().getMapSectionSize()){
                frontMapX = 0;
                frontMapIndex++;
            }
            if(GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()) != 34){
                isColliding = true;
            }
            double translY = next.getTranslY();
            if(translY > 0 && GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()+1) != 34)
                isColliding = true;
            if(translY < 0 && GameModel.getInstance().getTileData(frontMapIndex, frontMapX, next.getMapY()-1) != 34)
                isColliding = true;

        }
        return isColliding;
    }


}
