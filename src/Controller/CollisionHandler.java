package Controller;

public class CollisionHandler {
    private CollisionHandler(){};
    //TODO later, add collision method between entities and maybe change playerPosition to entityPosition
    public static boolean bottomCollision(final int[] playerPosition){
        boolean isColliding = false;
        if(playerPosition[4] == 0) {
            if (GameEngine.getInstance().getTileData(playerPosition[0], playerPosition[1], playerPosition[3] + 1) != 34) {
                //TODO later, maybe this condition (also in topCollision) could broke the game if at start player moves
                isColliding = true;

            }
            if (playerPosition[2] != 0) {
                if (playerPosition[1] == GameEngine.getInstance().getSectionSize() - 1) {
                    if (GameEngine.getInstance().getTileData(playerPosition[0] + 1, 0, playerPosition[3] + 1) != 34)
                        isColliding = true;
                } else {
                    if (GameEngine.getInstance().getTileData(playerPosition[0], playerPosition[1] + 1, playerPosition[3] + 1) != 34)
                        isColliding = true;
                }
            }
        }


//        System.out.println("MapIndex: "+playerPosition[0]+"\nmapX: "+playerPosition[1]+" traslX: "+playerPosition[2]+"\nmapY: "+playerPosition[3]+" traslY: "+playerPosition[4]+"\n isColliding "+isColliding);
        return isColliding;
    }

    public static boolean rightCollision(final int[] playerPosition){
        boolean isColliding = false;
        if(playerPosition[2] == 0){
            int mapIndex = playerPosition[0];
            int mapX = playerPosition[1];
            if(playerPosition[1] == 15){
                mapIndex++;
                mapX = -1;
            }
            if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerPosition[3]) != 34)
                isColliding = true;
            if(playerPosition[4] != 0)
                if(GameEngine.getInstance().getTileData(mapIndex,mapX+1, playerPosition[3]-1) != 34)
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
