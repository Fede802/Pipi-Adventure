package Model;

import Commons.Animation;
import Commons.EntityCoordinates;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Player extends GameEntity implements IPlayer{
    private static final int PLAYER_VEL_Y = 5;
    private boolean isJumping = false;

    public Player(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        animationList.add(GameEntity.WALK_ANIMATION,new Animation(new ArrayList<>(
                Arrays.asList(new File("Resources/Entities/Player/PInguino_Definitivo1.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo2.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo3.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo4.png"))
        )));
    }

    @Override
    public void move() {
        //TODO maybe usefull for start o death
    }

    @Override
    public void jump() {
        entityCoordinates.setTraslY(entityCoordinates.getTraslY()-PLAYER_VEL_Y);
        if(entityCoordinates.getTraslY() == -40){
            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
            entityCoordinates.setTraslY(0);
        }

    }

    @Override
    public void fall() {
        entityCoordinates.setTraslY(entityCoordinates.getTraslY()+PLAYER_VEL_Y);

        if(entityCoordinates.getTraslY() == PLAYER_VEL_Y){
            entityCoordinates.setMapY(entityCoordinates.getMapY()+1);
            entityCoordinates.setTraslY(-35);
        }
    }

    @Override
    public boolean isJumping() {
        return isJumping;
    }

    @Override
    public void setJumping(boolean jumping) {
        this.isJumping = jumping;
    }
}
