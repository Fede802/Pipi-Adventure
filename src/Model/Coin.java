package Model;

import Commons.Animation;
import Commons.EntityCoordinates;

public class Coin extends GameEntity{


    public Coin(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        animationList.clear();
        animationList.add(GameEntity.WALK_ANIMATION_RIGHT,new Animation("Resources/Entities/Coin/Monetina.gif"));
        animationList.add(GameEntity.WALK_ANIMATION_LEFT,null);
        animationList.add(GameEntity.DEATH_ANIMATION,new Animation("Resources/Entities/Coin/Monetona.png"));
    }

    @Override
    public void move() {
        super.move();
        //TODO check maybe isDying is necessary
        //TODO add velY to gameEntity or maybe there
        if (!isAlive){
            currentAnimation = GameEntity.DEATH_ANIMATION;
            entityCoordinates.updateTraslY(-5);
            currentDeathStep++;
            if(currentDeathStep == DEATH_STEP)
                isDying = false;
        }
    }

    @Override
    public void changeCoordinate() {

    }
}
