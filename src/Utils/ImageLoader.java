package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    public final int NUM_ROWS_OF_TILESET = GameDataConfig.getInstance().getDefaultNumRowsOfTileset();
    public final int NUM_COLUMNS_OF_TILESET = GameDataConfig.getInstance().getDefaultNumColumnsOfTileset();
    public final int TILE_SIZE = GameDataConfig.getInstance().getDefaultTilesetTileSize();
    public final int NUM_TILES = NUM_COLUMNS_OF_TILESET*NUM_ROWS_OF_TILESET;

    private static ImageLoader instance = null;
    private final Map<String,Image> resources = new HashMap<>();
    private final Map<String, ArrayList<Image>> tileSets = new HashMap<>();
    private int numFile;

    public int getNumFiles(String dirPath) {

        File f = new File(dirPath);
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getPath());
        }
        if (files != null)
            for (int i = 0; i < files.length; i++) {

                File file = files[i];
                if (file.isDirectory())
                    getNumFiles(file.getPath());
                else {
                    numFile++;
//                    System.out.println(numFile);
                }

        }
        return numFile;
    }

    public void loadImages(String dirPath, Runnable r){
        SwingWorker worker = new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground()  {
                load(dirPath,r);
                return null;
            }
            @Override
            public void done() {
                System.out.println("finish");
            }

        };
        worker.execute();
    }

    private void load(String dirPath, Runnable r){
        File f = new File(dirPath);
        File[] files = f.listFiles();

        if (files != null)
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String path = file.getPath();
                if (file.isDirectory())
                    load(path,r);
                else{
                    if(path.contains("tilesets"))
                        loadTileset(file);
                    else if (path.contains("gif"))
                        loadGif(file);
                    else
                        loadImage(file);
                    r.run();
                }


            }
    }

    private void loadGif(File f) {
        String path = f.getPath();
        resources.put(path,new ImageIcon(path).getImage());
    }

    private void loadImage(File f) {
        Image tempImg = null;
        try {
            tempImg = ImageIO.read(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resources.put(f.getPath(),tempImg);
    }

    private void loadTileset(File f) {
        ArrayList<Image> tempArray = new ArrayList<>();
        try {
            int rows = 1;
            int col = 0;
            for(int i = 0; i < NUM_TILES; i++){
                tempArray.add(ImageIO.read(f).getSubimage(TILE_SIZE*col,(rows-1)*TILE_SIZE,TILE_SIZE,TILE_SIZE));

                col++;
                if(rows*NUM_COLUMNS_OF_TILESET-1 == i){
                    rows++;
                    col = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tileSets.put(f.getPath(),tempArray);
    }

    public ArrayList<Image> getImages(String path){
        ArrayList<Image> tempImg;
        if(path.contains("tilesets"))
            tempImg = tileSets.get(path);
        else if(!path.contains(".")){
            File f = new File(path);
            File[] tempFiles = f.listFiles();
            for(int i = 0; i<tempFiles.length; i++) {
                for(int j = 0; j<tempFiles.length;j++) {

                    String currPath = tempFiles[j].getPath().substring(tempFiles[j].getPath().lastIndexOf("\\"));

                    for(int k = 0; k<currPath.length();k++) {
                        char tempChar = currPath.charAt(k);
                        if (Character.isDigit(tempChar)) {
                            int position = Integer.valueOf(currPath.substring(k, k + 1));
                            if(Character.isDigit(currPath.charAt(k+1))) {
                                position = Integer.valueOf(currPath.substring(k, k + 2));
                            }

                            if (position-1 == i && i != j) {
                                File temp = tempFiles[j];
                                tempFiles[j] = tempFiles[i];
                                tempFiles[i] = temp;
                            }
                        }
                    }
                }

            }

            tempImg = new ArrayList<>();
            for(int i = 0; i<tempFiles.length; i++) {
                tempImg.add(resources.get(tempFiles[i].getPath()));
            }
        }else {
            tempImg = new ArrayList<>();
            tempImg.add(resources.get(path));
        }
        return tempImg;
    }

    public static ImageLoader getInstance() {
        if (instance == null)
            instance = new ImageLoader();
        return instance;
    }


}
