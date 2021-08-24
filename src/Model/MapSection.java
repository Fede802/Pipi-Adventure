package Model;

public abstract class MapSection {

    public static final int SECTION_SIZE = 16;

    protected int[][] map;

    public MapSection(){
        map = new int[SECTION_SIZE][SECTION_SIZE];
    }

    public int getCell(final int mapX,final int mapY){
        return map[mapY][mapX];
    }

}

