package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;

public class Slime extends EarthEnemy{
    private static Animation WALK_ANIMATION_RIGHT;
    private static Animation WALK_ANIMATION_LEFT;
    private static Animation DEATH_ANIMATION_RIGTH;
    private static Animation DEATH_ANIMATION_LEFT;

    public Slime(EntityCoordinates entityCoordinates) {
        super(EntityType.ENEMY, entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT, WALK_ANIMATION_RIGHT);
        animationList.put(GameEntity.WALK_ANIMATION_LEFT, WALK_ANIMATION_LEFT);
        animationList.put(GameEntity.DEATH_ANIMATION_RIGHT, DEATH_ANIMATION_RIGTH);
        animationList.put(GameEntity.DEATH_ANIMATION_LEFT, DEATH_ANIMATION_LEFT);
    }

    public static void load() {
        WALK_ANIMATION_RIGHT = new Animation("res\\images\\entities\\enemy\\Slime\\Animazione\\Slime_Walk_R");
        WALK_ANIMATION_LEFT = new Animation("res\\images\\entities\\enemy\\Slime\\Animazione\\Slime_Walk_L");
        DEATH_ANIMATION_RIGTH = new Animation("res\\images\\entities\\enemy\\Slime\\Morte\\Slime_Death_R");
        DEATH_ANIMATION_LEFT = new Animation("res\\images\\entities\\enemy\\Slime\\Morte\\Slime_Death_L");

    }
}
