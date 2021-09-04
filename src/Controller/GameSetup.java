package Controller;

import Model.GameModel;

public class GameSetup implements IGameSetup{
    private static IGameSetup instance = null;

    private GameSetup() {}

    public static IGameSetup getInstance() {
        if (instance == null)
            instance = new GameSetup();
        return instance;
    }


    @Override
    public int getSectionSize() {
        return GameModel.getInstance().getSectionSize();
    }

    @Override
    public int getMapLength() {
        return GameModel.getInstance().getMapLength();
    }

    @Override
    public void setupGame() {

    }

    @Override
    public void updateGameBar() {

    }
}
