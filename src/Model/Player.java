package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;
import Utils.Config;
import Utils.GameConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Player extends GameEntity implements IPlayer{
    private double PLAYER_VEL_Y = RENDERED_TILE_SIZE/5.0;
    private boolean isJumping = false;


    private ArrayList<Bullet> bullets = new ArrayList<>();
    private static final ArrayList<Animation> animationList = new ArrayList<>(){{
        add(GameEntity.WALK_ANIMATION_RIGHT,new Animation(new ArrayList<>(
                Arrays.asList(new File("Resources/Entities/Player/PInguino_Definitivo1.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo2.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo3.png"),
                        new File("Resources/Entities/Player/PInguino_Definitivo4.png"))
        )));
        add(GameEntity.WALK_ANIMATION_LEFT,null);
        add(GameEntity.DEATH_ANIMATION,new Animation("Resources/Entities/Player/Pinguino_Death.gif"));
    }};

    public Player(EntityCoordinates entityCoordinates) {
        super(entityCoordinates);
    }

    @Override
    public void move() {
        super.move();

        //TODO maybe use for start movement
        if(isAlive) {
//            System.out.println("Change size from player = "+ (prevTileSize != RENDERED_TILE_SIZE));
//            if(prevTileSize != RENDERED_TILE_SIZE){
//                if(entityCoordinates.getTraslX()!=0){
////                System.out.println("MAP_VELX_prev_PLAYER= " +(prevTileSize/10));
////                System.out.println("STEP = "+entityCoordinates.getTraslX()/(prevTileSize/10));
//                entityCoordinates.setTraslX(RENDERED_TILE_SIZE/entityCoordinates.getTraslX()/(prevTileSize/10.0));
//                }
//                prevTileSize = RENDERED_TILE_SIZE;
//                System.out.print("Entered");
//            }
//            System.out.println("MAPL_VEL_X updated from player= "+MapGenerator.MAP_VEL_X);
            entityCoordinates.updateTraslX(MapGenerator.MAP_VEL_X);
//            System.out.println("traslX " + entityCoordinates.getTraslX());
            if(entityCoordinates.getTraslX()/RENDERED_TILE_SIZE == MapSection.SECTION_SIZE){
                entityCoordinates.setTraslX(0);
            }

//            if(Math.round(entityCoordinates.getTraslX()) == RENDERED_TILE_SIZE){
////                entityCoordinates.setTraslX(entityCoordinates.getTraslX()-RENDERED_TILE_SIZE);
//
//                entityCoordinates.setTraslX(0);
//                if(entityCoordinates.getMapX() == MapSection.SECTION_SIZE-1){
//                    entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()+1);
//                    entityCoordinates.setMapX(0);
//                }else{
//                    entityCoordinates.setMapX(entityCoordinates.getMapX()+1);
//                }
//                //TODO this maybe serve only for player
//                if(entityCoordinates.getMapX() == entityCoordinates.getSTART_MAP_X() && Math.round(entityCoordinates.getTraslX()) < MapGenerator.MAP_VEL_X){
//                    entityCoordinates.setMapIndex(entityCoordinates.getMapIndex()-1);
//                }
//            }
//        }
            if (!isAlive && isDying) {
                currentAnimation = GameEntity.DEATH_ANIMATION;
                entityCoordinates.setTraslY(entityCoordinates.getTraslY() - 5);
                currentDeathStep++;
//            System.out.println("entering");
//            System.out.println(!isAlive && isDying);
                if (currentDeathStep == DEATH_STEP) {
                    isDying = false;
//                System.out.println("false");
                }

            }
        }
//        System.out.print(entityCoordinates.getMapIndex()+" ");
//        System.out.print(entityCoordinates.getMapX()+" ");
//        System.out.println(entityCoordinates.getTraslX());
    }

    @Override
    public void jump() {
        //TODO parametrize traslation
        entityCoordinates.updateTraslY(-PLAYER_VEL_Y);
        System.out.println(entityCoordinates.getTraslY());
//        System.out.println("jump "+PLAYER_VEL_Y);
//        if(entityCoordinates.getTraslY() == -40){
//            entityCoordinates.setMapY(entityCoordinates.getMapY()-1);
//            entityCoordinates.setTraslY(0);
//        }
    }

    @Override
    public void fall() {
        entityCoordinates.updateTraslY(PLAYER_VEL_Y);
        System.out.println(entityCoordinates.getTraslY());
//        System.out.println();
//        System.out.println(entityCoordinates.getTraslY());
//        System.out.println("fall "+PLAYER_VEL_Y);
//        System.out.println(entityCoordinates.getTraslY());
        //TODO maybe = 0 and traslY = renderedTilesize? btw has to be refactor
//        if(entityCoordinates.getTraslY() == PLAYER_VEL_Y){
//            entityCoordinates.setMapY(entityCoordinates.getMapY()+1);
//            entityCoordinates.setTraslY(-35);
//        }
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
//        bullets.add(new Bullet(new EntityCoordinates.Builder(entityCoordinates.getSTART_MAP_X(),entityCoordinates.getMapY(),GameEntity.BULLET_ID)
//                .setMapIndex(entityCoordinates.getMapIndex())
//                .setMapX(entityCoordinates.getMapX()).build()));
    }

    @Override
    public ArrayList<Pair<Integer, EntityCoordinates>> getBullets() {
        ArrayList<Pair<Integer, EntityCoordinates>> bullets = new ArrayList<>();
        for(int i = 0; i < this.bullets.size(); i++){
//            this.bullets.get(i).setID(i);
            bullets.add(new Pair<>(i,this.bullets.get(i).getEntityCoordinates()));
        }
        return bullets;
    }

    public Animation getBulletAnimation(final int bulletIndex){
        return bullets.get(bulletIndex).getAnimation();
    }

    @Override
    public Animation getAnimation() {
        return animationList.get(currentAnimation);
    }


    public boolean isDead(){
        return !isAlive && !isDying;
    }
    @Override
    public void changeCoordinate(){
        entityCoordinates.setHeight(GameConfig.getInstance().getRenderedTileSize());
        entityCoordinates.setWidth(GameConfig.getInstance().getRenderedTileSize());
        entityCoordinates.setTraslX(entityCoordinates.getTraslX()/MapGenerator.MAP_VEL_X*(GameConfig.getInstance().getRenderedTileSize()/5.0));
        entityCoordinates.setTraslY(entityCoordinates.getTraslY()/PLAYER_VEL_Y*(GameConfig.getInstance().getRenderedTileSize()/5.0));
        PLAYER_VEL_Y = GameConfig.getInstance().getRenderedTileSize()/5.0;

    }

}
