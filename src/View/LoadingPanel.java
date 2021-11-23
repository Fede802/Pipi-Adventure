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
    private final String imgResPath = "res/images";
    private final String TITLE = "Pipi Adventure";
    private final Color titleColor = new Color(255, 216, 0);
    private final Font titleFont = new Font("04b", Font.BOLD, 45);
    private int totalResFile = ImageLoader.getInstance().getNumFiles(imgResPath);
    private int currentFileLoaded;
    private BackgroundDrawer BG_DRAWER;
    private Image loadingGIF = new ImageIcon("res/images/backgrounds/menu/Sfondo_Menu.gif").getImage();
    private final int loadingBarWidth = 200;
    private double barStep = (double)loadingBarWidth/totalResFile;

    private double shiftBarLength;
    private final int shiftBarStep = 20;
    private int currentBarStep = 0;
    private int shiftBarVelY;

    private int currentTitleStep;
    private double shiftTitleLength;
    private final int shiftTitleStep = 10;
    private int shiftTitleVelY;

    private boolean transition = false;

    public LoadingPanel(){
        super();
        System.out.println(totalResFile);
        System.out.println(this.getHeight());
        System.out.println("shiftlength "+shiftBarLength);
        System.out.println("shiftvwl "+shiftBarVelY);
        ArrayList<Image> temp = new ArrayList<>();
        try {
            temp.add(ImageIO.read(new File("res/images/backgrounds/menu/menubg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(barStep+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        BG_DRAWER = new BackgroundDrawer(temp,this,DX);
        ImageLoader.getInstance().loadImages(imgResPath,incrementLoadingbar);
    }



    private Runnable incrementLoadingbar = new Runnable() {
        @Override
        public void run() {
            currentFileLoaded++;
            System.out.println(currentFileLoaded);
            if(currentFileLoaded == totalResFile){
                //load and transition
                loadfin();
            }
        }
    };
private void loadfin(){
    System.out.println("startLoadApplication");
    GameEngine.getInstance().setResources();
    System.out.println("chaLoadApplication");

    System.out.println("startApplication");
    transition = true;
}
    @Override
    protected void timerActionEvent(ActionEvent e) {
        BG_DRAWER.update();
        if(transition){
            currentBarStep++;
            if(currentBarStep%2 == 0)
                currentTitleStep++;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException interruptedException) {
//                interruptedException.printStackTrace();
//            }
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
        StringDrawer.drawString(g2d, TITLE, titleFont, new Color(255, 120, 0),StringDrawer.TITLE_STROKE,titleColor,2*this.getHeight()/5-currentTitleStep*shiftTitleVelY, 0, this,StringDrawer.CENTER);

        Stroke ds = g2d.getStroke();
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRoundRect((this.getWidth()-loadingBarWidth)/2-1,3*this.getHeight()/5-12+shiftBarVelY*currentBarStep,loadingBarWidth+2,24,20,20);
        g2d.setColor(Color.MAGENTA);
//        System.out.println("bar " +(int)(barStep*currentFileLoaded));
//        System.out.println("barn "+barStep*currentFileLoaded);
        g2d.fillRoundRect((this.getWidth()-loadingBarWidth)/2,3*this.getHeight()/5-10+shiftBarVelY*currentBarStep,(int)(barStep*currentFileLoaded),20,20,20);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(ds);
        g2d.drawImage(loadingGIF, 0,100,this.getWidth(),this.getHeight()-100 , null);

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void loadResources() {

    }

    public int getBgTrasl() {
        return BG_DRAWER.getX();
    }

    public double getTitlePadding() {
        return 2*this.getHeight()/5-currentTitleStep*shiftTitleVelY;
    }

    public Image getBgGif() {
    return loadingGIF;
    }
}
