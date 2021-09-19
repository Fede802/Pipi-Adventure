package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;

import java.util.ArrayList;

public interface IGameModel {
    void updateMap();
    double getMapTraslX();
    void setMapTraslX(double mapTraslX);
    void updateMapTraslX();
    int getTileData(int mapIndex,int mapX,int mapY);
    int getSectionSize();
    int getMapLength();

    boolean isPlayerJumping();
    void setPlayerJumping(boolean isPlayerJumping);
    void playerJump();
    void playerFall();
    void shoot();

    EntityCoordinates getPlayerCoordinates();
    ArrayList<EntityCoordinates> getBullets();
    ArrayList<EntityCoordinates> getEntitiesCoordinates();
    ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesForRendering();

    //TODO merge methods
    void updatePlayerMapPosition();
    void updateEntitiesMapPosition();

    void updateEntitiesStatus(int entityID,int parent);

    void changeCoordinate();

    void printPlayerInfo();

    void setup();
}