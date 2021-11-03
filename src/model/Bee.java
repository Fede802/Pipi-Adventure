package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;

public class Bee extends FlyingEnemy {

    private static Animation WALK_ANIMATION_LEFT;
    private static Animation DEATH_ANIMATION_LEFT;

    public Bee(EntityCoordinates entityCoordinates) {
        super(EntityType.ENEMY, entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_LEFT,WALK_ANIMATION_LEFT);
        animationList.put(GameEntity.DEATH_ANIMATION_LEFT,DEATH_ANIMATION_LEFT);
    }

    public static void load () {
        WALK_ANIMATION_LEFT = new Animation("res\\images\\entities\\enemy\\Ape\\Animazione");
        DEATH_ANIMATION_LEFT = new Animation("res\\images\\entities\\enemy\\Ape\\Morte");
    }
}
