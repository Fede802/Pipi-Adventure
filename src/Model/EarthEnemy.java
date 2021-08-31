package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Utils.Config;

public abstract class EarthEnemy extends GameEntity{

    private static final int MOVING_STEP = 80;
    private static final int VEL_X = 2;
    private int currentStep = 0;

    public EarthEnemy(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
    }

    @Override
    public void move() {

        //TODO debug movement coords update

        if (currentStep < MOVING_STEP/2){
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()+VEL_X);
        }else{
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()-VEL_X);
        }
        if(entityCoordinates.getTraslX() >= RENDERED_TILE_SIZE){
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()-RENDERED_TILE_SIZE);
            entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
        }
        if(entityCoordinates.getTraslX() == -VEL_X){
            entityCoordinates.setTraslX(RENDERED_TILE_SIZE-VEL_X);
            entityCoordinates.setMapX(entityCoordinates.getMapX()-1);
        }
        currentStep++;
        if (currentStep == MOVING_STEP/2){
            currentAnimation = WALK_ANIMATION_LEFT;
        }
        if (currentStep == MOVING_STEP){
            currentAnimation = WALK_ANIMATION_RIGHT;
            currentStep = 0;
        }
    }


}
