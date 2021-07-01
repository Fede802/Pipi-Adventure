package View;

import Model.Model;

public class View implements IView{

    private static View istance = null;

    public static IView getIstance(){

        if (istance == null){

            istance = new View();
        }
        return istance;
    }

    @Override
    public void openGameWindow() {

    }

    @Override
    public void closeGameWindow() {

    }
}
