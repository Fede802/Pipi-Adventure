package View;

import Controller.GameEngine;
import Controller.GameSetup;
import Utils.Config;
import Utils.GameConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MapDrawer {

    public static final int NUM_ROWS_OF_TILESET = 5;
    public static final int NUM_COLUMNS_OF_TILESET = 7;
    public static int RENDERED_TILE_SIZE = GameConfig.getInstance().getRenderedTileSize();
    public static final int TILE_SIZE = 160;
    public static final int NUM_TILES = NUM_COLUMNS_OF_TILESET*NUM_ROWS_OF_TILESET;
    public static final int SECTION_SIZE = GameSetup.getInstance().getSectionSize();
    public static final int MAP_LENGTH = GameSetup.getInstance().getMapLength();

    private final JPanel parentPanel;
    //TODO later maybe has not to be final when we have more tileset, and maybe we could have an arraylist of tileset
    private final Image[] tileArray = new Image[NUM_TILES];

    public MapDrawer(final JPanel parentPanel,final File tileSet){
        this.parentPanel = parentPanel;
        importTiles(tileSet);
    }



    private void importTiles(final File tileSet) {
        try {
            int rows = 1;
            int col = 0;

            //TODO later maybe improve import of tileset
            for(int i = 0; i < NUM_TILES; i++){
                tileArray[i] = ImageIO.read(tileSet).getSubimage(TILE_SIZE*col,(rows-1)*TILE_SIZE,TILE_SIZE,TILE_SIZE);

                col++;
                if(rows*NUM_COLUMNS_OF_TILESET-1 == i){
                    rows++;
                    col = 0;
                }
                //TODO later delete this sout
                System.out.println(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawMap(final Graphics2D g2d){
        RENDERED_TILE_SIZE = GameConfig.getInstance().getRenderedTileSize();
        for(int mapIndex = 0; mapIndex < MAP_LENGTH; mapIndex++)
            for (int mapY = SECTION_SIZE-1; mapY > 0; mapY--)
                for (int mapX = 0; mapX < SECTION_SIZE; mapX++)
                    g2d.drawImage(tileArray[GameEngine.getInstance().getTileData(mapIndex, mapX, mapY)], Math.toIntExact(Math.round(RENDERED_TILE_SIZE * (mapX + SECTION_SIZE * mapIndex) - GameEngine.getInstance().getMapTraslX())), parentPanel.getHeight()-((SECTION_SIZE-mapY) * RENDERED_TILE_SIZE), RENDERED_TILE_SIZE, RENDERED_TILE_SIZE, null);

    }

}