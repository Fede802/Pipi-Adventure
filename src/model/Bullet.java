package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;
import utils.GameDataConfig;
import view.GameOverPanel;

public class Bullet extends GameEntity{
    private final int MOVING_STEP =2*(int)(GameDataConfig.getInstance().getMapSectionSize()*TILE_STEP);
    private int currentStep = 0;

    private static final Animation WALK_ANIMATION_RIGHT = new Animation("Resources/Entities/Player/Guns/Nuovo_Proiettile.gif");

    public Bullet(EntityCoordinates entityCoordinates) {
        super(EntityType.BULLET,entityCoordinates);
        TILE_STEP = 2.5;
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
    }

    @Override
    public void move() {
        //TODO debug movement coords update
        if(isAlive){
            entityCoordinates.updateTraslX(VEL_X);
            if(entityCoordinates.getTranslX() >= RENDERED_TILE_SIZE){
                entityCoordinates.setTranslX(entityCoordinates.getTranslX()-RENDERED_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
            }
            if(entityCoordinates.getMapX() == GameDataConfig.getInstance().getMapSectionSize()){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
                entityCoordinates.setMapX(0);
            }
            currentStep++;
            if (currentStep == MOVING_STEP)
                isAlive = false;
        }
    }
}
