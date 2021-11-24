package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoadingPanel extends ApplicationPanel{

    private final int DX = 1;
    private final String IMG_RES_PATH = "res/images";
    private final String TITLE = "Pipi Adventure";
    private final Color TITLE_COLOR = new Color(255, 216, 0);
    private final Font TITLE_FONT = new Font("04b", Font.BOLD, 45);
    private final int TOTAL_IMG_FILES = ImageLoader.getInstance().getNumFiles(IMG_RES_PATH);
    private final Image LOADING_GIF = new ImageIcon("res/images/backgrounds/menu/Sfondo_Menu.gif").getImage();
    private int currentFileLoaded;
    private BackgroundDrawer BG_DRAWER;


    private final int loadingBarWidth = 200;
    private final int shiftBarStep = 20;
    private final int shiftTitleStep = 10;
    private final double barStep = (double)loadingBarWidth/ TOTAL_IMG_FILES;

    private double shiftBarLength;
    private int shiftBarVelY;
    private int currentBarStep = 0;
    private int currentTitleStep;
    private double shiftTitleLength;
    private int shiftTitleVelY;

    private boolean transition = false;

    public LoadingPanel(){
        super();
        System.out.println(TOTAL_IMG_FILES);

        ArrayList<Image> temp = new ArrayList<>();
        try {
            temp.add(ImageIO.read(new File("res/images/backgrounds/menu/menubg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BG_DRAWER = new BackgroundDrawer(temp,this,DX);
        ImageLoader.getInstance().loadImages(IMG_RES_PATH, updateLoadingBar);
    }

    private Runnable updateLoadingBar = new Runnable() {
        @Override
        public void run() {
            currentFileLoaded++;
            if(currentFileLoaded == TOTAL_IMG_FILES){
                load();
            }
        }
    };

    private void load(){
        GameEngine.getInstance().setResources();
        transition = true;
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        BG_DRAWER.update();
        if(transition){
            currentBarStep++;
            if(currentBarStep%2 == 0)
                currentTitleStep++;
            if(currentBarStep == shiftBarStep)
                GameStateHandler.getInstance().startApplication();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        shiftBarLength = getHeight()-3*this.getHeight()/5-12;
        shiftBarVelY = (int) Math.ceil(shiftBarLength/shiftBarStep);
        shiftTitleLength = (2*this.getHeight()/5-100)/2;
        shiftTitleVelY = (int) Math.ceil(shiftTitleLength/shiftTitleStep);
        BG_DRAWER.drawBackground(g2d);
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, new Color(255, 120, 0),StringDrawer.TITLE_STROKE, TITLE_COLOR,2*this.getHeight()/5-currentTitleStep*shiftTitleVelY, 0, this,StringDrawer.CENTER);

        Stroke ds = g2d.getStroke();
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRoundRect((this.getWidth()-loadingBarWidth)/2-1,3*this.getHeight()/5-12+shiftBarVelY*currentBarStep,loadingBarWidth+2,24,20,20);
        g2d.setColor(Color.MAGENTA);
        g2d.fillRoundRect((this.getWidth()-loadingBarWidth)/2,3*this.getHeight()/5-10+shiftBarVelY*currentBarStep,(int)(barStep*currentFileLoaded),20,20,20);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(ds);
        g2d.drawImage(LOADING_GIF, 0,100,this.getWidth(),this.getHeight()-100 , null);

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

    @Override
    public void loadResources() {
        //nothing to do
    }

    //todo instead of this a method to get all res?

    public int getBgTransl() {
        return BG_DRAWER.getX();
    }



    public Image getBgGif() {
    return LOADING_GIF;
    }
}
