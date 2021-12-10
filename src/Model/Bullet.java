package model;

import commons.*;
import utils.GameDataConfig;

public class Bullet extends GameEntity {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public Bullet(EntityCoordinates entityCoordinates) {
        super(EntityType.BULLET, RenderingType.BULLET, entityCoordinates);
        tileStep = 2.5;
        walkingStep = 2*(int)(MapSection.SECTION_SIZE* tileStep);
        velX = RENDERING_TILE_SIZE / tileStep;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void move() {
        if(entityStatus == EntityStatus.ALIVE){
            defaultWalkMovement(RIGHT_DIR);
            currentWalkingStep++;
            if(currentWalkingStep == walkingStep)
                updateEntityStatus();
        }
    }

    @Override
    public void setDeathAnimation() {
        //nothing to do, no death animation at the moment
    }

    @Override
    public void resetEntity() {
        //nothing to do
    }

}
