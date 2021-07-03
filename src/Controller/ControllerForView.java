package Controller;

public class ControllerForView implements IControllerForView{

    private static ControllerForView istance = null;

    public static IControllerForView getIstance(){

        if (istance == null){
            istance = new ControllerForView();
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
    public void initGAme() {

    }

    @Override
    public void openGameWindow() {

    }

    @Override
    public void closeGameWindow() {

    }
}

