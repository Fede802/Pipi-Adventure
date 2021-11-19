package model;

import commons.*;
import utils.GameDataConfig;

public class Bullet extends GameEntity{

    public Bullet(EntityCoordinates entityCoordinates) {
        super(EntityType.BULLET, RenderingType.BULLET, entityCoordinates);
        TILE_STEP = 2.5;
        walkingStep = 2*(int)(GameDataConfig.getInstance().getMapSectionSize()*TILE_STEP);
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;

    }

    @Override
    public void move() {
        if(entityStatus == EntityStatus.ALIVE){
            defaultWalkMovement(commons.AnimationData.WALK_ANIMATION_RIGHT);
            currentWalkingStep++;
            if(currentWalkingStep == walkingStep)
                updateEntityStatus();
        }
    }

    @Override
    public void setDeathAnimation() {
        //nothing to do, no death animation at the moment
    }
}