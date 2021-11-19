package commons;

import utils.GameDataConfig;
import utils.ResourceLoader;

import java.awt.*;
import java.util.ArrayList;

public class AnimationData {

    public static final int WALK_ANIMATION_RIGHT = 0;
    public static final int WALK_ANIMATION_LEFT = 1;
    public static final int DEATH_ANIMATION_RIGHT = 2;
    public static final int DEATH_ANIMATION_LEFT = 3;
    public static final int JUMPING_ANIMATION = 4;

    public static final int LAST_FRAME = -1;

    public RenderingType getRenderingType() {
        return renderingType;
    }

    public void setRenderingType(RenderingType renderingType) {
        this.renderingType = renderingType;
    }

    public int getAnimationType() {
        return animationType;
    }

    public void setAnimationType(int animationType) {
        this.animationType = animationType;
    }

    private RenderingType renderingType;
    private int animationType;
    private int currentAnimationStep;
    private int currentNumLoop;
    private float opacity = 1f;

    private boolean hasToUpdate;

    public void setupAnimation(int currentAnimationStep,int currentNumLoop, int animationType, RenderingType renderingType){
        this.currentAnimationStep = currentAnimationStep;
        this.animationType = animationType;
        this.renderingType = renderingType;
        this.currentNumLoop = currentNumLoop;
        hasToUpdate = false;
    }

    public void setupAnimation(int currentAnimationStep, int animationType, RenderingType renderingType){
        this.setupAnimation(currentAnimationStep,0,animationType,renderingType);
    }

    public boolean isHasToUpdate() {
        return hasToUpdate;
    }

    public void hasToUpdate(boolean update) {
        hasToUpdate = update;
    }

    public float getOpacity() {
        return opacity;
    }

    //todo non credo serva
    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void switchOpacity() {
        if(opacity == 1f)
            opacity = 0.5f;
        else
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

    public void updateCurrentAnimationStep() {
        currentAnimationStep++;
    }

    public void updateCurrentNumLoop() {
        currentNumLoop++;
    }
}
