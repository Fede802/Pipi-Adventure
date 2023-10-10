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

public class GameOverPanel extends SlidableApplicationPanel {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final int BG_DX = 1;
    private final String TITLE = "Game Over";
    private final Color DEFAULT_COLOR = Color.RED;
    private final Font TITLE_FONT = new Font(FontLoader.GAME_FONT, Font.BOLD, 50);
    private final Font DEFAULT_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 21);
    private final String[] OPTIONS = {
            "Reset",
            "Help",
            "Menu"
    };
    private final String[] GAME_DATA_STRINGS = {
            "Score : ",
            "Record : ",
            "Coins : "
    };
    private final String RECORD_STRING = "NEW RECORD";
    private final String[] GAME_DATA_VALUES = new String[3];
    private final ArrayList<Rectangle2D.Double> BUTTONS = new ArrayList<>(){{
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
    }};

    private Image bed, frame;
    private BackgroundDrawer background;
    private int currentChoice = 0;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public GameOverPanel(ComponentContainer container) {
        super(container);
        AUDIO.put(MUSIC_THEME,new SoundManager("res/audio/GameOverTheme.wav",SoundManager.SOUND));
        AUDIO.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        AUDIO.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void setData(int currentScore, int recordScore, int currentCoins) {
        GAME_DATA_VALUES[0]=String.valueOf(currentScore);
        GAME_DATA_VALUES[1]=String.valueOf(recordScore);
        GAME_DATA_VALUES[2]=String.valueOf(currentCoins);
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        super.timerActionEvent(e);
        background.update();
    }

    @Override
    public void loadResources() {
        background = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\gameOver\\GameOver_BG.png"), this, BG_DX);
        bed = ImageLoader.getInstance().getImages("res\\images\\backgrounds\\gameOver\\GameOver.gif").get(0);
        frame = ImageLoader.getInstance().getImages("res\\images\\backgrounds\\PictureFrame.png").get(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        background.drawBackground(g2d);
        g2d.drawImage(bed, this.getWidth()/2-this.getWidth()/4,this.getHeight()/3-this.getHeight()/4,this.getWidth()/2,this.getHeight()/2,this);
        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), this );
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, Color.WHITE,StringDrawer.TITLE_STROKE, DEFAULT_COLOR,this.getWidth()/7, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < OPTIONS.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.WHITE);
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            StringDrawer.drawString(g2d, OPTIONS[i], DEFAULT_FONT, null,StringDrawer.DEFAULT_STROKE, DEFAULT_COLOR,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/2+this.getWidth()/4, this,StringDrawer.PADDING);
            double strWidth = StringDrawer.getStringWidth(g2d, OPTIONS[i], DEFAULT_FONT);
            double strHeight = StringDrawer.getStringHeight(g2d, DEFAULT_FONT);
            BUTTONS.get(i).setRect((this.getWidth()/2+this.getWidth()/4),180+i*(this.getWidth()/12)+this.getHeight()/3,strWidth,(strHeight));

        }
        for(int i = 0; i < GAME_DATA_STRINGS.length; i++) {
            StringDrawer.drawString(g2d, GAME_DATA_STRINGS[i] + GAME_DATA_VALUES[i], DEFAULT_FONT, Color.BLACK,StringDrawer.DEFAULT_STROKE, DEFAULT_COLOR,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/10, this,StringDrawer.PADDING);;
        }
        if (Integer.parseInt(String.valueOf(GAME_DATA_VALUES[0])) >= Integer.parseInt(String.valueOf(GAME_DATA_VALUES[1]))){
            StringDrawer.drawString(g2d, RECORD_STRING, DEFAULT_FONT, DEFAULT_COLOR, StringDrawer.DEFAULT_STROKE,Color.WHITE,180+this.getHeight()/4, 0,this,StringDrawer.CENTER);
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
    public void mousePressed(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AUDIO.get(CONFIRM_THEME).playOnce();
        select();
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
            GameStateHandler.getInstance().openHelpPanel();
        }
        if(currentChoice == 2) {
            GameStateHandler.getInstance().openMenuPanel();
        }
        GameEngine.getInstance().saveDataConfig();
    }

}
