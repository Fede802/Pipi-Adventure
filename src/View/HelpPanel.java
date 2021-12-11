package view;

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

public class HelpPanel extends ApplicationPanel {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final String TITLE = "Controls";
    private final ArrayList<Rectangle2D.Double> BUTTONS = new ArrayList<>() {{
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
    }};
    private final String[] ACTIONS_STRINGS = {
            "Jump",
            "Shoot",
            "Pause",
    };
    private final String[] OPTIONS = {
            "Menu",
            "Play"
    };
    private final Color DEFAULT_COLOR = Color.RED;
    private final Font TITLE_FONT = new Font(FontLoader.GAME_FONT, Font.BOLD, 40);
    private final Font INFO_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 25);
    private final Font OPTIONS_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 30);

    private BackgroundDrawer background;
    private int currentChoice = 0;
    private Image[] firstLineImages;
    private Image[] secondLineImages;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public HelpPanel() {
        super();
        AUDIO.put(MUSIC_THEME, new SoundManager("res/audio/Help_Theme.wav",SoundManager.SOUND));
        AUDIO.put(SCROLL_THEME, new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        AUDIO.put(CONFIRM_THEME, new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    protected void timerActionEvent(ActionEvent e) {
        //no update needed
        repaint();
    }

    @Override
    public void loadResources() {
        background = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\help\\Help_BG.png"), this, 0);
        Image leftClick = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Left_Click.png").get(0);
        Image rightClick = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Right_Click.png").get(0);
        Image pauseClick = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Pause_Click.png").get(0);
        Image qButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Q_button.png").get(0);
        Image pButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\p_button.png").get(0);
        Image spaceButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Space_button.png").get(0);
        firstLineImages = new Image[]{leftClick, rightClick, pauseClick};
        secondLineImages = new Image[]{qButton, pButton, spaceButton};
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        background.drawBackground(g2d);
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, null, StringDrawer.TITLE_STROKE, DEFAULT_COLOR, this.getHeight() / 25, 0, this, StringDrawer.CENTER);
        for(int i = -1; i< ACTIONS_STRINGS.length-1; i++){
            StringDrawer.drawString(g2d, ACTIONS_STRINGS[i+1], INFO_FONT, null, StringDrawer.DEFAULT_STROKE, DEFAULT_COLOR,this.getHeight() / 6, 7*this.getWidth()/24*i,this,StringDrawer.CENTER);
            g2d.drawImage(firstLineImages[i+1],this.getWidth()/2+7*this.getWidth()/24*i-this.getHeight()/14,this.getHeight()/4,this.getHeight()/7,this.getHeight()/7,null);
            StringDrawer.drawString(g2d, ACTIONS_STRINGS[i+1], INFO_FONT, null, StringDrawer.DEFAULT_STROKE, DEFAULT_COLOR,this.getHeight() / 2, 7*this.getWidth()/24*i,this,StringDrawer.CENTER);
            g2d.drawImage(secondLineImages[i+1],this.getWidth()/2+7*this.getWidth()/24*i-this.getHeight()/14,7*this.getHeight()/12,this.getHeight()/7,this.getHeight()/7,null);
        }
        for (int i = 0; i < OPTIONS.length; i++) {
            if (i == currentChoice) {
                g2d.setColor(Color.WHITE);
            } else {
                g2d.setColor(Color.BLACK);
            }
            StringDrawer.drawString(g2d, OPTIONS[i], OPTIONS_FONT, null, StringDrawer.DEFAULT_STROKE, DEFAULT_COLOR, this.getHeight() - this.getHeight() / 8,  ((StringDrawer.getStringWidth(g2d, OPTIONS[i], INFO_FONT)/2)+this.getWidth() / 10)*(2*i-1), this, StringDrawer.CENTER);
            double strWidth = StringDrawer.getStringWidth(g2d, OPTIONS[i], OPTIONS_FONT);
            double strHeight = StringDrawer.getStringHeight(g2d, OPTIONS_FONT);
            BUTTONS.get(i).setRect((this.getWidth()-strWidth)/2+((StringDrawer.getStringWidth(g2d, OPTIONS[i], INFO_FONT)/2)+this.getWidth() / 10)*(2*i-1),this.getHeight() - this.getHeight() / 8,strWidth,(strHeight));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            AUDIO.get(CONFIRM_THEME).playOnce();
            select();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            AUDIO.get(SCROLL_THEME).playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = OPTIONS.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
            GameStateHandler.getInstance().openMenuPanel();
        }
        if(currentChoice == 1) {
            GameStateHandler.getInstance().startGame();
        }
    }

}
