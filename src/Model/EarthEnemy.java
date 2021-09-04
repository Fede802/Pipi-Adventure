package Model;

import Commons.EntityCoordinates;
import Utils.GameConfig;

public abstract class EarthEnemy extends GameEntity{

    private static final int MOVING_STEP = 80;
    private static double vel_x = RENDERED_TILE_SIZE/20.0;
    private int currentStep = 0;

    public EarthEnemy(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
    }

    @Override
    public void move() {
        super.move();
        //TODO debug movement coords update
        vel_x = RENDERED_TILE_SIZE/20.0;
        if (currentStep < MOVING_STEP/2){
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()+vel_x);
        }else{
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()-vel_x);
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
    @Override
    public void changeCoordinate() {
        entityCoordinates.setWidth(GameConfig.getInstance().getRenderedTileSize());
        entityCoordinates.setHeight(GameConfig.getInstance().getRenderedTileSize());
        entityCoordinates.setTraslX(entityCoordinates.getTraslX() / vel_x * (GameConfig.getInstance().getRenderedTileSize()/20.0));
        vel_x = GameConfig.getInstance().getRenderedTileSize() / 20.0;
    }


}

