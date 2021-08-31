package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;

import java.util.ArrayList;

public interface IGameModel {
    void updateMap();
    int getMapTraslX();
    void setMapTraslX(int mapTraslX);
    void updateMapTraslX();
    int getTileData(int mapIndex,int mapX,int mapY);
    int getSectionSize();
    int getMapLength();
    EntityCoordinates getPlayerCoordinates();
    ArrayList<Pair<Integer,EntityCoordinates>> getPlayerBullets();
    ArrayList<Pair<Integer,EntityCoordinates>> getEntities();
    ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesCoordinates();
    void updateEntitiesStatus(int entityID);
    void updatePlayerStatus(int entityID);
    void updatePlayerMapPosition();
    boolean isPlayerJumping();
    void setPlayerJumping(boolean isPlayerJumping);
    void playerJump();
    void playerFall();
    void updateEntitiesMapPosition();
    void addCoin();
    void updateScore();
    int getScore();
    int getCoin();
    void looseLife();
    int getLife();
    boolean isPlayerDead();
    boolean isPlayerDying();


}
