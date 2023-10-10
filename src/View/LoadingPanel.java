package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.FontLoader;
import utils.ImageLoader;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoadingPanel extends ApplicationPanel {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final String TITLE = "Pipi Adventure";
    private final int BG_DX = 1;
    private final String IMG_RES_PATH = "res/images";
    private final Color DEFAULT_COLOR = new Color(255, 216, 0);
    private final Font TITLE_FONT = new Font(FontLoader.GAME_FONT, Font.BOLD, 45);
    private final int TOTAL_IMG_FILES = ImageLoader.getInstance().getNumFiles(IMG_RES_PATH);
    private final BackgroundDrawer BACKGROUND;
    private final BackgroundDrawer BACKGROUND_ANIMATION;
    private final Stroke BAR_STROKE = new BasicStroke(4);
    private final int LOADING_BAR_WIDTH = 200;
    private final double BAR_STEP = (double) LOADING_BAR_WIDTH / TOTAL_IMG_FILES;
    private final int SHIFT_BAR_STEP = 20;
    private final int SHIFT_TITLE_STEP = 10;
    private final Runnable BAR_UPDATE = new Runnable() {
        @Override
        public void run() {
            currentFilesLoaded++;
            if(currentFilesLoaded == TOTAL_IMG_FILES){
                load();
            }
        }
    };

    private int currentFilesLoaded;
    private boolean transition;

    //title transition vars
    private int titlePaddingTop = 2*this.getHeight()/5;
    private int finalTitlePaddingTop;
    private double shiftTitleLength;
    private int shiftTitleVelY;
    private int currentTitleStep;

    //bar transition vars
    private int barPaddingTop = 3*this.getHeight()/5-12;
    private double shiftBarLength;
    private int shiftBarVelY;
    private int currentBarStep;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public LoadingPanel(int titlePaddingTop){
        super();
        finalTitlePaddingTop = titlePaddingTop;
        ArrayList<Image> temp = new ArrayList<>();
        try {
            temp.add(ImageIO.read(new File("res/images/backgrounds/menu/menubg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BACKGROUND = new BackgroundDrawer(temp,this, BG_DX);
        int animationFrames = 17;
        ArrayList<Image> tempBGAnimation = new ArrayList<>();
        for(int i = 0; i < animationFrames;i++)
            tempBGAnimation.add(new ImageIcon("res/images/backgrounds/menu/Animation/New_Menu_Animation"+(i+1)+".png").getImage());
        BACKGROUND_ANIMATION = new BackgroundDrawer(tempBGAnimation,this,0);
        BACKGROUND_ANIMATION.updateFrames(true, false);
        System.out.println(TOTAL_IMG_FILES);
        ImageLoader.getInstance().loadImages(IMG_RES_PATH, BAR_UPDATE);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public int getBgTransl() {
        return BACKGROUND.getX();
    }

    public int getBgAnimationCurrentFrame() {
        return BACKGROUND_ANIMATION.getCurrentFrame();
    }

    public int getCurrentTitlePaddingTop() {
        return titlePaddingTop-currentTitleStep*shiftTitleVelY;
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        BACKGROUND.update();
        BACKGROUND_ANIMATION.update();
        if(transition){
            currentBarStep++;
            if(currentBarStep%2 == 0)
                currentTitleStep++;
            if(currentBarStep == SHIFT_BAR_STEP)
                GameStateHandler.getInstance().startApplication();
        }
        repaint();
    }

    @Override
    public void loadResources() {
        //nothing to do
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        barPaddingTop = 3*this.getHeight()/5-12;
        shiftBarLength = getHeight()-barPaddingTop;
        shiftBarVelY = (int) Math.ceil(shiftBarLength/ SHIFT_BAR_STEP);

        titlePaddingTop = 2*this.getHeight()/5;
        shiftTitleLength = (titlePaddingTop-finalTitlePaddingTop)/2;
        shiftTitleVelY = (int) Math.ceil(shiftTitleLength/ SHIFT_TITLE_STEP);

        BACKGROUND.drawBackground(g2d);
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, new Color(255, 120, 0),StringDrawer.TITLE_STROKE, DEFAULT_COLOR,titlePaddingTop-currentTitleStep*shiftTitleVelY, 0, this,StringDrawer.CENTER);

        g2d.setStroke(BAR_STROKE);
        g2d.drawRoundRect((this.getWidth()- LOADING_BAR_WIDTH)/2-1,barPaddingTop+shiftBarVelY*currentBarStep, LOADING_BAR_WIDTH +2,24,20,20);
        g2d.setColor(Color.MAGENTA);
        g2d.fillRoundRect((this.getWidth()- LOADING_BAR_WIDTH)/2,barPaddingTop+2+shiftBarVelY*currentBarStep,(int)(BAR_STEP * currentFilesLoaded),20,20,20);

        BACKGROUND_ANIMATION.drawBackground(g2d);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //nothing to do
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //nothing to do
    }

    private void load(){
        GameEngine.getInstance().setResources();
        transition = true;
    }

}
