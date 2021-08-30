package Model;

import Commons.EntityCoordinates;

public class PlainSection1 extends MapSection{

    public PlainSection1(){
        //super();

        //mapEntities.put(GameEntity.COIN_ID,new Coin(GameEntity.COIN_ID,new EntityCoordinates.Builder(1,12).build()));
        map = new int[][]{
                //TODO later, in all section refactor tile list
                /*
                     1 normal grass
                     6 worm grass
                     13 pink flower grass
                     20 red flower grass
                     -----
                     8 dirt
                     28 worm dirt
                     29 skull dirt
                     30 bone dirt
                     31 dead fish dirt
                     32 helix dirt
                     ------
                     15 normal base
                */
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,21,23,34,34},
                {34,34,34,34,34,34,34,34,21,23,34,34,34,34,34,34},
                {34,34,34,0,1,2,34,34,34,34,34,34,34,34,34,34},
                {1,1,1,8,8,8,1,1,1,1,1,1,1,1,1,1},
                {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
                {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15}
        };
    }

    @Override
    protected void spawnEntities() {
        mapEntities.add(new Coin(new EntityCoordinates.Builder(1,12,GameEntity.COIN_ID).build()));
        mapEntities.add(new Coin(new EntityCoordinates.Builder(4,11,GameEntity.COIN_ID).build()));
        mapEntities.add(new Coin(new EntityCoordinates.Builder(10,9,GameEntity.COIN_ID).build()));
    }

}
