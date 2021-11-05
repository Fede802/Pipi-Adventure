package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;

public class Coin extends GameEntity{

    private static Animation WALK_ANIMATION_RIGHT;
    private static Animation DEATH_ANIMATION_RIGHT;

    public Coin(EntityCoordinates entityCoordinates) {
        super(EntityType.COIN,entityCoordinates);
//        System.out.println("create coin \n\n\n\n\n\n\n\n\n\n\n");
//        if(WALK_ANIMATION_RIGHT == null)
//            System.out.println("null anui");
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
        animationList.put(GameEntity.DEATH_ANIMATION_RIGHT,DEATH_ANIMATION_RIGHT);
        deathLoop = 5;

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

    public static void load(){
        WALK_ANIMATION_RIGHT = new Animation("res\\images\\entities\\coin\\Moneta.gif");
        DEATH_ANIMATION_RIGHT = new Animation("res\\images\\entities\\coin\\Moneta_img.png");
        System.out.println("load animation coin");
    }

    @Override
    public boolean isDead() {
        return super.isDead();
    }
}