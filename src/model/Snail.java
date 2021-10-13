package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Snail extends EarthEnemy{
    private static final Animation WALK_ANIMATION_RIGHT = new Animation(new ArrayList<>(
            Arrays.asList(new File("Resources/Entities/Enemy/Luma/Luma_1d.png"),
                    new File("Resources/Entities/Enemy/Luma/Luma_2d.png"),
                    new File("Resources/Entities/Enemy/Luma/Luma_3d.png"),
                    new File("Resources/Entities/Enemy/Luma/Luma_4d.png"))
    ));
    private static final Animation WALK_ANIMATION_LEFT = new Animation(new ArrayList<>(
            Arrays.asList(new File("Resources/Entities/Enemy/Luma/Luma_1.png"),
                    new File("Resources/Entities/Enemy/Luma/Luma_2.png"),
                    new File("Resources/Entities/Enemy/Luma/Luma_3.png"),
                    new File("Resources/Entities/Enemy/Luma/Luma_4.png"))
    ));
    private static final Animation DEATH_ANIMATION_LEFT = new Animation("Resources/Entities/Enemy/Luma/Lumachina_Morte.gif");
    public Snail(EntityCoordinates entityCoordinates) {
        super(EntityType.ENEMY,entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
        animationList.put(GameEntity.WALK_ANIMATION_LEFT,WALK_ANIMATION_LEFT);
        animationList.put(GameEntity.DEATH_ANIMATION_LEFT,DEATH_ANIMATION_LEFT);
    }

    @Override
    public void move() {
        super.move();
        if (!isAlive){
            //TODO why animation isn't shown
            currentAnimation = GameEntity.DEATH_ANIMATION_LEFT;
            currentDeathStep++;
            if(currentDeathStep == DEFAULT_DEATH_STEP)
                isDying = false;
        }
    }
}
