package View;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;
import Controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EntitiesDrawer {
    private static final int TICK_TO_UPDATE_ANIMATION = 3;
    private static ArrayList<Pair<EntityCoordinates, Animation>> entitiesCoordinates;
    private static EntityCoordinates entityPos;
    private static Animation animation;
    private static int tick;
    private EntitiesDrawer(){}
    public static void drawEntities(Graphics2D g2d, JPanel panel){
        //TODO get entitiesID
        entitiesCoordinates = GameEngine.getInstance().getEntitiesCoordinates();

        for(int i = 0; i < entitiesCoordinates.size(); i++){
            entityPos = entitiesCoordinates.get(i).getKey();
            animation = entitiesCoordinates.get(i).getValue();
            if(entitiesCoordinates.get(i).getKey().getID() == 0){
                g2d.drawImage(animation.getFrame(), entityPos.getSTART_MAP_X() * MapDrawer.RENDERED_TILE_SIZE, panel.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * MapDrawer.RENDERED_TILE_SIZE + entityPos.getTraslY(), MapDrawer.RENDERED_TILE_SIZE, MapDrawer.RENDERED_TILE_SIZE, null);
            }else if(entitiesCoordinates.get(i).getKey().getID() == 3){
                //drawBullets
            }else{
                g2d.drawImage(animation.getFrame(), (entityPos.getMapX() + GameEngine.getInstance().getSectionSize() * entityPos.getMapIndex()) * MapDrawer.RENDERED_TILE_SIZE - GameEngine.getInstance().getMapTraslX() + entityPos.getTraslX(), panel.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * MapDrawer.RENDERED_TILE_SIZE - entityPos.getTraslY(), MapDrawer.RENDERED_TILE_SIZE, MapDrawer.RENDERED_TILE_SIZE, null);
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
