package view;

import utils.ImageLoader;

import java.awt.*;
import java.util.ArrayList;

public class Animation {

    private final ArrayList<Image> animationTile;
    private final int animationStep;
    private int currentAnimationStep;
    private float opacity = 1f;
    private boolean finish;

    public Animation(String path){
        animationTile = ImageLoader.getInstance().getImages(path);
        animationStep = animationTile.size();
    }

    public Animation(ArrayList<Image> img ){
        animationTile = img;
        animationStep = animationTile.size();
    }

    public Image getFrame(){
        return animationTile.get(currentAnimationStep);
    }

    public void update(){
        currentAnimationStep++;
        if(currentAnimationStep == animationStep){
            currentAnimationStep = 0;
            finish = true;
        }
    }

    public int getAnimationStep() {
        return animationStep;
    }

    public int getCurrentAnimationStep() {
        return currentAnimationStep;
    }

    public void setCurrentAnimationStep(int currentAnimationStep) {
        this.currentAnimationStep = currentAnimationStep;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void switchOpacity() {
        if(opacity == 1f)
            opacity = 0.5f;
        else
            opacity = 1f;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public void resetAnimation(){
        currentAnimationStep = 0;
        finish = false;
        opacity = 1f;
    }

}
