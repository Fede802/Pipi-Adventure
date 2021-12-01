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

    private Image gun;
    private int renderedTileSize;
    private final int SECTION_SIZE = GameDataConfig.getInstance().getMapSectionSize();
    private HashMap<RenderingType,HashMap<Integer, ArrayList<Image>>> entityFrames;
    private final JPanel panel;

    public EntitiesDrawer(JPanel panel){
        this.panel = panel;
    }

    public void drawEntities(Graphics2D g2d){
        int entityNum = GameEngine.getInstance().getTotalEntity();
        double mapTranslX = GameEngine.getInstance().getMapTranslX();
        for(int i = 0; i < entityNum; i++){
            Pair<EntityCoordinates, AnimationData> temp = GameEngine.getInstance().getEntityForRendering(i);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, temp.getValue().getOpacity()));
            ArrayList<Image> frameArray = entityFrames.get(temp.getValue().getRenderingType()).get(temp.getValue().getAnimationType());
            if(temp.getValue().isHasToUpdate()){
                temp.getValue().updateCurrentAnimationStep();
                if(temp.getValue().getCurrentAnimationStep() == frameArray.size()-1 || frameArray.size() == 1){
                    temp.getValue().updateCurrentNumLoop();
                    temp.getValue().setCurrentAnimationStep(AnimationData.LAST_FRAME);
                }
                if(temp.getValue().getCurrentAnimationStep() == frameArray.size())
                    temp.getValue().setCurrentAnimationStep(0);
                GameEngine.getInstance().updateAnimationData(temp.getValue(),i);
            }
            Image tempFrame;
            if(temp.getValue().getCurrentAnimationStep() == AnimationData.LAST_FRAME){
                tempFrame = frameArray.get(frameArray.size()-1);
            }else{
                tempFrame = frameArray.get(temp.getValue().getCurrentAnimationStep());
            }
            g2d.drawImage(tempFrame,
                    Math.toIntExact(Math.round((temp.getKey().getMapX() + SECTION_SIZE * temp.getKey().getMapIndex()) * renderedTileSize - mapTranslX + temp.getKey().getTranslX())),
                    Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - temp.getKey().getMapY()) * renderedTileSize + temp.getKey().getTranslY())),
                    renderedTileSize,
                    renderedTileSize,
                    null
            );
            if(temp.getValue().getRenderingType() == RenderingType.PLAYER && temp.getValue().getAnimationType() != AnimationData.DEATH_ANIMATION_RIGHT)
                g2d.drawImage(gun,
                        Math.toIntExact(Math.round((temp.getKey().getMapX()+ 1 + SECTION_SIZE * temp.getKey().getMapIndex()) * renderedTileSize - mapTranslX -(renderedTileSize/10) + temp.getKey().getTranslX())),
                        Math.toIntExact(Math.round(panel.getHeight() - (SECTION_SIZE - temp.getKey().getMapY()) * renderedTileSize + temp.getKey().getTranslY())),
                        renderedTileSize,
                        renderedTileSize,
                        null
                );
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

    }
    public void updateRenderedTileSize(final int renderedTileSize){
        this.renderedTileSize = renderedTileSize;
    }

    public void loadResources(){
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
}
