package Controller;

import Commons.EntityCoordinates;

public class CollisionHandler {
    private CollisionHandler(){};
    //TODO later, add collision method between entities and maybe change playerPosition to entityPosition
    public static boolean bottomCollision(final EntityCoordinates entityCoordinates){
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


//        System.out.println("MapIndex: "+playerPosition[0]+"\nmapX: "+playerPosition[1]+" traslX: "+playerPosition[2]+"\nmapY: "+playerPosition[3]+" traslY: "+playerPosition[4]+"\n isColliding "+isColliding);
        return isColliding;
    }
//TODO refactor with EntityCoordinates
    public static boolean rightCollision(final EntityCoordinates entityCoordinates){
        boolean isColliding = false;
        if(entityCoordinates.getTraslX() == 0){
            int mapIndex = entityCoordinates.getMapIndex();
            int mapX = entityCoordinates.getMapX();
            if(mapX == 15){
                mapIndex++;
                mapX = -1;
            }
            if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, entityCoordinates.getMapY()) != 34)
                isColliding = true;
            if(entityCoordinates.getTraslY() != 0)
                if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, entityCoordinates.getMapY()-1) != 34)
                    isColliding = true;
        }
        return isColliding;
    }

    public static boolean leftCollision(final int[] playerPosition){
        return false; //nothing to do for now
    }

    public static boolean topCollision(final int[] playerPosition){
        boolean isColliding = false;
        if(GameEngine.getInstance().getTileData(playerPosition[0],playerPosition[1],playerPosition[3]-1) != 34){
            if(playerPosition[1] == 0){
                if(GameEngine.getInstance().getTileData(playerPosition[0]-1,GameEngine.getInstance().getSectionSize()-1, playerPosition[3]-1) != 34)
                    isColliding = true;
            }else{
                if(GameEngine.getInstance().getTileData(playerPosition[0],playerPosition[1]-1,playerPosition[3]-1) != 34)
                    isColliding = true;
            }
        }
        return isColliding;
    }
}
