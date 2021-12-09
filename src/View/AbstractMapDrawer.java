package view;

import utils.GameConfig;
import utils.GameDataConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractMapDrawer {

    private final int UPDATE_TICK_DELAY = GameConfig.getInstance().getUpdateTickDelay();

    protected final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    protected final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();
    protected final JPanel PARENT_PANEL;

    protected ArrayList<ArrayList<Image>> tileSetsArray;
    protected int renderedTileSize;
    protected int currentTileset = 0;
    private boolean transition;
    private int currentTick;
    //maps order when transition
    private boolean descending;

    public AbstractMapDrawer(final JPanel PARENT_PANEL){
        this.PARENT_PANEL = PARENT_PANEL;
    }

    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
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

    public abstract void drawMap(final Graphics2D g2d);

    public abstract void loadResources();

}
