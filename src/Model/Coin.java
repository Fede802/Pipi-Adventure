package Model;

import Commons.Animation;
import Commons.EntityCoordinates;

import java.util.ArrayList;

public class Coin extends GameEntity{

    private static final ArrayList<Animation> animationList = new ArrayList<>(){{
        add(GameEntity.WALK_ANIMATION_RIGHT,new Animation("Resources/Entities/Coin/Monetina.gif"));
        add(GameEntity.WALK_ANIMATION_LEFT,null);
        add(GameEntity.DEATH_ANIMATION,new Animation("Resources/Entities/Coin/Monetona.png"));
    }};

    public Coin(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
    }

    @Override
    public void move() {
        //TODO check maybe isDying is not necessary
        if (!isAlive && isDying){
            currentAnimation = GameEntity.DEATH_ANIMATION;
            entityCoordinates.setTraslY(entityCoordinates.getTraslY()+5);
            currentDeathStep++;
            if(currentDeathStep == DEATH_STEP)
                isDying = false;
        }
    }
    @Override
    public Animation getAnimation() {
        return animationList.get(currentAnimation);
    }
}
