package view;

import controller.GameEngine;
import utils.GameDataConfig;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class MapDrawer {

    public static final int NUM_ROWS_OF_TILESET = GameDataConfig.getInstance().getDefaultNumRowsOfTileset();
    public static final int NUM_COLUMNS_OF_TILESET = GameDataConfig.getInstance().getDefaultNumColumnsOfTileset();
    public static final int TILE_SIZE = GameDataConfig.getInstance().getDefaultTilesetTileSize();
    public static final int NUM_TILES = NUM_COLUMNS_OF_TILESET*NUM_ROWS_OF_TILESET;
    public static final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    public static final int MAP_LENGTH = GameDataConfig.getInstance().getMapLength();

    private int renderedTileSize; //= GameConfig.getInstance().getRenderedTileSize();
    private final JPanel parentPanel;
    //TODO later maybe has not to be final when we have more tileset, and maybe we could have an arraylist of tileset
    private final Image[] tileArray = new Image[NUM_TILES];

    public MapDrawer(final JPanel parentPanel, final File tileSet){
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
        for(int mapIndex = 0; mapIndex < MAP_LENGTH; mapIndex++){
            g2d.drawLine(Math.toIntExact(Math.round(renderedTileSize * SECTION_SIZE * mapIndex - GameEngine.getInstance().getMapTranslX())),0,Math.toIntExact(Math.round(renderedTileSize * SECTION_SIZE * mapIndex - GameEngine.getInstance().getMapTranslX())),parentPanel.getHeight());
            for (int mapY = SECTION_SIZE-1; mapY > 0; mapY--)
                for (int mapX = 0; mapX < SECTION_SIZE; mapX++)
                g2d.drawImage(tileArray[GameEngine.getInstance().getTileData(mapIndex, mapX, mapY)], Math.toIntExact(Math.round(renderedTileSize * (mapX + SECTION_SIZE * mapIndex) - GameEngine.getInstance().getMapTranslX())), parentPanel.getHeight()-((SECTION_SIZE-mapY) * renderedTileSize), renderedTileSize, renderedTileSize, null);
        }

    }

    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

}