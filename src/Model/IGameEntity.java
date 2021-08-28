package Model;

import Commons.Animation;
import Commons.EntityCoordinates;

public interface IGameEntity {
    void move();
    EntityCoordinates getEntityCoordinates();
    boolean collide(IGameEntity entity);
    Animation getAnimation();
    boolean isAlive();
    void setAlive(boolean isAlive);
    int getID();
    void update(int traslX);
    boolean isRemovable();

}
