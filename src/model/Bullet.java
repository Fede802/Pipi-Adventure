package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.EntityType;
import utils.GameDataConfig;
import view.GameOverPanel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Bullet extends GameEntity{

    private static final Animation WALK_ANIMATION_RIGHT = new Animation(new ArrayList<>(
            Arrays.asList(new File("resources/entities/Player/Guns/Nuovo_Proiettile1.png"),
                    new File("resources/entities/Player/Guns/Nuovo_Proiettile2.png"),
                    new File("resources/entities/Player/Guns/Nuovo_Proiettile3.png"),
                    new File("resources/entities/Player/Guns/Nuovo_Proiettile4.png"),
                    new File("resources/entities/Player/Guns/Nuovo_Proiettile5.png"),
                    new File("resources/entities/Player/Guns/Nuovo_Proiettile6.png"),
                    new File("resources/entities/Player/Guns/Nuovo_Proiettile7.png"),
                    new File("resources/entities/Player/Guns/Nuovo_Proiettile8.png"))
    ));

    public Bullet(EntityCoordinates entityCoordinates) {
        super(EntityType.BULLET,entityCoordinates);
        TILE_STEP = 2.5;
        walkingStep = 2*(int)(GameDataConfig.getInstance().getMapSectionSize()*TILE_STEP);
        VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
    }

    @Override
    public void move() {
        if(isAlive){
            defaultWalkMovement(GameEntity.WALK_ANIMATION_RIGHT);
            currentWalkingStep++;
            if(currentWalkingStep == walkingStep)
                updateEntityStatus();
            System.out.println("bullet debug "+ entityCoordinates.getMapX());
        }
    }

    @Override
    public void setDeathAnimation() {
        //nothing to do, no death animation at the moment
    }
}
