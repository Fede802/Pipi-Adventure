package view;

import controller.GameEngine;
import utils.GameDataConfig;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MapDrawer {
    //todo config
    private final int UPDATE_TICK_DELAY = 40;
    private final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    private final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();

    private int renderedTileSize;
    private final JPanel parentPanel;
    private int currentTileset = 0;
    private boolean transition;
    private int currentTick;
    private boolean descending;

    private ArrayList<ArrayList<Image>> tileArray;

    public MapDrawer(final JPanel parentPanel, final ArrayList<ArrayList<Image>> tileSet){
        this.parentPanel = parentPanel;
        tileArray = tileSet;
    }

    public void drawMap(final Graphics2D g2d){
        for(int mapIndex = 0; mapIndex < MAP_LENGTH; mapIndex++){
            for (int mapY = SECTION_SIZE-1; mapY > 0; mapY--)
                for (int mapX = 0; mapX < SECTION_SIZE; mapX++)
                g2d.drawImage(tileArray.get(currentTileset).get(GameEngine.getInstance().getTileData(mapIndex, mapX, mapY)),
                        Math.toIntExact(Math.round(renderedTileSize * (mapX + SECTION_SIZE * mapIndex) - GameEngine.getInstance().getMapTranslX())),
                        parentPanel.getHeight()-((SECTION_SIZE-mapY) * renderedTileSize),
                        renderedTileSize,
                        renderedTileSize,
                        null
                );
        }
    }

    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

    public void update(){
        if(transition)
            currentTick++;
        if (currentTick == UPDATE_TICK_DELAY){
            if (descending){
                currentTileset--;
            }else {
                currentTileset++;
            }
            currentTick = 0;
            if (currentTileset == tileArray.size()-1 || currentTileset==0){
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
}