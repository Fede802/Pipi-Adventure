package model;

import commons.EntityCoordinates;
import commons.EntityType;
import utils.GameDataConfig;

public class EarthEnemy extends GameEntity{
    private static final int MOVING_STEP = 96;
    private int currentStep = 0;

    public EarthEnemy(EntityType id, EntityCoordinates entityCoordinates) {
        super(id,entityCoordinates);
        TILE_STEP = 10.0;
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
    }

    @Override
    public void move() {
        //TODO debug movement coords update
        if(isAlive) {
            if (currentStep < MOVING_STEP / 2) {
                entityCoordinates.updateTraslX(VEL_X);
                if(entityCoordinates.getTranslX() >= RENDERED_TILE_SIZE){
                    entityCoordinates.setTranslX(entityCoordinates.getTranslX()-RENDERED_TILE_SIZE);
                    entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
                }
            } else {
                entityCoordinates.updateTraslX(-VEL_X);
                if(Math.abs(entityCoordinates.getTranslX()) >= RENDERED_TILE_SIZE){
                    entityCoordinates.setTranslX(entityCoordinates.getTranslX()+RENDERED_TILE_SIZE);
                    entityCoordinates.setMapX(entityCoordinates.getMapX()-1);
                }
            }
            currentStep++;
            if (currentStep == MOVING_STEP / 2) {
                currentAnimation = WALK_ANIMATION_LEFT;
            }
            if (currentStep == MOVING_STEP) {
                currentAnimation = WALK_ANIMATION_RIGHT;
                currentStep = 0;
            }
        }else{//TODO death animation
        }
    }
}
