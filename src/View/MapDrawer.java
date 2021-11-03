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

    public static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    public static final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();

    private int renderedTileSize;
    private final JPanel parentPanel;
    //TODO later maybe has not to be final when we have more tileset, and maybe we could have an arraylist of tileset
    private ArrayList<Image> tileArray;

    public MapDrawer(final JPanel parentPanel, final ArrayList<Image> tileSet){
        this.parentPanel = parentPanel;
        tileArray = tileSet;
    }

    public void drawMap(final Graphics2D g2d){
        for(int mapIndex = 0; mapIndex < MAP_LENGTH; mapIndex++){
            //g2d.drawLine(Math.toIntExact(Math.round(renderedTileSize * SECTION_SIZE * mapIndex - GameEngine.getInstance().getMapTranslX())),0,Math.toIntExact(Math.round(renderedTileSize * SECTION_SIZE * mapIndex - GameEngine.getInstance().getMapTranslX())),parentPanel.getHeight());
            for (int mapY = SECTION_SIZE-1; mapY > 0; mapY--)
                for (int mapX = 0; mapX < SECTION_SIZE; mapX++)
                g2d.drawImage(tileArray.get(GameEngine.getInstance().getTileData(mapIndex, mapX, mapY)), Math.toIntExact(Math.round(renderedTileSize * (mapX + SECTION_SIZE * mapIndex) - GameEngine.getInstance().getMapTranslX())), parentPanel.getHeight()-((SECTION_SIZE-mapY) * renderedTileSize), renderedTileSize, renderedTileSize, null);
        }

    }

    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

}