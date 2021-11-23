package model;

import commons.EntityCoordinates;
import commons.EntityType;
import commons.RenderingType;

public abstract class Enemy extends GameEntity{

    public Enemy(RenderingType R_ID, EntityCoordinates entityCoordinates) {
        super(EntityType.ENEMY, R_ID, entityCoordinates);
        TILE_STEP = 10.0;
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
    }

}
