package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import View.MapDrawer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Slime extends GameEntity{
    private static final int MOVING_STEP = 80;
    private int currentStep = 0;
    public Slime(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        animationList.add(WALK_ANIMATION,new Animation(new ArrayList<>(
                Arrays.asList(new File("Resources/Entities/Enemy/Luma/Luma_1d.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_2d.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_3d.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_4d.png"))
        )));
        animationList.add(DEATH_ANIMATION,new Animation("Resources/Entities/Coin/Monetona.png"));
        animationList.add(WALK_ANIMATION_RIGHT,new Animation(new ArrayList<>(
                Arrays.asList(new File("Resources/Entities/Enemy/Luma/Luma_1.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_2.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_3.png"),
                        new File("Resources/Entities/Enemy/Luma/Luma_4.png"))
        )));


    }

    @Override
    public void move() {
        if (currentStep < MOVING_STEP/2){
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()+2);
        }else{
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()-2);
        }
        System.out.println(entityCoordinates.getTraslX());
        currentStep++;
        if (currentStep == MOVING_STEP/2)
            currentAnimation = WALK_ANIMATION_RIGHT;
        if (currentStep == MOVING_STEP){
            currentAnimation = WALK_ANIMATION;
            currentStep = 0;
        }
    }
}
