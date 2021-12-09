package model;

import commons.EntityCoordinates;

public class PlainSection3 extends MapSection {

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public PlainSection3(){
        map = new int[][]{
                /*
                     0 start grass
                     1 center grass
                     2 end grass
                     6 worm grass
                     -----
                     8 dirt
                     13 pink flower grass
                     -----
                     15 base dirt
                     20 red flower grass
                     -----
                     21 start platform
                     22 center platform
                     23 end platform
                     -----
                     28 worm dirt
                     29 skull dirt
                     30 bone dirt
                     31 dead fish dirt
                     32 helix dirt
                     34 null tile

                */
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

    //    --------------------------------------------------------
    //                      INSTANCE METHOD
    //    --------------------------------------------------------

    @Override
    protected void spawnEntities(int daytime) {
        MAP_ENTITIES.add(new Coin(new EntityCoordinates.Builder(3,12).build()));
        MAP_ENTITIES.add(new Coin(new EntityCoordinates.Builder(8,9).build()));
        MAP_ENTITIES.add(new Coin(new EntityCoordinates.Builder(13,12).build()));
    }

}