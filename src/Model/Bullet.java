package Model;

import Commons.Animation;
import Commons.EntityCoordinates;

import java.util.ArrayList;

public class Bullet extends GameEntity{
    private static final ArrayList<Animation> animationList = new ArrayList<>(){{
        add(GameEntity.WALK_ANIMATION_RIGHT,new Animation("Resources/Entities/Player/Guns/Proiettile2.gif"));

    }};
    public Bullet(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
    }

    @Override
    public void move() {
        //TODO movement logic
    }
    @Override
    public Animation getAnimation() {
        return animationList.get(currentAnimation);
    }
}
