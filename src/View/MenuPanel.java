package view;

import controller.GameStateHandler;
import utils.ImageLoader;
import utils.SoundManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MenuPanel extends ApplicationPanel{

    private final int DX = 1;
    private final String TITLE = "Pipi Adventure";
    private final ArrayList<Rectangle2D.Double> BUTTONS = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};
    private BackgroundDrawer BG_DRAWER;
    private boolean transition = false;
    private boolean firstOpen = true;

    private double paddingTop = 30+this.getHeight()/3;
    private final double SHIFT_STEP = 20;
    private final int SHIFT_TITLE_STEP = 10;
    private int currentStep = 20;
    private int shiftBarVelY;
    private int currentTitleStep;
    private double shiftTitleLength;
    private int shiftTitleVelY;

    private int currentChoice = 0;
    private final String[] OPTIONS = {
            "Start",
            "Upgrade",
            "Help",
            "Quit",
            "Music",
            "Sound"
    };

    private final Color TITLE_COLOR = new Color(255, 216, 0);
    private final Font TITLE_FONT = new Font("04b", Font.BOLD, 45);
    private final Font FONT = new Font("04b", Font.PLAIN,30);
    private Image soundON,soundOFF,musicON,musicOFF,music,sound;
    private Animation menuGIF;
    //TODO config
    private final int BG_TICK_ANIMATION =10;
    private  int currentBGTickAnimation;

    public MenuPanel(){
        super();
        audio.put(MUSIC_THEME,new SoundManager("res/audio/Title_Theme.wav",SoundManager.SOUND));
        audio.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        audio.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }


    @Override
    protected void timerActionEvent(ActionEvent e) {
        BG_DRAWER.update();
        currentBGTickAnimation++;
        if (currentBGTickAnimation == BG_TICK_ANIMATION){
            currentBGTickAnimation = 0;
            menuGIF.update();
        }
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
        paddingTop = 20+this.getHeight()/3;
        shiftBarVelY = (int) Math.ceil(paddingTop/ SHIFT_STEP);
        BG_DRAWER.drawBackground(g2d);
        shiftTitleLength = (2*this.getHeight()/5-100)/2;
        shiftTitleVelY = (int) Math.ceil(shiftTitleLength/ SHIFT_TITLE_STEP);
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, new Color(255, 120, 0),StringDrawer.TITLE_STROKE, TITLE_COLOR,100+(2*this.getHeight()/5-100)/2-currentTitleStep*shiftTitleVelY, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < OPTIONS.length; i++) {
            if(i == currentChoice)
                g2d.setColor(Color.DARK_GRAY);
            else
                g2d.setColor(new Color(255, 120, 0));
           if (i == 4)
               g2d.drawImage(menuGIF.getFrame(), 0,100,this.getWidth(),this.getHeight()-100 , null);
            switch (i){
                case(4):{
                    g2d.drawImage(music, this.getWidth()/10-50, this.getHeight()-(this.getHeight()/10)+5+shiftBarVelY*currentStep,50*minSize/DEFAULT_WIDTH,50*minSize/DEFAULT_WIDTH,null);
                    BUTTONS.get(4).setRect((int)this.getWidth()/10-50,(int)this.getHeight()-(this.getHeight()/10)+5+shiftBarVelY*currentStep,(int)50*minSize/DEFAULT_WIDTH,(int)50*minSize/DEFAULT_WIDTH);
                    if (currentChoice == 4)
                        g2d.draw(BUTTONS.get(4));
                    break;
                }
                case(5):{
                    g2d.drawImage(sound, this.getWidth()-this.getWidth()/10, this.getHeight()-(this.getHeight()/10)+5+shiftBarVelY*currentStep,50*minSize/DEFAULT_WIDTH,50*minSize/DEFAULT_WIDTH,null);
                    BUTTONS.get(5).setRect(this.getWidth()-this.getWidth()/10, this.getHeight()-(this.getHeight()/10)+5+shiftBarVelY*currentStep,50*minSize/DEFAULT_WIDTH,50*minSize/DEFAULT_WIDTH);
                    if (currentChoice == 5)
                        g2d.draw(BUTTONS.get(5));
                    break;
                }
                default:{
                    StringDrawer.drawString(g2d, OPTIONS[i], FONT, null,StringDrawer.DEFAULT_STROKE, TITLE_COLOR,20+i*60+this.getHeight()/3+shiftBarVelY*currentStep, 0, this,StringDrawer.CENTER);
                    double strWidth = StringDrawer.getStringWidth(g2d, OPTIONS[i], FONT);
                    double strHeight = StringDrawer.getStringHeight(g2d, FONT);
                    BUTTONS.get(i).setRect((this.getWidth()-strWidth)/2,20+i*60+this.getHeight()/3+shiftBarVelY*currentStep,strWidth,(strHeight));
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
            SoundManager.switchMusicConfig();
            if(music == musicOFF){
                music = musicON;

            }else{
                music = musicOFF;

            }

        }
        if (currentChoice == 5){
            SoundManager.switchSoundConfig();
            if(sound == soundOFF){
                sound = soundON;
                audio.get(MUSIC_THEME).startLoop();
            }else{
                sound = soundOFF;
                audio.get(MUSIC_THEME).stopLoop();
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
                currentChoice = OPTIONS.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == OPTIONS.length) {
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
        for(int i = 0; i < BUTTONS.size(); i++){
            if(BUTTONS.get(i).contains(e.getX(),e.getY())){
                if(currentChoice != i){
                    currentChoice = i;
                    audio.get(SCROLL_THEME).playOnce();
                }
            }
        }
    }

    @Override
    public void loadResources() {
        BG_DRAWER = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\menu\\menubg.png"), this,DX);
        musicON = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Music_Button1.png").get(0);
        musicOFF = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Music_Button2.png").get(0);
        soundON = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Sound_Button1.png").get(0);
        soundOFF = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Sound_Button2.png").get(0);
    }

    public void setup(int bgTransl, Animation bg) {
        BG_DRAWER.setX(bgTransl);
        this.menuGIF = bg;
        if(SoundManager.isIsMusicActive())
            music = musicON;
        else
            music = musicOFF;
        if(SoundManager.isIsSoundActive())
            sound = soundON;
        else
            sound = soundOFF;

    }
}
