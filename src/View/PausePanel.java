package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.FontLoader;
import utils.ImageLoader;
import utils.SoundManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class PausePanel extends SlidableApplicationPanel {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final String TITLE = "Pause";
    private final Color DEFAULT_COLOR = Color.WHITE;
    private final Color DEFAULT_BOUND_COLOR = Color.BLACK;
    private final Font TITLE_FONT = new Font(FontLoader.GAME_FONT, Font.BOLD, 50);
    private final Font OPTIONS_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 21);
    private final String[] OPTIONS = {
            "Restart",
            "Resume",
            "Menu"
    };
    private final int BG_DX = 1;
    private final ArrayList<Rectangle2D.Double> BUTTONS = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};

    private Image pipiRun, frame;
    private BackgroundDrawer background;
    private int currentChoice = 1;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public PausePanel(ComponentContainer container) {
        super(container);
        AUDIO.put(MUSIC_THEME,new SoundManager("res/audio/Pause_Theme.wav",SoundManager.SOUND));
        AUDIO.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        AUDIO.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    protected void timerActionEvent(ActionEvent e) {
        background.update();
        super.timerActionEvent(e);
    }

    @Override
    public void loadResources() {
        background = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\pause\\Pause_BG.png"), this, BG_DX);;
        pipiRun = ImageLoader.getInstance().getImages("res\\images\\backgrounds\\pause\\Pause_BackGround_GIF.gif").get(0);
        frame = ImageLoader.getInstance().getImages("res\\images\\backgrounds\\PictureFrame.png").get(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        background.drawBackground(g2d);
        g2d.drawImage(pipiRun ,0,this.getHeight()/2-this.getHeight()/8,this.getWidth(),this.getHeight()/2,this);
        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), this );
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, DEFAULT_BOUND_COLOR,StringDrawer.TITLE_STROKE, DEFAULT_COLOR,this.getWidth()/4, 0, this,StringDrawer.CENTER);
        for(int i = 0; i < OPTIONS.length; i++) {
            Color boundColor;
            if(i == currentChoice) {
                boundColor = Color.RED;
            }else{
                boundColor = DEFAULT_BOUND_COLOR;
            }
            StringDrawer.drawString(g2d, OPTIONS[i], OPTIONS_FONT, boundColor,StringDrawer.DEFAULT_STROKE, DEFAULT_COLOR,this.getHeight()/2 + 50*i, 0 ,this,StringDrawer.CENTER);
            double strWidth = StringDrawer.getStringWidth(g2d,OPTIONS[i], OPTIONS_FONT);
            double strHeight = StringDrawer.getStringHeight(g2d, OPTIONS_FONT);
            BUTTONS.get(i).setRect((this.getWidth()-strWidth)/2,this.getHeight()/2 + 50*i,strWidth,(strHeight));
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
            GameEngine.getInstance().saveDataConfig();
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            GameStateHandler.getInstance().resumeGame();
        }
        if(currentChoice == 2) {
            GameEngine.getInstance().saveDataConfig();
            GameStateHandler.getInstance().openMenuPanel();
        }
    }

}
