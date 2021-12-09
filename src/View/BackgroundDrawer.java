package view;

import utils.GameConfig;
import utils.GameDataConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BackgroundDrawer {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final int UPDATE_TICK_DELAY = GameConfig.getInstance().getUpdateTickDelay();
    private final ArrayList<Image> IMAGES;
    private final JPanel PARENT_PANEL;
    private final int DX;

    private int x;
    private int paddingBottom;
    private int currentTick;
    private int currentFrame;
    private boolean transition;
    //images order when transition
    private boolean descending;
    private boolean loop = false;

    //    --------------------------------------------------------
    //                       CONSTRUCTORS
    //    --------------------------------------------------------

    public BackgroundDrawer(ArrayList<Image> background, JPanel PARENT_PANEL, final int DX) {
        this.PARENT_PANEL = PARENT_PANEL;
        this.DX = DX;
        IMAGES = background;
    }

    public BackgroundDrawer(ArrayList<Image> background, JPanel PARENT_PANEL, final int DX, final int paddingBottom) {
        this(background, PARENT_PANEL, DX);
        this.paddingBottom = paddingBottom;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setPaddingBottom(int paddingBottom){
        this.paddingBottom = paddingBottom;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean isTransition() {
        return transition;
    }

    public void updateFrames(){
        updateFrames(false);
    }

    public void updateFrames(boolean loop) {
        this.loop = loop;
        transition = true;
    }

    public void update() {
        x += DX;
        if(x>= PARENT_PANEL.getWidth()){
            x = 0;
        }
        if (transition){
            currentTick++;
            if (currentTick == UPDATE_TICK_DELAY){
                if (descending){
                    currentFrame--;
                }else {
                    currentFrame++;
                }
                currentTick = 0;
                if (currentFrame == IMAGES.size()-1 || currentFrame==0){
                    if (!loop){
                        transition = false;
                    }
                    descending = !descending;
                }
            }
        }
    }

    public void drawBackground(Graphics2D g2d) {
        g2d.drawImage(IMAGES.get(currentFrame), - (x), 0, PARENT_PANEL.getWidth(), PARENT_PANEL.getHeight()-paddingBottom,null);
        g2d.drawImage(IMAGES.get(currentFrame), PARENT_PANEL.getWidth()- (x), 0, PARENT_PANEL.getWidth(), PARENT_PANEL.getHeight()-paddingBottom, null);
    }

    public void reset() {
        currentTick=0;
        transition=false;
        currentFrame=0;
        descending=false;
    }

}

