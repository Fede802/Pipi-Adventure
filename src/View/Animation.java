package view;

import utils.ImageLoader;
import java.awt.Image;
import java.util.ArrayList;

public class Animation {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final ArrayList<Image> ANIMATION_TILES;
    private final int ANIMATION_STEPS;

    private int currentAnimationStep;
    private float opacity = 1f;
    private boolean finish;

    //    --------------------------------------------------------
    //                       CONSTRUCTORS
    //    --------------------------------------------------------

    public Animation(String path) {
        ANIMATION_TILES = ImageLoader.getInstance().getImages(path);
        ANIMATION_STEPS = ANIMATION_TILES.size();
    }

    public Animation(ArrayList<Image> img) {
        ANIMATION_TILES = img;
        ANIMATION_STEPS = ANIMATION_TILES.size();
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public Image getFrame(){
        return ANIMATION_TILES.get(currentAnimationStep);
    }

    public void update() {
        currentAnimationStep++;
        if(currentAnimationStep == ANIMATION_STEPS){
            currentAnimationStep = 0;
            finish = true;
        }
    }

    public int getANIMATION_STEPS() {
        return ANIMATION_STEPS;
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
