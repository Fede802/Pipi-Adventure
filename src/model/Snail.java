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
    private static final Animation DEATH_ANIMATION_RIGTH = new Animation(new ArrayList<>(
            Arrays.asList(new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte1.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte2.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte3.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte4.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte5.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte6.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte7.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte8.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte9.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte10.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte11.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte12.png"),
                    new File("resources/entities/Enemy/Luma/Luma_Death/Lumachina_Morte13.png"))
    ));
    public Snail(EntityCoordinates entityCoordinates) {
        super(EntityType.ENEMY,entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
        animationList.put(GameEntity.WALK_ANIMATION_LEFT,WALK_ANIMATION_LEFT);
        animationList.put(GameEntity.DEATH_ANIMATION_RIGHT,DEATH_ANIMATION_RIGTH);
    }

}