package view;

import controller.GameStateHandler;
import utils.ImageLoader;
import utils.SoundManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class HelpPanel extends ApplicationPanel {

    private final String TITLE = "Controls";
    private final ArrayList<Rectangle2D.Double> buttons = new ArrayList<>() {{
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
    }};
    private BackgroundDrawer BG_DRAWER;
    private int currentChoice = 0;
    private final String[] CONTROL_STRINGS = {
            "Jump",
            "Shoot",
            "Pause",
    };

    private final String[] OPTIONS = {
            "Menu",
            "Play"
    };

    private final Color titleColor = Color.RED;
    private final Font titleFont = new Font("04b", Font.BOLD, 40);
    private final Font font = new Font("04b", Font.PLAIN, 25);
    private final Font font2 = new Font("04b", Font.PLAIN, 30);
    private Image leftClick;
    private Image rightClick;
    private Image pauseClick;
    private Image qButton;
    private Image pButton;
    private Image spaceButton;
    private Image[] firstLineImages;
    private Image[] secondLineImages;


    public HelpPanel() {
        super();
        //TODO change menu theme
        audio.put(MUSIC_THEME, new SoundManager("res/audio/Help_Theme.wav",SoundManager.SOUND));
        audio.put(SCROLL_THEME, new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        audio.put(CONFIRM_THEME, new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        //no update needed
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        BG_DRAWER.drawBackground(g2d);
        StringDrawer.drawString(g2d, TITLE, titleFont, null, StringDrawer.TITLE_STROKE, titleColor, this.getHeight() / 25, 0, this, StringDrawer.CENTER);
        for(int i = -1; i< CONTROL_STRINGS.length-1; i++){
            StringDrawer.drawString(g2d, CONTROL_STRINGS[i+1], font, null, StringDrawer.DEFAULT_STROKE, titleColor,this.getHeight() / 6, 7*this.getWidth()/24*i,this,StringDrawer.CENTER);
            g2d.drawImage(firstLineImages[i+1],this.getWidth()/2+7*this.getWidth()/24*i-this.getHeight()/14,this.getHeight()/4,this.getHeight()/7,this.getHeight()/7,null);
            StringDrawer.drawString(g2d, CONTROL_STRINGS[i+1], font, null, StringDrawer.DEFAULT_STROKE, titleColor,this.getHeight() / 2, 7*this.getWidth()/24*i,this,StringDrawer.CENTER);
            g2d.drawImage(secondLineImages[i+1],this.getWidth()/2+7*this.getWidth()/24*i-this.getHeight()/14,7*this.getHeight()/12,this.getHeight()/7,this.getHeight()/7,null);
        }
        for (int i = 0; i < OPTIONS.length; i++) {
            if (i == currentChoice) {
                g2d.setColor(Color.WHITE);
            } else {
                g2d.setColor(Color.BLACK);
            }
            StringDrawer.drawString(g2d, OPTIONS[i], font2, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() - this.getHeight() / 8,  ((StringDrawer.getStringWidth(g2d, OPTIONS[i], font)/2)+this.getWidth() / 10)*(2*i-1), this, StringDrawer.CENTER);
            double strWidth = StringDrawer.getStringWidth(g2d, OPTIONS[i],font2);
            double strHeight = StringDrawer.getStringHeight(g2d,font2);
            buttons.get(i).setRect((this.getWidth()-strWidth)/2+((StringDrawer.getStringWidth(g2d, OPTIONS[i], font)/2)+this.getWidth() / 10)*(2*i-1),this.getHeight() - this.getHeight() / 8,strWidth,(strHeight));
        }
    }

    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().menu();
        }
        if(currentChoice == 1) {
            GameStateHandler.getInstance().startGame();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            audio.get(CONFIRM_THEME).playOnce();
            select();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = OPTIONS.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
        BG_DRAWER = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\help\\Help_BG.png"), this, 0);
        leftClick = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Left_Click.png").get(0);
        rightClick = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Right_Click.png").get(0);
        pauseClick = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Pause_Click.png").get(0);
        qButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Q_button.png").get(0);
        pButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\p_button.png").get(0);
        spaceButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Space_button.png").get(0);
        firstLineImages = new Image[]{leftClick, rightClick, pauseClick};
        secondLineImages = new Image[]{qButton, pButton, spaceButton};
    }
}
