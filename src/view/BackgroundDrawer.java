package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class BackgroundDrawer {
    private static final int UPDATE_TICK_DELAY = 40;

    private ArrayList<Image> image = new ArrayList<>();
    private JPanel panel;



    private int x;
    private int dx;
    private int paddingBottom;
    private int currentTick;
    private int currentFrame;
    private boolean transition;
    private boolean descending;
    private boolean loop = false;


    public BackgroundDrawer(ArrayList<Image> background, JPanel panel, final int dx){
        this.panel = panel;
        this.dx = dx;
        image = background;
    }
    public BackgroundDrawer(ArrayList<Image> background, JPanel panel, final int dx, final int paddingBottom) {
        this(background,panel,dx);
        this.paddingBottom = paddingBottom;
    }

    public void update() {
        x += dx;
        if(x>=panel.getWidth()){
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
                if (currentFrame == image.size()-1 || currentFrame==0){
                    if (!loop){
                        transition = false;
                    }
                    descending = !descending;
                }
            }
        }
    }

    public void drawBackground(Graphics2D g2d) {
        g2d.drawImage(image.get(currentFrame), - (x), 0,panel.getWidth(),panel.getHeight()-paddingBottom,null);
        g2d.drawImage(image.get(currentFrame), panel.getWidth()- (x), 0,panel.getWidth(),panel.getHeight()-paddingBottom, null);
    }
    public void setPaddingBottom(int paddingBottom){
        this.paddingBottom = paddingBottom;
    }

    public void updateFrames(){
        transition = true;
    }

    public void updateFrames(boolean loop){
        this.loop = loop;
        transition = true;
    }

    public void reset(){
        currentTick=0;
        transition=false;
        currentFrame=0;
        descending=false;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public boolean isTransition() {
        return transition;
    }

}

