package model;


import commons.EntityCoordinates;

public class PlainSection1 extends MapSection{

    public PlainSection1(){
        //super();
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
                {34,34,34,34,34,34,21,22,22,23,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,21,22,22,23,34,34,34,34,21,22,22,23,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {1,1,6,1,1,13,1,20,1,1,6,1,1,13,1,1},
                {8,8,28,8,8,8,8,8,29,8,8,8,8,30,8,8},
                {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15}
        };
    }

    @Override
    protected void spawnEntities() {

        mapEntities.add(new Coin(new EntityCoordinates.Builder(4,9, EntityCoordinates.COIN_ID).build()));
        mapEntities.add(new Coin(new EntityCoordinates.Builder(11,9,EntityCoordinates.COIN_ID).build()));

    }

}
