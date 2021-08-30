package Controller;

import Commons.EntityCoordinates;
import Commons.Pair;
import Model.GameModel;
import Model.GameStatus;

import java.util.ArrayList;

public class CollisionHandler {
    private static final EntityCoordinates playerCoordinates = GameModel.getInstance().getPlayerCoordinates();
    private static ArrayList<Pair<Integer,EntityCoordinates>> playerBullets;
    private static ArrayList<Pair<Integer,EntityCoordinates>> entities;
    private CollisionHandler(){}

    public static boolean playerBottomCollision(){
        boolean isColliding = false;
        if(playerCoordinates.getTraslY() == 0) {
            if (GameModel.getInstance().getTileData(playerCoordinates.getMapIndex(), playerCoordinates.getMapX(), playerCoordinates.getMapY() + 1) != 34) {
                //TODO later, maybe this condition (also in topCollision) could broke the game if at start player moves
                isColliding = true;

            }
            if (playerCoordinates.getTraslX() != 0) {
                if (playerCoordinates.getMapX() == GameModel.getInstance().getSectionSize() - 1) {
                    if (GameModel.getInstance().getTileData(playerCoordinates.getMapIndex() + 1, 0, playerCoordinates.getMapY() + 1) != 34)
                        isColliding = true;
                } else {
                    if (GameModel.getInstance().getTileData(playerCoordinates.getMapIndex(), playerCoordinates.getMapX() + 1, playerCoordinates.getMapY() + 1) != 34)
                        isColliding = true;
                }
            }
        }
        return isColliding;
    }
    public static boolean playerRightCollision(){
        boolean isColliding = false;
        if(playerCoordinates.getTraslX() == 0){
            int mapIndex = playerCoordinates.getMapIndex();
            int mapX = playerCoordinates.getMapX();
            if(mapX == 15){
                mapIndex++;
                mapX = -1;
            }
            if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerCoordinates.getMapY()) != 34)
                isColliding = true;
            if(playerCoordinates.getTraslY() != 0)
                if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerCoordinates.getMapY()-1) != 34)
                    isColliding = true;
        }
        return isColliding;
    }
    //TODO player top collision

    public static void entityCollision(){
        playerBullets = GameModel.getInstance().getPlayerBullets();
        entities = GameModel.getInstance().getEntities();
        for(int i = 0; i < entities.size(); i++){
            if(playerCoordinates.getMapIndex() == entities.get(i).getValue().getMapIndex()
                    && playerCoordinates.getMapX()+playerCoordinates.getTraslX() == entities.get(i).getValue().getMapX()
                    +entities.get(i).getValue().getTraslX()
                    && playerCoordinates.getMapY() == entities.get(i).getValue().getMapY())
                if(entities.get(i).getValue().getID() == 2){
                    GameModel.getInstance().updateEntitiesStatus(entities.get(i).getKey());
                    GameModel.getInstance().addCoin();
                }
                else
                    System.out.println("gameOver");
            playerBullets = GameModel.getInstance().getPlayerBullets();
            entities = GameModel.getInstance().getEntities();
        }
        for(int i = 0; i < playerBullets.size(); i++)
            for(int j = 0; j < entities.size(); j++){
                if(playerBullets.get(i).getValue().getMapIndex() == entities.get(j).getValue().getMapIndex()
                        && playerBullets.get(i).getValue().getMapX()+playerBullets.get(i).getValue().getTraslX() == entities.get(j).getValue().getMapX()
                        +entities.get(j).getValue().getTraslX()
                        && playerBullets.get(i).getValue().getMapY()+playerBullets.get(i).getValue().getTraslY() == entities.get(j).getValue().getMapY()
                        +entities.get(j).getValue().getTraslY())
                            if(entities.get(j).getKey() == 1)
                                System.out.println("update this enemy id status");
        }
        for(int i = 0; i < playerBullets.size(); i++){
            boolean isColliding = false;
            if(playerBullets.get(i).getValue().getTraslX() == 0){
                int mapIndex = playerBullets.get(i).getValue().getMapIndex();
                int mapX = playerBullets.get(i).getValue().getMapX();
                if(mapX == 15){
                    mapIndex++;
                    mapX = -1;
                }
                if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerBullets.get(i).getValue().getMapY()) != 34)
                    isColliding = true;
                if(playerBullets.get(i).getValue().getTraslY() != 0)
                    if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerBullets.get(i).getValue().getMapY()-1) != 34)
                        isColliding = true;
            }
            if(isColliding){
                System.out.println("update this bullet id status");
            }
        }
    }




}
    //TODO later, add collision method between entities and maybe change playerPosition to entityPosition
    /*public static boolean bottomCollision(final EntityCoordinates entityCoordinates){
        boolean isColliding = false;
        if(entityCoordinates.getTraslY() == 0) {
            if (GameEngine.getInstance().getTileData(entityCoordinates.getMapIndex(), entityCoordinates.getMapX(), entityCoordinates.getMapY() + 1) != 34) {
                //TODO later, maybe this condition (also in topCollision) could broke the game if at start player moves
                isColliding = true;

            }
            if (entityCoordinates.getTraslX() != 0) {
                if (entityCoordinates.getMapX() == GameEngine.getInstance().getSectionSize() - 1) {
                    if (GameEngine.getInstance().getTileData(entityCoordinates.getMapIndex() + 1, 0, entityCoordinates.getMapY() + 1) != 34)
                        isColliding = true;
                } else {
                    if (GameEngine.getInstance().getTileData(entityCoordinates.getMapIndex(), entityCoordinates.getMapX() + 1, entityCoordinates.getMapY() + 1) != 34)
                        isColliding = true;
                }
            }
        }
        return isColliding;
    }
}*/
