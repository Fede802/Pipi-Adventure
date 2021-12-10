package view;

import utils.GameConfig;
import utils.GameDataConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractMapDrawer {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final int UPDATE_TICK_DELAY = GameConfig.getInstance().getUpdateTickDelay();

    protected final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    protected final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    protected final JPanel PARENT_PANEL;

    protected ArrayList<ArrayList<Image>> tileSetsArray = new ArrayList<>();
    protected int renderingTileSize;
    protected int currentTileset;

    private boolean transition;
    private int currentTick;
    //maps order when transition
    private boolean descending;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public AbstractMapDrawer(JPanel parentPanel){
        this.PARENT_PANEL = parentPanel;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void updateRenderingTileSize(int renderingTileSize){
        this.renderingTileSize = renderingTileSize;
    }

    public void update() {
        if(transition)
            currentTick++;
        if (currentTick == UPDATE_TICK_DELAY){
            if (descending){
                currentTileset--;
            }else {
                currentTileset++;
            }
            currentTick = 0;
            if (currentTileset == tileSetsArray.size()-1 || currentTileset==0){
                transition = false;
                descending = !descending;
            }
        }
    }

    public void updateTileset(){
        transition = true;
    }

    public void reset() {
        currentTick=0;
        transition=false;
        currentTileset=0;
        descending=false;
    }

    //    --------------------------------------------------------
    //                      ABSTRACT METHODS
    //    --------------------------------------------------------

    public abstract void drawMap(final Graphics2D g2d);

    public abstract void loadResources();

}
