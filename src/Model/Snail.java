package Model;

import Commons.Animation;
import Commons.EntityCoordinates;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Snail extends EarthEnemy{

    private static final ArrayList<Animation> animationList = new ArrayList<>(){{
        add(WALK_ANIMATION_RIGHT,new Animation(new ArrayList<>(
                Arrays.asList(new File("Resources/Entities/Enemy/Luma/Luma_1d.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_2d.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_3d.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_4d.png"))
        )));
        add(WALK_ANIMATION_LEFT,new Animation(new ArrayList<>(
                Arrays.asList(new File("Resources/Entities/Enemy/Luma/Luma_1.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_2.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_3.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_4.png"))
        )));
    }};
    public Snail(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
    }

    @Override
    public void move() {
        super.move();
        if (!isAlive && isDying){
//            currentAnimation = GameEntity.DEATH_ANIMATION;
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
