package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Controller.GameEngine;

import java.util.ArrayList;

public abstract class GameEntity implements IGameEntity{
    public static final int WALK_ANIMATION = 0;
    public static final int DEATH_ANIMATION = 1;
    public static final int WALK_ANIMATION_RIGHT = 2;
    public static final int PLAYER_ID = 0;
    public static final int ENEMY_ID = 1;
    public static final int COIN_ID = 2;
    public static final int BULLET_ID = 3;

    protected EntityCoordinates entityCoordinates;
    protected boolean isAlive = true;
    protected boolean isRemovable = false;
    protected int ID;
    protected ArrayList<Animation> animationList = new ArrayList<>();
    protected int currentAnimation = WALK_ANIMATION;

    public GameEntity(EntityCoordinates entityCoordinates){
        this.ID = entityCoordinates.getID();
        this.entityCoordinates = entityCoordinates;
    }

    @Override
    public EntityCoordinates getEntityCoordinates() {
        return entityCoordinates;
    }

    @Override
    public boolean collide(IGameEntity entity) {
        boolean collide = false;
        if(this.entityCoordinates.getMapIndex() == entity.getEntityCoordinates().getMapIndex() && this.entityCoordinates.getMapY() == entity.getEntityCoordinates().getMapY() && this.entityCoordinates.getMapX() == entity.getEntityCoordinates().getMapX()){
            collide = true;
        }
        //TODO entity collision
        return collide;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    @Override
    public int getID() {
        return this.ID;
    }
    @Override
    public void update(int traslX){
        //maybe trasl < 0
        //TODO use move
        entityCoordinates.setTraslX(traslX);
        if(entityCoordinates.getTraslX() >= 40){
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()-40);
            if(entityCoordinates.getMapX() == MapSection.SECTION_SIZE-1){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
                entityCoordinates.setMapX(0);
            }else{
                entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
            }
            //TODO this maybe serve only for player
            if(entityCoordinates.getMapX() == 2 && entityCoordinates.getTraslX() == 0){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()-1);
            }
        }
    }
    @Override
    public boolean isRemovable(){
        return isRemovable;
    }
    @Override
    public Animation getAnimation(){
        return animationList.get(currentAnimation);
    }

    /*public static class Builder{
        private final int START_MAP_X;
        private final int START_MAP_Y;

        private int mapIndex;

        private int mapX;
        private int traslX;

        private int mapY;
        private int traslY;

        public Builder(final int ID, final int mapIndex, final int START_MAP_X, final int START_MAP_Y){
            this.mapIndex = mapIndex;
            this.START_MAP_X = START_MAP_X;
            this.START_MAP_Y = START_MAP_Y;
            this.mapX = START_MAP_X;
            this.traslX = 0;
            this.mapY = START_MAP_Y;
            this.traslY = 0;
        }

        public GameEntity build(){
            EntityCoordinates entityCoordinates = new EntityCoordinates.Builder(mapIndex,START_MAP_X,START_MAP_Y).setTraslX(traslX).setTraslY(traslY).build();
            GameEntity gameEntity = new GameEntity()
            return GameEntity;
        }

        public Builder setTraslX(int traslX) {
            this.traslX = traslX;
            return this;
        }

        public Builder setTraslY(int traslY) {
            this.traslY = traslY;
            return this;
        }

    }*/
}
