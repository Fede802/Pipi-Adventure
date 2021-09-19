package controller;

import commons.EntityCoordinates;
import model.GameModel;
import utils.GameConfig;
import utils.GameDataConfig;

import java.util.ArrayList;

public class CollisionHandler {
    private static final EntityCoordinates playerCoordinates = GameModel.getInstance().getPlayerCoordinates();
    private static ArrayList<EntityCoordinates> playerBullets;
    private static ArrayList<EntityCoordinates> entities;
    private CollisionHandler(){}

    public static boolean playerBottomCollision(){
        boolean isColliding = false;

        int mapIndex = playerCoordinates.getMapIndex();
        int mapX = playerCoordinates.getSTART_MAP_X()+(int)(playerCoordinates.getTranslX())/ GameDataConfig.getInstance().getRenderedTileSize();
        boolean touchTile = playerCoordinates.getTranslY()% GameDataConfig.getInstance().getRenderedTileSize() == 0;
        int mapY = playerCoordinates.getSTART_MAP_Y()+(int)(playerCoordinates.getTranslY())/GameDataConfig.getInstance().getRenderedTileSize();
        if(mapX >= 16){
            mapIndex++;
            mapX-=16;
        }
//        System.out.println("mapx "+mapX);
        if(touchTile) {
            if (GameModel.getInstance().getTileData(playerCoordinates.getMapIndex(), mapX, mapY + 1) != 34) {
                //TODO later, maybe this condition (also in topCollision) could broke the game if at start player moves
                isColliding = true;

            }
            if (playerCoordinates.getTranslX()%GameDataConfig.getInstance().getRenderedTileSize() != 0) {
                if (mapX == GameModel.getInstance().getSectionSize() - 1) {
                    if (GameModel.getInstance().getTileData(mapIndex + 1, 0, mapY + 1) != 34)
                        isColliding = true;
                } else {
                    if (GameModel.getInstance().getTileData(mapIndex, mapX + 1, mapY + 1) != 34)
                        isColliding = true;
                }
            }
        }
        return isColliding;
    }


//    public static boolean playerRightCollision(){
//        boolean isColliding = false;
//        if(playerCoordinates.getTraslX() == 0){
//            int mapIndex = playerCoordinates.getMapIndex();
//            int mapX = playerCoordinates.getMapX();
//            if(mapX == 15){
//                mapIndex++;
//                mapX = -1;
//            }
//            if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerCoordinates.getMapY()) != 34)
//                isColliding = true;
//            if(playerCoordinates.getTraslY() != 0)
//                if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerCoordinates.getMapY()-1) != 34)
//                    isColliding = true;
//        }
//        return isColliding;
//    }
    //TODO player top collision

    public static void entityCollision(){
        entities = GameModel.getInstance().getEntitiesCoordinates();
        for(int i = 0; i < entities.size(); i++)
            if(entities.get(i)!=null&&playerCoordinates.collide(entities.get(i)))
                if(entities.get(i).getID() == 2){
                    GameModel.getInstance().updateEntitiesStatus(i,1);
                }else{

                }
        playerBullets = GameModel.getInstance().getBullets();
            for(int i = 0; i < playerBullets.size();i++)
                for(int j = 0; j < entities.size(); j++)
                    if(entities.get(j)!=null&&playerBullets.get(i).collide(entities.get(j)))
                        if(entities.get(j).getID() == 1){
                            GameModel.getInstance().updateEntitiesStatus(j,1);
                        }else{

                        }
    }
}



