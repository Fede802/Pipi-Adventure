package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;
import Utils.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Player extends GameEntity implements IPlayer{
    private static final int PLAYER_VEL_Y = 5;
    private boolean isJumping = false;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Player(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        animationList.add(GameEntity.WALK_ANIMATION_RIGHT,new Animation(new ArrayList<>(
                Arrays.asList(new File("Resources/Entities/Player/PInguino_Definitivo1.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo2.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo3.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo4.png"))
        )));
    }

    @Override
    public void move() {
        //TODO maybe use for start movement
        entityCoordinates.updateTraslX(MapGenerator.MAP_VEL_X);
        if(entityCoordinates.getTraslX() >= RENDERED_TILE_SIZE){
            entityCoordinates.setTraslX(entityCoordinates.getTraslX()-RENDERED_TILE_SIZE);
            if(entityCoordinates.getMapX() == MapSection.SECTION_SIZE-1){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
                entityCoordinates.setMapX(0);
            }else{
                entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
            }
            //TODO this maybe serve only for player
            if(entityCoordinates.getMapX() == entityCoordinates.getSTART_MAP_X() && entityCoordinates.getTraslX() == 0){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()-1);
            }
        }
    }

    @Override
    public void jump() {
        //TODO parametrize traslation
        entityCoordinates.setTraslY(entityCoordinates.getTraslY()-PLAYER_VEL_Y);
        if(entityCoordinates.getTraslY() == -40){
            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
            entityCoordinates.setTraslY(0);
        }
    }

    @Override
    public void fall() {
        entityCoordinates.setTraslY(entityCoordinates.getTraslY()+PLAYER_VEL_Y);
        //TODO maybe = 0 and traslY = renderedTilesize? btw has to be refactor
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
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    @Override
    public void shoot() {
        bullets.add(new Bullet(new EntityCoordinates.Builder(entityCoordinates.getSTART_MAP_X(),entityCoordinates.getMapY(),GameEntity.BULLET_ID)
                .setMapIndex(entityCoordinates.getMapIndex())
                .setMapX(entityCoordinates.getMapX()).build()));
    }

    @Override
    public ArrayList<Pair<Integer, EntityCoordinates>> getBullets() {
        ArrayList<Pair<Integer, EntityCoordinates>> bullets = new ArrayList<>();
        for(int i = 0; i < this.bullets.size(); i++){
            this.bullets.get(i).setID(i);
            bullets.add(new Pair<>(i,this.bullets.get(i).getEntityCoordinates()));
        }
        return bullets;
    }

    public Animation getBulletAnimation(final int bulletIndex){
        return bullets.get(bulletIndex).getAnimation();
    }

}
