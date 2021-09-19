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
        entitiesCoordinates = GameEngine.getInstance().getEntitiesForRendering();

        for(int i = 0; i < entitiesCoordinates.size(); i++){
            entityPos = entitiesCoordinates.get(i).getKey();
            animation = entitiesCoordinates.get(i).getValue();
            if(entitiesCoordinates.get(i).getKey().getID() == 0){

                g2d.drawImage(gun,  (entityPos.getSTART_MAP_X()+1) * renderedTileSize -(renderedTileSize/10), (panel.getHeight()-(SECTION_SIZE-entityPos.getSTART_MAP_Y())*renderedTileSize)+(int)(entityPos.getTranslY()) , renderedTileSize, renderedTileSize, null);
//                g2d.drawImage(bullet,  (entityPos.getSTART_MAP_X()+1 ) * RENDERED_TILE_SIZE , Math.toIntExact(Math.round(panel.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * RENDERED_TILE_SIZE + entityPos.getTraslY())) + RENDERED_TILE_SIZE/3, RENDERED_TILE_SIZE/2, RENDERED_TILE_SIZE/2, null);
//                g2d.drawRect((entityPos.getSTART_MAP_X()+1) * RENDERED_TILE_SIZE ,Math.toIntExact(Math.round(panel.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * RENDERED_TILE_SIZE + entityPos.getTraslY()))+RENDERED_TILE_SIZE/3, RENDERED_TILE_SIZE/2, RENDERED_TILE_SIZE/2);
//                System.out.println(entityPos.getTraslY());
                g2d.drawImage(animation.getFrame(), entityPos.getSTART_MAP_X() *renderedTileSize, (panel.getHeight()-(SECTION_SIZE-entityPos.getSTART_MAP_Y())*renderedTileSize)+(int)(entityPos.getTranslY()), renderedTileSize,renderedTileSize, null);
//                g2d.drawImage(animation.getFrame(), entityPos.getSTART_MAP_X() * RENDERED_TILE_SIZE, Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - entityPos.getSTART_MAP_Y()) * RENDERED_TILE_SIZE + entityPos.getTraslY())), RENDERED_TILE_SIZE,RENDERED_TILE_SIZE, null);
            }else if(entitiesCoordinates.get(i).getKey().getID() == 3){
                //drawBullets
                g2d.drawImage(animation.getFrame(), Math.toIntExact(Math.round((entityPos.getSTART_MAP_X() + SECTION_SIZE * entityPos.getMapIndex()) * renderedTileSize - GameEngine.getInstance().getMapTraslX() + entityPos.getTranslX())),(panel.getHeight()-(SECTION_SIZE-entityPos.getSTART_MAP_Y())*renderedTileSize)+(int)(entityPos.getTranslY()), renderedTileSize,renderedTileSize, null);

            }else{
                g2d.drawImage(animation.getFrame(), Math.toIntExact(Math.round((entityPos.getSTART_MAP_X() + SECTION_SIZE * entityPos.getMapIndex()) * renderedTileSize - GameEngine.getInstance().getMapTraslX() + entityPos.getTranslX())), Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - entityPos.getSTART_MAP_Y()) * renderedTileSize + entityPos.getTranslY())), renderedTileSize,renderedTileSize, null);
            }
            if(tick == TICK_TO_UPDATE_ANIMATION){
                animation.update();
            }
        }
        if(tick == TICK_TO_UPDATE_ANIMATION){
            tick = 0;
        }
        tick++;

    }
    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

}
