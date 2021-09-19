package model;

import commons.Animation;
import commons.EntityCoordinates;

public class Coin extends GameEntity{

    private static final Animation WALK_ANIMATION_RIGHT = new Animation("Resources/Entities/Coin/Monetina.gif");
    private static final Animation DEATH_ANIMATION_RIGHT = new Animation("Resources/Entities/Coin/Monetona.png");

    public Coin(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
        animationList.put(GameEntity.DEATH_ANIMATION_RIGHT,DEATH_ANIMATION_RIGHT);
    }

    @Override
    public void move() {
        if (!isAlive){
            currentAnimation = GameEntity.DEATH_ANIMATION_RIGHT;
            entityCoordinates.updateTraslY(-VEL_Y);
            currentDeathStep++;
            if(currentDeathStep == deathStep)
                isDying = false;
        }
    }

}
