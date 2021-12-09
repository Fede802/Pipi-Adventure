package view;

import utils.ImageLoader;

import java.awt.*;
import java.util.ArrayList;

public class Animation {

    private final ArrayList<Image> ANIMATION_TILES;
    private final int ANIMATION_STEP;

    private int currentAnimationStep;
    private float opacity = 1f;
    private boolean finish;

    public Animation(String path) {
        ANIMATION_TILES = ImageLoader.getInstance().getImages(path);
        ANIMATION_STEP = ANIMATION_TILES.size();
    }

    public Animation(ArrayList<Image> img) {
        ANIMATION_TILES = img;
        ANIMATION_STEP = ANIMATION_TILES.size();
    }

    public Image getFrame(){
        return ANIMATION_TILES.get(currentAnimationStep);
    }

    public void update() {
        currentAnimationStep++;
        if(currentAnimationStep == ANIMATION_STEP){
            currentAnimationStep = 0;
            finish = true;
        }
    }

    public int getANIMATION_STEP() {
        return ANIMATION_STEP;
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

    public void resetAnimation() {
        currentAnimationStep = 0;
        finish = false;
        opacity = 1f;
    }

}
