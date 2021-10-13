package commons;

import utils.GameDataConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Animation {

    private static final int TILE_SIZE = GameDataConfig.getInstance().getDefaultImageTileSize();
    private final ArrayList<Image> animationTile = new ArrayList<>();
    private final int animationStep;
    private int currentAnimationStep;
    private float opacity = 1f;

    public Animation(String imagePath){
        animationTile.add(new ImageIcon(imagePath).getImage());
        animationStep = 1;
    }

    public Animation(File image, int numColumns){
        for(int i = 0;i < numColumns;i++){
            try {
                animationTile.add(ImageIO.read(image).getSubimage(0,TILE_SIZE*i,TILE_SIZE,TILE_SIZE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        animationStep = numColumns;
    }
    public Animation(ArrayList<File> animationFrameFile){
        for(int i = 0;i < animationFrameFile.size();i++){
            try {
                animationTile.add(ImageIO.read(animationFrameFile.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        animationStep = animationFrameFile.size();
    }

    public Image getFrame(){
        return animationTile.get(currentAnimationStep);
    }
    public void update(){
        currentAnimationStep++;
        if(currentAnimationStep == animationStep)
            currentAnimationStep = 0;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
}