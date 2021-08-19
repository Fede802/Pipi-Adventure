package Model;

public abstract class MapSection {

    protected int[][] map;

    public MapSection(){
        map = new int[16][16];
    }

    public int[][] getMap(){

        return map;
    }
    public int getCell(int i,int j){
        return map[i][j];
    }

}
