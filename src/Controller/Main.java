package Controller;

import Model.MapGenerator;

public class Main {
    public static void main(String[] args) {

        MapGenerator mapGenerator = new MapGenerator();

        for (int i =0; i<100; i++){
            mapGenerator.updateMap();
        }
    }
}
