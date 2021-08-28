package Commons;

import View.MapDrawer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Animation {
    private static final int TILE_SIZE = 120;
    private ArrayList<Image> animationTile = new ArrayList<>();
    private int animationStep;
    private int currentAnimationStep;

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
        Image frame = animationTile.get(currentAnimationStep);

        return frame;
    }
    public void update(){
        currentAnimationStep++;
        if(currentAnimationStep == animationStep)
            currentAnimationStep = 0;
    }
}
