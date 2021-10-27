package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;

public class Coin extends GameEntity{

    private static final Animation WALK_ANIMATION_RIGHT = new Animation("Resources/Entities/Coin/Monetina.gif");
    private static final Animation DEATH_ANIMATION_RIGHT = new Animation("Resources/Entities/Coin/Monetona.png");

    public Coin(EntityCoordinates entityCoordinates) {
        super(EntityType.COIN,entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
        deathLoop = 10;
        animationList.put(GameEntity.DEATH_ANIMATION_RIGHT,DEATH_ANIMATION_RIGHT);
    }

    @Override
    public void move() {
        if(!isAlive)
            entityCoordinates.updateTraslY(-VEL_Y);
    }

    @Override
    public void setDeathAnimation() {
        currentAnimation = GameEntity.DEATH_ANIMATION_RIGHT;
    }

}