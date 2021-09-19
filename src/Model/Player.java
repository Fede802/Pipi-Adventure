package model;

import commons.Animation;
import commons.EntityCoordinates;
import commons.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Player extends GameEntity implements IPlayer{
    //change to 4 8 5 10
    //TODO find a better way to use PLAYER vel x in map generator update coords
    public static double TILE_STEP = 5.0;
    public static double PLAYER_VEL_X = RENDERED_TILE_SIZE/TILE_STEP;


    private static final Animation WALK_ANIMATION_RIGHT = new Animation(new ArrayList<>(
            Arrays.asList(new File("Resources/Entities/Player/PInguino_Definitivo1.png"),
                    new File("Resources/Entities/Player/PInguino_Definitivo2.png"),
                    new File("Resources/Entities/Player/PInguino_Definitivo3.png"),
                    new File("Resources/Entities/Player/PInguino_Definitivo4.png"))
    ));
    private static final Animation DEATH_ANIMATION_RIGHT = new Animation("Resources/Entities/Player/Pinguino_Death.gif");

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean isJumping = false;

    public Player(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
        animationList.put(GameEntity.WALK_ANIMATION_RIGHT,WALK_ANIMATION_RIGHT);
        animationList.put(GameEntity.DEATH_ANIMATION_RIGHT,DEATH_ANIMATION_RIGHT);
    }

    @Override
    public void move() {
        //TODO maybe use for start movement
        if(isAlive) {
            entityCoordinates.updateTraslX(PLAYER_VEL_X);
            if (entityCoordinates.getTranslX() / RENDERED_TILE_SIZE >= MapSection.SECTION_SIZE) {
                entityCoordinates.updateTraslX(-MapSection.SECTION_SIZE * RENDERED_TILE_SIZE);
                for (int j = 0; j < bullets.size(); j++){
                    bullets.get(j).getEntityCoordinates().setMapIndex(bullets.get(j).getEntityCoordinates().getMapIndex()-1);
                }
            }
        }
        for (int j = 0; j < bullets.size(); j++){
            bullets.get(j).move();
            if(!bullets.get(j).isAlive){
                bullets.remove(j);
            }
        }
        System.out.println();


    }

    @Override
    public void jump() {
        entityCoordinates.updateTraslY(-VEL_Y);
    }

    @Override
    public void fall() {
        entityCoordinates.updateTraslY(VEL_Y);
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
        bullets.add(new Bullet(new EntityCoordinates.Builder(entityCoordinates.getSTART_MAP_X()+1,entityCoordinates.getSTART_MAP_Y(),EntityCoordinates.BULLET_ID)
                .setTraslX(entityCoordinates.getTranslX())
                .setTraslY(entityCoordinates.getTranslY())
                .build()));
    }
    @Override
    public void updateBulletStatus(int bulletID){
        bullets.get(bulletID).setAlive(false);
        bullets.remove(bulletID);
    }
    @Override
    public ArrayList<EntityCoordinates> getBulletsCoordinate(){
        ArrayList<EntityCoordinates> bulletsCoordinates = new ArrayList<>();
        for (int j = 0; j < bullets.size(); j++)
            bulletsCoordinates.add(bullets.get(j).getEntityCoordinates());
        return bulletsCoordinates;
    }
    @Override
    public ArrayList<Pair<EntityCoordinates,Animation>> getBulletsForRendering(){
        ArrayList<Pair<EntityCoordinates,Animation>> bulletsCoordinates = new ArrayList<>();
        for (int j = 0; j < bullets.size(); j++){
            bulletsCoordinates.add(new Pair<>(bullets.get(j).getEntityCoordinates(),bullets.get(j).getAnimation()));
        }
        return bulletsCoordinates;
    }

    @Override
    public Animation getAnimation() {
        return animationList.get(currentAnimation);
    }

    @Override
    public void changeCoordinate(){
        super.changeCoordinate();
        PLAYER_VEL_X = RENDERED_TILE_SIZE/TILE_STEP;
        for(int i = 0; i < bullets.size();i++){
            bullets.get(i).changeCoordinate();
        }
    }

}