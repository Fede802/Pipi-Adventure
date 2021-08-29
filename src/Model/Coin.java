package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Utils.SoundManager;

import javax.swing.*;
import java.io.File;
import java.net.SocketImplFactory;
import java.util.ArrayList;
import java.util.Arrays;

public class Coin extends GameEntity{

    private static final int DEATH_STEP = 20;
    private int currentDeathStep = 0;

    //TODO add field to GameEntity
    private  SoundManager coinSound = new SoundManager("Resources/Audio/coinpickup.wav");

    public Coin(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        animationList.add(GameEntity.WALK_ANIMATION,new Animation("Resources/Entities/Coin/Monetina.gif"));
        animationList.add(GameEntity.DEATH_ANIMATION,new Animation("Resources/Entities/Coin/Monetona.png"));
    }

    @Override
    public void move() {
        if (!isAlive){
            currentAnimation = GameEntity.DEATH_ANIMATION;
            entityCoordinates.setTraslY(entityCoordinates.getTraslY()+5);
            currentDeathStep++;
            if(currentDeathStep == DEATH_STEP)
                isRemovable = true;
        }
        //TODO movement if catch
    }
}
