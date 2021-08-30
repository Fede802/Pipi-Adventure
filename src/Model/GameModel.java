package Model;

import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pair;

import java.util.ArrayList;

public class GameModel implements IGameModel{

    private static final MapGenerator MAP_GENERATOR = new MapGenerator();
    private static final GameStatus GAME_STATUS = new GameStatus();
    private static final Player PLAYER = new Player(new EntityCoordinates.Builder(2,12,GameEntity.PLAYER_ID)
                                                    .setMapIndex(0)
                                                    .build());

    private static GameModel instance = null;
    private GameModel(){}
    public static IGameModel getInstance() {
        if (instance == null){
            instance = new GameModel();
        }
        return instance;
    }

    @Override
    public void updateMap() {
        MAP_GENERATOR.updateMap();
    }

    @Override
    public int getMapTraslX() {
        return MAP_GENERATOR.getMapTraslX();
    }

    @Override
    public void setMapTraslX(final int mapTraslX) {
        MAP_GENERATOR.setMapTraslX(mapTraslX);
    }

    @Override
    public void updateMapTraslX() {
        MAP_GENERATOR.updateMapTraslX();
    }

    @Override
    public int getTileData(final int mapIndex, final int mapX, final int mapY) {
        return MAP_GENERATOR.getTileData(mapIndex,mapX,mapY);
    }

    @Override
    public int getSectionSize() {
        return MapSection.SECTION_SIZE;
    }

    @Override
    public int getMapLength() {
        return MapGenerator.MAP_LENGHT;
    }

    @Override
    public EntityCoordinates getPlayerCoordinates() {
        return PLAYER.getEntityCoordinates();
    }

    @Override
    public ArrayList<Pair<Integer,EntityCoordinates>> getPlayerBullets() {
        return PLAYER.getBullets();
    }

    @Override
    public ArrayList<Pair<Integer, EntityCoordinates>> getEntities() {
        return MAP_GENERATOR.getEntities();
    }

    @Override
    public ArrayList<Pair<EntityCoordinates, Animation>> getEntitiesCoordinates() {
        ArrayList<Pair<EntityCoordinates, Animation>> entitiesCoordinates = MAP_GENERATOR.getMapEntitiesCoordinates();
        entitiesCoordinates.add(new Pair<>(PLAYER.getEntityCoordinates(), PLAYER.getAnimation()));
        for(int i = 0; i < PLAYER.getBullets().size();i++)
            entitiesCoordinates.add(new Pair<>(PLAYER.getBullets().get(i).getValue(), PLAYER.getBulletAnimation(i)));
        return entitiesCoordinates;
    }

    @Override
    public void updateEntitiesStatus(final int entityID) {
        MAP_GENERATOR.updateEntitiesStatus(entityID);
    }

    @Override
    public void updatePlayerStatus(int entityID) {

    }

    @Override
    public void updatePlayerMapPosition() {
        PLAYER.move();
    }

    @Override
    public boolean isPlayerJumping() {
        return PLAYER.isJumping();
    }

    @Override
    public void setPlayerJumping(boolean isPlayerJumping) {
        PLAYER.setJumping(isPlayerJumping);
    }

    @Override
    public void playerJump() {
        PLAYER.jump();
    }

    @Override
    public void playerFall() {
        PLAYER.fall();
    }

    @Override
    public void updateEntitiesMapPosition() {
        MAP_GENERATOR.updateEntitiesMapPosition();
    }

    @Override
    public void addCoin() {
        GAME_STATUS.addCoin();
    }

    @Override
    public void updateScore() {
        GAME_STATUS.updateScore();
    }

    @Override
    public int getScore() {
        return GAME_STATUS.getScore();
    }

    @Override
    public int getCoin() {
        return GAME_STATUS.getCoin();
    }

    @Override
    public void looseLife() {
        GAME_STATUS.looseLife();
    }

    @Override
    public int getLife() {
        return GAME_STATUS.getLife();
    }
}
