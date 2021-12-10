package model;

import commons.EntityCoordinates;
import commons.EntityType;
import commons.RenderingType;

public abstract class Enemy extends GameEntity {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public Enemy(RenderingType R_ID, EntityCoordinates entityCoordinates) {
        super(EntityType.ENEMY, R_ID, entityCoordinates);
        tileStep = 10.0;
        velX = RENDERING_TILE_SIZE / tileStep;
    }

}
