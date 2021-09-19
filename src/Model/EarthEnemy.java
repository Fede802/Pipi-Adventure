package model;

import commons.EntityCoordinates;

public class EarthEnemy extends GameEntity{
    private static final int MOVING_STEP = 96;
    private int currentStep = 0;

    public EarthEnemy(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        TILE_STEP = 10.0;
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
    }

    @Override
    public void move() {
        //TODO debug movement coords update
        if(isAlive) {
            if (currentStep < MOVING_STEP / 2) {
                entityCoordinates.updateTraslX(VEL_X);
            } else {
                entityCoordinates.updateTraslX(-VEL_X);
            }
            currentStep++;
            if (currentStep == MOVING_STEP / 2) {
                currentAnimation = WALK_ANIMATION_LEFT;
            }
            if (currentStep == MOVING_STEP) {
                currentAnimation = WALK_ANIMATION_RIGHT;
                currentStep = 0;
            }
        }else{

        }
    }
}