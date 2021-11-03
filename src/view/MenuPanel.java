package view;

import controller.GameStateHandler;
import utils.ResouceLoader;
import utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

public class MenuPanel extends ApplicationPanel{

    private final int DX = 1;
    private final String TITLE = "Pipi Adventure";
    private final ArrayList<Rectangle2D.Double> buttons = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};
    private BackgroundDrawer BG_DRAWER;
    private boolean transition = false;
    private boolean firstOpen = true;
    private double paddingTop = 50+this.getHeight()/3;
    private int shiftBarVelY;
    private double shiftstep = 20;

    //todo never used
    private double titlePadding;
    private int currentTitleStep;
    private double shiftTitleLength;
    private final int shiftTitleStep = 10;
    private int shiftTitleVelY;

    private int currentStep = 20;

    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Upgrade",
            "Help",
            "Quit",
            "Music",
            "Sound"
    };

    private final Color titleColor = new Color(255, 216, 0);
    private final Font titleFont = new Font("04b", Font.BOLD, 45);
    private final Font font = new Font("04b", Font.PLAIN,30);
    private Image menuGIF,soundON,soundOFF,musicON,musicOFF,music,sound;


    public MenuPanel(){
        super();
        audio.put(MUSIC_THEME,new SoundManager("res/audio/Title_Theme.wav"));
        audio.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav"));
        audio.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav"));
    }
    public MenuPanel(BackgroundDrawer bg){
        this();
        BG_DRAWER = bg;
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        BG_DRAWER.update();
        if(firstOpen && transition){
            currentStep--;
            if(currentStep%2 == 0)
                currentTitleStep++;
            if(currentStep == 0){
                transition = false;
                firstOpen = false;
            }
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        paddingTop = 50+this.getHeight()/3;
        shiftBarVelY = (int) Math.ceil(paddingTop/shiftstep);
        BG_DRAWER.drawBackground(g2d);
        shiftTitleLength = (2*this.getHeight()/5-100)/2;
        shiftTitleVelY = (int) Math.ceil(shiftTitleLength/shiftTitleStep);
        StringDrawer.drawString(g2d, TITLE, titleFont, new Color(255, 120, 0),StringDrawer.TITLE_STROKE,titleColor,100+(2*this.getHeight()/5-100)/2-currentTitleStep*shiftTitleVelY, 0, this,StringDrawer.CENTER);

        int minSize = this.getWidth();
        int size = this.getHeight();
        if(size < minSize){
            minSize = size;
        }

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice)
                g2d.setColor(Color.DARK_GRAY);
            else
                g2d.setColor(new Color(255, 120, 0));
           if (i == 3)
               g2d.drawImage(menuGIF, 0,100,this.getWidth(),this.getHeight()-100 , null);
            switch (i){
                case(4):{
                    g2d.drawImage(music, this.getWidth()/10-50, this.getHeight()-(this.getHeight()/10)+5,50*minSize/DEFAULT_WIDTH,50*minSize/DEFAULT_WIDTH,null);
                    buttons.get(4).setRect((int)this.getWidth()/10-50,(int)this.getHeight()-(this.getHeight()/10)+5,(int)50*minSize/DEFAULT_WIDTH,(int)50*minSize/DEFAULT_WIDTH);
                    if (currentChoice == 4)
                        g2d.draw(buttons.get(4));
                    break;
                }
                case(5):{
                    g2d.drawImage(sound, this.getWidth()-this.getWidth()/10, this.getHeight()-(this.getHeight()/10)+5,50*minSize/DEFAULT_WIDTH,50*minSize/DEFAULT_WIDTH,null);
                    buttons.get(5).setRect(this.getWidth()-this.getWidth()/10, this.getHeight()-(this.getHeight()/10)+5,50*minSize/DEFAULT_WIDTH,50*minSize/DEFAULT_WIDTH);
                    if (currentChoice == 5)
                        g2d.draw(buttons.get(5));
                    break;
                }
                default:{
                    StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,50+i*60+this.getHeight()/3+shiftBarVelY*currentStep, 0, this,StringDrawer.CENTER);
                    double strWidth = StringDrawer.getStringWidth(g2d,options[i],font);
                    double strHeight = StringDrawer.getStringHeight(g2d,font);
                    buttons.get(i).setRect((this.getWidth()-strWidth)/2,50+i*60+this.getHeight()/3+shiftBarVelY*currentStep,strWidth,(strHeight));
                }
            }
            g2d.setColor(new Color(255, 120, 0));
        }

    }

    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            GameStateHandler.getInstance().openUpgradePanel();
        }
        if(currentChoice == 2){
            GameStateHandler.getInstance().openControlView();
        }
        if(currentChoice == 3) {
            System.exit(0);
        }
        if (currentChoice == 4){
            if(music == musicOFF){
                music = musicON;
            }else{
                music = musicOFF;
            }
        }
        if (currentChoice == 5){
            if(sound == soundOFF){
                sound = soundON;
            }else{
                sound = soundOFF;
            }
        }
    }

    @Override
    public void start() {
        super.start();
        transition = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            audio.get(CONFIRM_THEME).playOnce();
            select();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        audio.get(CONFIRM_THEME).playOnce();
        select();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).contains(e.getX(),e.getY())){
                if(currentChoice != i){
                    currentChoice = i;
                    audio.get(SCROLL_THEME).playOnce();
                }
            }
        }
    }

    @Override
    public void loadResources() {
        BG_DRAWER = new BackgroundDrawer(ResouceLoader.getInstance().getRes("res\\images\\backgrounds\\menu\\menubg.png"), this,DX);
        musicON = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Music_Button1.png").get(0);
        musicOFF = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Music_Button2.png").get(0);
        soundON = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Sound_Button1.png").get(0);
        soundOFF = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Sound_Button2.png").get(0);
    }

    public void setup(int bgTrasl, double titlePadding, Image bg) {
        BG_DRAWER.setX(bgTrasl);
        this.titlePadding = titlePadding;
        this.menuGIF = bg;
        music = musicON;
        sound = soundON;

    }
}
