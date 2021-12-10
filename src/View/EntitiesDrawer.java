package view;

import commons.*;
import controller.GameEngine;
import utils.GameDataConfig;
import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EntitiesDrawer {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    private final JPanel PARENT_PANEL;

    private HashMap<RenderingType,HashMap<Integer, ArrayList<Image>>> entityFrames;
    private Image gun;
    private int renderingTileSize;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public EntitiesDrawer(JPanel parentPanel){
        this.PARENT_PANEL = parentPanel;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void drawEntities(Graphics2D g2d) {
        int entityNum = GameEngine.getInstance().getTotalEntities();
        double mapTranslX = GameEngine.getInstance().getMapTranslX();
        for(int i = 0; i < entityNum; i++){
            Pair<EntityCoordinates, AnimationData> temp = GameEngine.getInstance().getEntityForRendering(i);
            EntityCoordinates tempPosition = temp.getKey();
            AnimationData tempAnimation = temp.getValue();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tempAnimation.getOpacity()));
            ArrayList<Image> frameArray = entityFrames.get(tempAnimation.getRenderingType()).get(tempAnimation.getAnimationType());
            if(tempAnimation.isHasToUpdate()){
                updateAnimation(tempAnimation,frameArray.size());
                GameEngine.getInstance().updateAnimation(temp.getValue(),i);
            }
            Image tempFrame;
            if(tempAnimation.getCurrentAnimationStep() == AnimationData.LAST_FRAME){
                tempFrame = frameArray.get(frameArray.size()-1);
            }else{
                tempFrame = frameArray.get(temp.getValue().getCurrentAnimationStep());
            }
            g2d.drawImage(tempFrame,
                    Math.toIntExact(Math.round((tempPosition.getMapX() + SECTION_SIZE * tempPosition.getMapIndex()) * renderingTileSize - mapTranslX + tempPosition.getTranslX())),
                    Math.toIntExact(Math.round(PARENT_PANEL.getHeight() - (SECTION_SIZE - tempPosition.getMapY()) * renderingTileSize + tempPosition.getTranslY())),
                    renderingTileSize,
                    renderingTileSize,
                    null
            );
            if(temp.getValue().getRenderingType() == RenderingType.PLAYER && temp.getValue().getAnimationType() != AnimationData.DEATH_ANIMATION_RIGHT)
                g2d.drawImage(gun,
                        Math.toIntExact(Math.round((tempPosition.getMapX()+ 1 + SECTION_SIZE * tempPosition.getMapIndex()) * renderingTileSize - mapTranslX -(renderingTileSize /10) + tempPosition.getTranslX())),
                        Math.toIntExact(Math.round(PARENT_PANEL.getHeight() - (SECTION_SIZE - tempPosition.getMapY()) * renderingTileSize + tempPosition.getTranslY())),
                        renderingTileSize,
                        renderingTileSize,
                        null
                );
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void updateRenderingTileSize(int renderingTileSize){
        this.renderingTileSize = renderingTileSize;
    }

    public void loadResources() {
        gun = ImageLoader.getInstance().getImages("res\\images\\entities\\player\\Pistola.png").get(0);
        HashMap<Integer,ArrayList<Image>> player = new HashMap<>(){{
            put(AnimationData.WALK_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\player\\Walk") );
            put(AnimationData.DEATH_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\player\\Death"));
            put(AnimationData.JUMPING_ANIMATION, ImageLoader.getInstance().getImages("res\\images\\entities\\player\\Jump"));
        }};
        HashMap<Integer,ArrayList<Image>> coin = new HashMap<>(){{
            put(AnimationData.WALK_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\coin\\Moneta.gif") );
            put(AnimationData.DEATH_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\coin\\Moneta_img.png"));
        }};
        HashMap<Integer,ArrayList<Image>> bullet = new HashMap<>(){{
            put(AnimationData.WALK_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\player\\Guns") );
        }};
        HashMap<Integer,ArrayList<Image>> snail = new HashMap<>(){{
            put(AnimationData.WALK_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Luma\\Animazione\\Luma_Walk_R") );
            put(AnimationData.DEATH_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Luma\\Morte\\Luma_Death_R"));
            put(AnimationData.WALK_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Luma\\Animazione\\Luma_Walk_L") );
            put(AnimationData.DEATH_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Luma\\Morte\\Luma_Death_L"));

        }};
        HashMap<Integer,ArrayList<Image>> slime = new HashMap<>(){{
            put(AnimationData.WALK_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Slime\\Animazione\\Slime_Walk_R") );
            put(AnimationData.DEATH_ANIMATION_RIGHT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Slime\\Morte\\Slime_Death_R"));
            put(AnimationData.WALK_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Slime\\Animazione\\Slime_Walk_L") );
            put(AnimationData.DEATH_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Slime\\Morte\\Slime_Death_L"));
        }};
        HashMap<Integer,ArrayList<Image>> bee = new HashMap<>(){{
            put(AnimationData.WALK_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Ape\\Animazione") );
            put(AnimationData.DEATH_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Ape\\Morte"));
        }};
        HashMap<Integer,ArrayList<Image>> bat = new HashMap<>(){{
            put(AnimationData.WALK_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Pipistrello\\Animazione") );
            put(AnimationData.DEATH_ANIMATION_LEFT, ImageLoader.getInstance().getImages("res\\images\\entities\\enemy\\Pipistrello\\Morte"));
        }};

        entityFrames = new HashMap<>(){{
            put(RenderingType.PLAYER,player);
            put(RenderingType.COIN,coin);
            put(RenderingType.BULLET,bullet);
            put(RenderingType.SNAIL,snail);
            put(RenderingType.SLIME,slime);
            put(RenderingType.BEE,bee);
            put(RenderingType.BAT,bat);
        }};
    }

    private void updateAnimation(AnimationData animation, int animationLength){
        animation.updateCurrentAnimationStep();
        if(animation.getCurrentAnimationStep() == animationLength-1 || animationLength == 1){
            animation.updateCurrentNumLoop();
            animation.setCurrentAnimationStep(AnimationData.LAST_FRAME);
        }
        if(animation.getCurrentAnimationStep() == animationLength)
            animation.setCurrentAnimationStep(0);
    }

}
