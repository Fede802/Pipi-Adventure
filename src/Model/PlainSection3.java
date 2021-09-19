package model;

import commons.EntityCoordinates;

public class PlainSection3 extends MapSection{

    public PlainSection3(){
        //super();
        map = new int[][]{
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,21,22,23,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34},
                {34,34,34,34,34,34,34,0,1,2,34,34,34,34,34,34},
                {34,34,34,34,34,34,0,8,8,8,2,34,34,34,34,34},
                {1,20,1,20,1,1,8,8,8,8,8,1,13,1,6,1},
                {8,8,32,8,8,8,8,8,28,8,8,8,30,8,8,8},
                {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15}
        };
    }

    @Override
    protected void spawnEntities() {
        mapEntities.add(new Coin(new EntityCoordinates.Builder(3,12, EntityCoordinates.COIN_ID).build()));
        mapEntities.add(new Coin(new EntityCoordinates.Builder(8,9,EntityCoordinates.COIN_ID).build()));
        mapEntities.add(new Coin(new EntityCoordinates.Builder(13,12,EntityCoordinates.COIN_ID).build()));

    }
}

