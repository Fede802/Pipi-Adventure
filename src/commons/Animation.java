package commons;

import utils.GameDataConfig;
import utils.ResouceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Animation {

    private static final int TILE_SIZE = GameDataConfig.getInstance().getDefaultImageTileSize();
    private ArrayList<Image> animationTile;
    private final int animationStep;
    private int currentAnimationStep;
    private int currentNumLoop = 0;
    private float opacity = 1f;



    public Animation(String path){
        animationTile = ResouceLoader.getInstance().getRes(path);
        animationStep = animationTile.size();
    }


    public Image getFrame(){
        return animationTile.get(currentAnimationStep);
    }
    public void update(){

        currentAnimationStep++;
        if(currentAnimationStep == animationStep-1){
            currentNumLoop++;
        }else if(currentAnimationStep == animationStep)
            currentAnimationStep = 0;
        //todo if death loop not checked max int problem

    }

    public float getOpacity() {
        return opacity;
    }

    public void switchOpacity() {
        if(opacity == 1f)
            opacity = 0.5f;
        else
            opacity = 1f;
    }


    public int getNumLoop() {
        return currentNumLoop;
    }

    public void resetAnimation(){
        //maybe call it setupAnimation
        //todo maybe non serve
        currentAnimationStep = 0;

        currentNumLoop = 0;
        opacity = 1f;
    }

    public int getCurrentAnimationStep() {
        return currentAnimationStep;
    }

    public void setCurrentAnimationStep(int currentAnimationStep) {
        this.currentAnimationStep = currentAnimationStep;
    }

    public int getCurrentNumLoop() {
        return currentNumLoop;
    }

    public void setCurrentNumLoop(int currentNumLoop) {
        this.currentNumLoop = currentNumLoop;
    }
}
