package Model;

import Commons.EntityCoordinates;
import Commons.Pair;

import java.util.ArrayList;

public interface IPlayer {
    void jump();
    void fall();
    boolean isJumping();
    void setJumping(boolean isJumping);
    void shoot();
    ArrayList<Pair<Integer, EntityCoordinates>> getBullets();
}
