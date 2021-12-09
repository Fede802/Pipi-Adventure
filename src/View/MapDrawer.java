package view;

import controller.GameEngine;
import utils.ImageLoader;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MapDrawer extends AbstractMapDrawer {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public MapDrawer(JPanel PARENT_PANEL) {
        super(PARENT_PANEL);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void drawMap(final Graphics2D g2d){
        for(int mapIndex = 0; mapIndex < MAP_LENGTH; mapIndex++){
            for (int mapY = SECTION_SIZE-1; mapY > 0; mapY--)
                for (int mapX = 0; mapX < SECTION_SIZE; mapX++)
                    g2d.drawImage(tileSetsArray.get(currentTileset).get(GameEngine.getInstance().getTileData(mapIndex, mapX, mapY)),
                        Math.toIntExact(Math.round(renderedTileSize * (mapX + SECTION_SIZE * mapIndex) - GameEngine.getInstance().getMapTranslX())),
                        PARENT_PANEL.getHeight()-((SECTION_SIZE-mapY) * renderedTileSize),
                        renderedTileSize,
                        renderedTileSize,
                        null
                    );
        }
    }

    @Override
    public void loadResources() {
        tileSetsArray = new ArrayList<>();
        tileSetsArray.add(ImageLoader.getInstance().getImages("res\\images\\tilesets\\1.png"));
        tileSetsArray.add(ImageLoader.getInstance().getImages("res\\images\\tilesets\\2.png"));
        tileSetsArray.add(ImageLoader.getInstance().getImages("res\\images\\tilesets\\3.png"));
        tileSetsArray.add(ImageLoader.getInstance().getImages("res\\images\\tilesets\\4.png"));
        tileSetsArray.add(ImageLoader.getInstance().getImages("res\\images\\tilesets\\5.png"));
        tileSetsArray.add(ImageLoader.getInstance().getImages("res\\images\\tilesets\\6.png"));
    }

}