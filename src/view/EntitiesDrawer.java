package view;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;
import controller.GameEngine;
import utils.GameDataConfig;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;

public class EntitiesDrawer {

    private Image gun;
    private int renderedTileSize;
    private final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();

    public EntitiesDrawer(){}

    public void drawEntities(Graphics2D g2d, JPanel panel){
        //TODO panel in constructor
        int entityNum = GameEngine.getInstance().getTotalEntity();
        int playerId = GameEngine.getInstance().getPlayerId();
        double mapTranslX = GameEngine.getInstance().getMapTranslX();
//        g2d.drawImage(gun,  (GameDataConfig.getInstance().getPlayerStartMapX()+1) * renderedTileSize -(renderedTileSize/10), (panel.getHeight()-(SECTION_SIZE-GameDataConfig.getInstance().getPlayerStartMapY())*renderedTileSize)+(int)(entityPos.getTranslY()) , renderedTileSize, renderedTileSize, null);
        for(int i = 0; i < entityNum; i++){
            Pair<EntityCoordinates,Animation> temp = GameEngine.getInstance().getEntityForRendering(i);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, temp.getValue().getOpacity()));
            g2d.drawImage(temp.getValue().getFrame(),
                    Math.toIntExact(Math.round((temp.getKey().getMapX() + SECTION_SIZE * temp.getKey().getMapIndex()) * renderedTileSize - mapTranslX + temp.getKey().getTranslX())),
                    Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - temp.getKey().getMapY()) * renderedTileSize + temp.getKey().getTranslY())),
                    renderedTileSize,
                    renderedTileSize,
                    null
            );
            if(i == playerId)
                g2d.drawImage(gun,
                        Math.toIntExact(Math.round((temp.getKey().getMapX()+ 1 + SECTION_SIZE * temp.getKey().getMapIndex()) * renderedTileSize - mapTranslX -(renderedTileSize/10) + temp.getKey().getTranslX())),
                        Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - temp.getKey().getMapY()) * renderedTileSize + temp.getKey().getTranslY())),
                        renderedTileSize,
                        renderedTileSize,
                        null
                );
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

    public void getRes(){
        gun = ResourceLoader.getInstance().getRes("res\\images\\entities\\player\\Pistola.png").get(0);
    }
}
