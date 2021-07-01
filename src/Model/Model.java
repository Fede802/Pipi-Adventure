package Model;

public class Model implements IModel{

    private static Model istance = null;

    public static IModel getIstance(){

        if (istance == null){

            istance = new Model();
        }
        return istance;
    }

    @Override
    public int getiIndex() {
        return 0;
    }

    @Override
    public int getjIndex() {
        return 0;
    }

    @Override
    public int getNumRowsOfMap() {
        return 0;
    }

    @Override
    public int getNumColonsOfMap() {
        return 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public void initGAme() {

    }
}
