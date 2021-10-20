package model;

import commons.*;
import utils.GameDataConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Player extends GameEntity implements IPlayer{
    //change to 4 8 5 10
    //TODO find a better way to use PLAYER vel x in map generator update coords
    public static double TILE_STEP =5 ;
    public static double PLAYER_VEL_X = RENDERED_TILE_SIZE/TILE_STEP;


    private static final Animation DEATH_ANIMATION_RIGHT = new Animation(new ArrayList<>(
            Arrays.asList(new File("resources/entities/Player/Death/Pinguino_Death1.png"),
                    new File("resources/entities/Player/Death/Pinguino_Death2.png"),
                    new File("resources/entities/Player/Death/Pinguino_Death3.png"),
                    new File("resources/entities/Player/Death/Pinguino_Death4.png"),
                    new File("resources/entities/Player/Death/Pinguino_Death5.png"),
                    new File("resources/entities/Player/Death/Pinguino_Death6.png"),
                    new File("resources/entities/Player/Death/Pinguino_Death7.png"),
                    new File("resources/entities/Player/Death/Pinguino_Death8.png")
            )
    ));
    private static final Animation WALK_ANIMATION_RIGHT = new Animation(new ArrayList<>(
            Arrays.asList(new File("Resources/Entities/Player/PInguino_Definitivo1.png"),
                    new File("Resources/Entities/Player/PInguino_Definitivo2.png"),
                    new File("Resources/Entities/Player/PInguino_Definitivo3.png"),
                    new File("Resources/Entities/Player/PInguino_Definitivo4.png"))
    ));


    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean isJumping = false;

    public Player(EntityCoordinates entityCoordinates) {
        super(EntityType.PLAYER,entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
        animationList.put(GameEntity.DEATH_ANIMATION_RIGHT,DEATH_ANIMATION_RIGHT);
    }

    @Override
    public void setAlive(boolean isAlive) {
        super.setAlive(isAlive);
        isDying = !isAlive;
        if(isDying)
            currentAnimation = GameEntity.DEATH_ANIMATION_RIGHT;
    }

    @Override
    public void move() {
        //TODO maybe use for start movement
        if(isAlive) {
            entityCoordinates.updateTraslX(PLAYER_VEL_X);
            if(entityCoordinates.getTranslX() >= RENDERED_TILE_SIZE){
                entityCoordinates.setTranslX(entityCoordinates.getTranslX()-RENDERED_TILE_SIZE);
                entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
            }
            if(entityCoordinates.getMapX() == GameDataConfig.getInstance().getMapSectionSize()){
                entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
                entityCoordinates.setMapX(0);
            }
        }
    }

    public void moveBullet(){
        for(int i = bullets.size()-1 ; i >=0; i--) {
            if (!bullets.get(i).isAlive() && !bullets.get(i).isDying())
                bullets.remove(i);
            else
                bullets.get(i).move();
        }
    }

    @Override
    public void jump() {
        entityCoordinates.updateTraslY(-VEL_Y);
        if(Math.abs(entityCoordinates.getTranslY()) ==  RENDERED_TILE_SIZE) {
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
        }
    }
    void updateBulletsIndex(){
        for(int i = 0 ; i < bullets.size(); i++){
            bullets.get(i).getEntityCoordinates().setMapIndex(
                    bullets.get(i).getEntityCoordinates().getMapIndex()-1
            );
        }
    }
    @Override
    public void fall() {
        entityCoordinates.updateTraslY(VEL_Y);
        if(entityCoordinates.getTranslY() == RENDERED_TILE_SIZE){
            entityCoordinates.setTranslY(0);
            entityCoordinates.setMapY(entityCoordinates.getMapY()+1);
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
        bullets.add(new Bullet(new EntityCoordinates.Builder(entityCoordinates.getMapX()+1,entityCoordinates.getMapY())
                .setStartMapIndex(entityCoordinates.getMapIndex())
                .setTranslX(entityCoordinates.getTranslX())
                .setTranslY(entityCoordinates.getTranslY())
                .build()));
    }
    @Override
    public void updateBulletStatus(int bulletID){
        bullets.get(bulletID).setAlive(false);
        //todo
//        bullets.remove(bulletID);
    }
    @Override
    public EntityCoordinates getBulletCoordinate(int bulletID, EntityStatus entityStatus){
        int count = 0;
        if(entityStatus == EntityStatus.ALIVE)
        for(int i = 0; i < bullets.size();i++){
            if(bullets.get(i).isAlive){
                if(count == bulletID){
                    count = i;
                    i = bullets.size();
                }else{
                    count++;
                }
            }
        }
        else{
            count = bulletID;
        }
       return bullets.get(count).getEntityCoordinates();
    }


    @Override
    public Animation getAnimation() {
        return animationList.get(currentAnimation);
    }

    public Animation getBulletAnimation(int entityID){
        return bullets.get(entityID).getAnimation();
    }

    @Override
    public void changeCoordinate(){
        super.changeCoordinate();
        PLAYER_VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
        for(int i = 0; i < bullets.size();i++){
            bullets.get(i).changeCoordinate();
        }
    }

    public int bulletCount() {
        return bullets.size();
    }

    public boolean isBulletDead(int entityID) {
        return bullets.get(entityID).isDead();
    }

    public void setup() {
        getAnimation().resetAnimation();
        currentAnimation = GameEntity.WALK_ANIMATION_RIGHT;
        setAlive(true);
    }
}
