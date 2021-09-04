package View;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;
import Controller.GameEngine;
import Controller.GameSetup;
import Utils.Config;
import Utils.GameConfig;

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
    private static int RENDERED_TILE_SIZE = GameConfig.getInstance().getRenderedTileSize();
    private static int SECTION_SIZE = GameSetup.getInstance().getSectionSize();

    private EntitiesDrawer(){}

    public static void drawEntities(Graphics2D g2d, JPanel panel){
        RENDERED_TILE_SIZE = GameConfig.getInstance().getRenderedTileSize();
        entitiesCoordinates = GameEngine.getInstance().getEntitiesCoordinates();

        for(int i = 0; i < entitiesCoordinates.size(); i++){
            entityPos = entitiesCoordinates.get(i).getKey();
            animation = entitiesCoordinates.get(i).getValue();
            if(entitiesCoordinates.get(i).getKey().getID() == 0){

                g2d.drawImage(gun,  (entityPos.getSTART_MAP_X()+1) * RENDERED_TILE_SIZE -(MapDrawer.RENDERED_TILE_SIZE/10), (panel.getHeight()-(SECTION_SIZE-entityPos.getSTART_MAP_Y())*RENDERED_TILE_SIZE)+(int)(entityPos.getTraslY()) , RENDERED_TILE_SIZE, RENDERED_TILE_SIZE, null);
//                g2d.drawImage(bullet,  (entityPos.getSTART_MAP_X()+1 ) * RENDERED_TILE_SIZE , Math.toIntExact(Math.round(panel.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * RENDERED_TILE_SIZE + entityPos.getTraslY())) + RENDERED_TILE_SIZE/3, RENDERED_TILE_SIZE/2, RENDERED_TILE_SIZE/2, null);
//                g2d.drawRect((entityPos.getSTART_MAP_X()+1) * RENDERED_TILE_SIZE ,Math.toIntExact(Math.round(panel.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * RENDERED_TILE_SIZE + entityPos.getTraslY()))+RENDERED_TILE_SIZE/3, RENDERED_TILE_SIZE/2, RENDERED_TILE_SIZE/2);
//                System.out.println(entityPos.getTraslY());
                g2d.drawImage(animation.getFrame(), entityPos.getSTART_MAP_X() * RENDERED_TILE_SIZE, (panel.getHeight()-(SECTION_SIZE-entityPos.getSTART_MAP_Y())*RENDERED_TILE_SIZE)+(int)(entityPos.getTraslY()), RENDERED_TILE_SIZE,RENDERED_TILE_SIZE, null);
//                g2d.drawImage(animation.getFrame(), entityPos.getSTART_MAP_X() * RENDERED_TILE_SIZE, Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - entityPos.getSTART_MAP_Y()) * RENDERED_TILE_SIZE + entityPos.getTraslY())), RENDERED_TILE_SIZE,RENDERED_TILE_SIZE, null);
            }else if(entitiesCoordinates.get(i).getKey().getID() == 3){
                //drawBullets

            }else{
                g2d.drawImage(animation.getFrame(), Math.toIntExact(Math.round((entityPos.getSTART_MAP_X() + SECTION_SIZE * entityPos.getMapIndex()) * RENDERED_TILE_SIZE - GameEngine.getInstance().getMapTraslX() + entityPos.getTraslX())), Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - entityPos.getSTART_MAP_Y()) * RENDERED_TILE_SIZE + entityPos.getTraslY())), RENDERED_TILE_SIZE, RENDERED_TILE_SIZE, null);
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

}
