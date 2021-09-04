package Model;

import Commons.EntityCoordinates;
import Utils.GameConfig;

public abstract class FlyingEnemy extends GameEntity{
    private static double vel_x = RENDERED_TILE_SIZE/20.0;

    public FlyingEnemy(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
    }

    @Override
    public void move() {
        super.move();
        //TODO debug movement coords update
        vel_x = RENDERED_TILE_SIZE/20.0;
        entityCoordinates.setTraslX(entityCoordinates.getTraslX()-vel_x);
    }
}
