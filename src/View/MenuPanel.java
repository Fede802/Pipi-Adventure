package view;

import controller.GameStateHandler;
import utils.FontLoader;
import utils.ImageLoader;
import utils.SoundManager;
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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MenuPanel extends ApplicationPanel {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final String TITLE = "Pipi Adventure";
    private final ArrayList<Rectangle2D.Double> BUTTONS = new ArrayList<>(){{
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
    }};
    private final double SHIFT_OPTIONS_STEP = 20;
    private final int SHIFT_TITLE_STEP = 10;
    private final Stroke BUTTONS_STROKE = new BasicStroke(4);
    private final String[] OPTIONS = {
            "Start",
            "Upgrade",
            "Help",
            "Quit",
            "Music",
            "Sound"
    };
    private final Color DEFAULT_COLOR = new Color(255, 216, 0);
    private final Color DEFAULT_BOUND_COLOR = new Color(255, 120, 0);
    private final Font TITLE_FONT = new Font(FontLoader.GAME_FONT, Font.BOLD, 45);
    private final Font OPTIONS_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN,30);
    private final int BG_DX = 1;

    private Image soundON,soundOFF,musicON,musicOFF,music,sound;
    private BackgroundDrawer menuAnimation;
    private BackgroundDrawer background;
    private int currentChoice = 0;

    //transition vars
    private boolean transition = false;
    private boolean firstOpen = true;

    //title transition vars
    private int titlePadding = 100;
    private int currentTitlePadding;
    private double shiftTitleLength;
    private int shiftTitleVelY;
    private int currentTitleStep;

    //options transition vars
    private double optionsMinPaddingTop = 20+this.getHeight()/3;
    private int shiftOptionsVelY;
    private int currentStep = 20;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public MenuPanel() {
        super();
        AUDIO.put(MUSIC_THEME,new SoundManager("res/audio/Title_Theme.wav",SoundManager.SOUND));
        AUDIO.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        AUDIO.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void setup(int bgTransl, int animationFrame, int currentTitlePaddingTop) {
        this.currentTitlePadding = currentTitlePaddingTop;
        background.setX(bgTransl);
        menuAnimation.setCurrentFrame(animationFrame);
        menuAnimation.updateFrames(true, false);
        if(SoundManager.isMusicActive())
            music = musicON;
        else
            music = musicOFF;
        if(SoundManager.isSoundActive())
            sound = soundON;
        else
            sound = soundOFF;
    }

    public int getTitlePadding() {
        return titlePadding;
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        background.update();
        menuAnimation.update();
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
    public void loadResources() {
        background = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\menu\\menubg.png"), this, BG_DX);
        menuAnimation = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\menu\\Animation"),this,0);
        musicON = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Music_Button1.png").get(0);
        musicOFF = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Music_Button2.png").get(0);
        soundON = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Sound_Button1.png").get(0);
        soundOFF = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Sound_Button2.png").get(0);
    }

    @Override
    public void start() {
        super.start();
        transition = true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(BUTTONS_STROKE);
        optionsMinPaddingTop = 20+this.getHeight()/3;
        shiftOptionsVelY = (int) Math.ceil((this.getHeight()-optionsMinPaddingTop) / SHIFT_OPTIONS_STEP);
        background.drawBackground(g2d);
        shiftTitleLength = currentTitlePadding-titlePadding;
        shiftTitleVelY = (int) Math.ceil(shiftTitleLength/ SHIFT_TITLE_STEP);
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, DEFAULT_BOUND_COLOR,StringDrawer.TITLE_STROKE, DEFAULT_COLOR,currentTitlePadding-(currentTitleStep*shiftTitleVelY), 0, this,StringDrawer.CENTER);
        //get and set prevColor not needed, every panel has it's own graphics context
        g2d.setColor(Color.DARK_GRAY);
        for(int i = 0; i < OPTIONS.length; i++) {
            Color boundColor;
            if(i == currentChoice)
                boundColor = Color.DARK_GRAY;
            else
                boundColor = DEFAULT_BOUND_COLOR;
           if (i == 4)
               menuAnimation.drawBackground(g2d);
            switch (i) {
                case 4 -> {
                    g2d.drawImage(music, this.getWidth() / 10 - 50, this.getHeight() - (this.getHeight() / 10) + 5 + shiftOptionsVelY * currentStep, 50 * minSize / DEFAULT_WIDTH, 50 * minSize / DEFAULT_WIDTH, null);
                    BUTTONS.get(4).setRect((int) this.getWidth() / 10 - 50, (int) this.getHeight() - (this.getHeight() / 10) + 5 + shiftOptionsVelY * currentStep, (int) 50 * minSize / DEFAULT_WIDTH, (int) 50 * minSize / DEFAULT_WIDTH);
                    if (currentChoice == 4)
                        g2d.draw(BUTTONS.get(4));
                    break;
                }
                case 5 -> {
                    g2d.drawImage(sound, this.getWidth() - this.getWidth() / 10, this.getHeight() - (this.getHeight() / 10) + 5 + shiftOptionsVelY * currentStep, 50 * minSize / DEFAULT_WIDTH, 50 * minSize / DEFAULT_WIDTH, null);
                    BUTTONS.get(5).setRect(this.getWidth() - this.getWidth() / 10, this.getHeight() - (this.getHeight() / 10) + 5 + shiftOptionsVelY * currentStep, 50 * minSize / DEFAULT_WIDTH, 50 * minSize / DEFAULT_WIDTH);
                    if (currentChoice == 5)
                        g2d.draw(BUTTONS.get(5));
                    break;
                }
                default -> {
                    StringDrawer.drawString(g2d, OPTIONS[i], OPTIONS_FONT, boundColor, StringDrawer.DEFAULT_STROKE, DEFAULT_COLOR, optionsMinPaddingTop + i * 60 + shiftOptionsVelY * currentStep, 0, this, StringDrawer.CENTER);
                    double strWidth = StringDrawer.getStringWidth(g2d, OPTIONS[i], OPTIONS_FONT);
                    double strHeight = StringDrawer.getStringHeight(g2d, OPTIONS_FONT);
                    BUTTONS.get(i).setRect((this.getWidth() - strWidth) / 2, optionsMinPaddingTop + i * 60 + shiftOptionsVelY * currentStep, strWidth, (strHeight));
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            AUDIO.get(CONFIRM_THEME).playOnce();
            select();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            AUDIO.get(SCROLL_THEME).playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = OPTIONS.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            AUDIO.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == OPTIONS.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AUDIO.get(CONFIRM_THEME).playOnce();
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
                    AUDIO.get(SCROLL_THEME).playOnce();
                }
            }
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
            GameStateHandler.getInstance().openHelpPanel();
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
                AUDIO.get(MUSIC_THEME).startLoop();
            }else{
                sound = soundOFF;
                AUDIO.get(MUSIC_THEME).stopLoop();
            }
        }
    }

}
