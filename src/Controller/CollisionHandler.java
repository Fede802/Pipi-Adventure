package Controller;

import Commons.EntityCoordinates;
import Commons.Pair;
import Model.ApplicationStatus;
import Model.GameModel;
import Utils.GameConfig;

import java.util.ArrayList;

public class CollisionHandler {
    private static final EntityCoordinates playerCoordinates = GameModel.getInstance().getPlayerCoordinates();
    private static ArrayList<Pair<Integer,EntityCoordinates>> playerBullets;
    private static ArrayList<Pair<Integer,EntityCoordinates>> entities;
    private CollisionHandler(){}

    public static boolean playerBottomCollision(){
        boolean isColliding = false;
        int mapIndex = playerCoordinates.getMapIndex();
        int mapX = playerCoordinates.getSTART_MAP_X()+(int)playerCoordinates.getTraslX()/GameConfig.getInstance().getRenderedTileSize();
        boolean touchTile = playerCoordinates.getTraslY()%GameConfig.getInstance().getRenderedTileSize() == 0;
        int mapY = playerCoordinates.getSTART_MAP_Y()+(int)playerCoordinates.getTraslY()/GameConfig.getInstance().getRenderedTileSize();
        if(mapX >= GameSetup.getInstance().getSectionSize()){
            mapIndex++;
            mapX-=GameSetup.getInstance().getSectionSize();
        }
        if(touchTile) {
            if (GameModel.getInstance().getTileData(playerCoordinates.getMapIndex(), mapX, mapY + 1) != 34) {
                //TODO later, maybe this condition (also in topCollision) could broke the game if at start player moves
                isColliding = true;

            }
            if (playerCoordinates.getTraslX()%GameConfig.getInstance().getRenderedTileSize() != 0) {
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
        playerBullets = GameModel.getInstance().getPlayerBullets();
        entities = GameModel.getInstance().getEntities();
//        int mapIndex
        for(int i = 0; i < entities.size(); i++){
            if(/*playerCoordinates.getMapIndex() == entities.get(i).getValue().getMapIndex()
                    && playerCoordinates.getMapX()+playerCoordinates.getTraslX() == entities.get(i).getValue().getMapX()
                    +entities.get(i).getValue().getTraslX()
//                    && playerCoordinates.getMapY() == entities.get(i).getValue().getMapY(), playerCoordinates.getMapIndex() == entities.get(i).getValue().getMapIndex()&&*/entities.get(i).getValue()!=null&&playerCoordinates.collide(entities.get(i).getValue()))
                if(entities.get(i).getValue().getID() == 2){
                    System.out.println("collide");
                    GameModel.getInstance().updateEntitiesStatus(entities.get(i).getKey());
                    ApplicationStatus.getInstance().setCoin(1);
                }
                else
                    GameModel.getInstance().updatePlayerStatus(-1);
            playerBullets = GameModel.getInstance().getPlayerBullets();
            entities = GameModel.getInstance().getEntities();
        }
//        for(int i = 0; i < playerBullets.size(); i++)
//            for(int j = 0; j < entities.size(); j++){
//                if(playerBullets.get(i).getValue().getMapIndex() == entities.get(j).getValue().getMapIndex()
//                        && playerBullets.get(i).getValue().getMapX()+playerBullets.get(i).getValue().getTraslX() == entities.get(j).getValue().getMapX()
//                        +entities.get(j).getValue().getTraslX()
//                        && playerBullets.get(i).getValue().getMapY()+playerBullets.get(i).getValue().getTraslY() == entities.get(j).getValue().getMapY()
//                        +entities.get(j).getValue().getTraslY())
//                    if(entities.get(j).getKey() == 1)
//                        System.out.println("update this enemy id status");
//            }
//        for(int i = 0; i < playerBullets.size(); i++){
//            boolean isColliding = false;
//            if(playerBullets.get(i).getValue().getTraslX() == 0){
//                int mapIndex = playerBullets.get(i).getValue().getMapIndex();
//                int mapX = playerBullets.get(i).getValue().getMapX();
//                if(mapX == 15){
//                    mapIndex++;
//                    mapX = -1;
//                }
//                if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerBullets.get(i).getValue().getMapY()) != 34)
//                    isColliding = true;
//                if(playerBullets.get(i).getValue().getTraslY() != 0)
//                    if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerBullets.get(i).getValue().getMapY()-1) != 34)
//                        isColliding = true;
//            }
//            if(isColliding){
//                System.out.println("update this bullet id status");
//            }
        }
    }




//}
//}
