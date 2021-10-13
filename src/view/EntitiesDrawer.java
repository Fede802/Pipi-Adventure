package view;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;
import controller.GameEngine;
import utils.GameConfig;
import utils.GameDataConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EntitiesDrawer {
    private static final int TICK_TO_UPDATE_ANIMATION = 3;
    private static ArrayList<Pair<EntityCoordinates, Animation>> entitiesCoordinates;
    private static EntityCoordinates entityPos;
    private static Animation animation;
    private static int tick;
    private static final Image gun = new ImageIcon("Resources/Entities/Player/Guns/Pistola_2.png").getImage();
    private int renderedTileSize;
    private final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();

    public EntitiesDrawer(){}

    public void drawEntities(Graphics2D g2d, JPanel panel){
        //TODO panel in constructor
        int entityNum = GameEngine.getInstance().getTotalEntity();
        double mapTranslX = GameEngine.getInstance().getMapTranslX();
//        g2d.drawImage(gun,  (GameDataConfig.getInstance().getPlayerStartMapX()+1) * renderedTileSize -(renderedTileSize/10), (panel.getHeight()-(SECTION_SIZE-GameDataConfig.getInstance().getPlayerStartMapY())*renderedTileSize)+(int)(entityPos.getTranslY()) , renderedTileSize, renderedTileSize, null);
        for(int i = 0; i < entityNum; i++){
            g2d.drawImage(GameEngine.getInstance().getEntityForRendering(i).getValue().getFrame(),
                    Math.toIntExact(Math.round((GameEngine.getInstance().getEntityForRendering(i).getKey().getMapX() + SECTION_SIZE * GameEngine.getInstance().getEntityForRendering(i).getKey().getMapIndex()) * renderedTileSize - mapTranslX + GameEngine.getInstance().getEntityForRendering(i).getKey().getTranslX())),
                    Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - GameEngine.getInstance().getEntityForRendering(i).getKey().getMapY()) * renderedTileSize + GameEngine.getInstance().getEntityForRendering(i).getKey().getTranslY())),
                    renderedTileSize,
                    renderedTileSize,
                    null
            );
        }
        //TODO move to controller

//            if(tick == TICK_TO_UPDATE_ANIMATION){
//                animation.update();
//            }
//        }
//        if(tick == TICK_TO_UPDATE_ANIMATION){
//            tick = 0;
//        }
//        tick++;

    }
    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

}
