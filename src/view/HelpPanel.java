package view;

import controller.GameStateHandler;
import utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

public class HelpPanel extends ApplicationPanel implements KeyListener, MouseListener {
    private final String TITLE = "Controls";
    private final ArrayList<Rectangle2D.Double> buttons = new ArrayList<>() {{
        add(new Rectangle2D.Double());add(new Rectangle2D.Double());
    }};
    private final BackgroundDrawer BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Help/Help_BG1.png"), this, 0);
    private int currentChoice = 0;
    private String[] options = {
            "Jump",
            "Shoot",
            "Pause",
    };

    private String[] button = {
            "Menu",
            "Play"
    };

    private final Color titleColor = Color.RED;
    private final Font titleFont = new Font("04b", Font.BOLD, 40);
    private final Font font = new Font("04b", Font.PLAIN, 25);
    private final Font font2 = new Font("04b", Font.PLAIN, 30);
    private final Image leftClick = new ImageIcon("Resources/GameImages/Left_Click.png").getImage();
    private final Image rightClick = new ImageIcon("Resources/GameImages/Right_Click.png").getImage();
    private final Image pauseClick = new ImageIcon("Resources/GameImages/Pause_Click.png").getImage();
    private final Image qButton = new ImageIcon("Resources/GameImages/Q_button.png").getImage();
    private final Image pButton = new ImageIcon("Resources/GameImages/P_button.png").getImage();
    private final Image spaceButton = new ImageIcon("Resources/GameImages/Space_button.png").getImage();
    private final Image[] firstLineImages = {leftClick,rightClick,pauseClick};
    private final Image[] secondLineImages = {qButton,pButton,spaceButton};


    public HelpPanel() {
        super();
        audio.put(MUSIC_THEME, new SoundManager("Resources/Audio/Title_Theme.wav"));
        audio.put(SCROLL_THEME, new SoundManager("Resources/Audio/MenuScroll.wav"));
        audio.put(CONFIRM_THEME, new SoundManager("Resources/Audio/MenuConfirm.wav"));
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        //maybe nothing to do
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        BG_DRAWER.drawBackground(g2d);
//        g2d.drawImage(BackGround, 0, 0, this.getWidth(), this.getHeight(), null);
        StringDrawer.drawString(g2d, TITLE, titleFont, null, StringDrawer.TITLE_STROKE, titleColor, this.getHeight() / 25, 0, this, StringDrawer.CENTER);
        for(int i = -1; i<options.length-1; i++){
            StringDrawer.drawString(g2d, options[i+1], font, null, StringDrawer.DEFAULT_STROKE, titleColor,this.getHeight() / 6, 7*this.getWidth()/24*i,this,StringDrawer.CENTER);
            g2d.drawImage(firstLineImages[i+1],this.getWidth()/2+7*this.getWidth()/24*i-this.getHeight()/14,this.getHeight()/4,this.getHeight()/7,this.getHeight()/7,null);
            StringDrawer.drawString(g2d, options[i+1], font, null, StringDrawer.DEFAULT_STROKE, titleColor,this.getHeight() / 2, 7*this.getWidth()/24*i,this,StringDrawer.CENTER);
            g2d.drawImage(secondLineImages[i+1],this.getWidth()/2+7*this.getWidth()/24*i-this.getHeight()/14,7*this.getHeight()/12,this.getHeight()/7,this.getHeight()/7,null);
        }
//        StringDrawer.drawString(g2d, options[0], font, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() / 6, this.getWidth() / 10, this, StringDrawer.PADDING);
//        StringDrawer.drawString(g2d, options[1], font, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() / 6, 0, this, StringDrawer.CENTER);
//        StringDrawer.drawString(g2d, options[2], font, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() / 6, this.getWidth() - StringDrawer.getStringWidth(g2d, "Pause", font) - this.getWidth() / 10, this, StringDrawer.PADDING);
//
//        g2d.drawImage(leftClick, this.getWidth() / 13, this.getHeight() / 4, this.getHeight() / 6, this.getHeight() / 6, this);
//        g2d.drawImage(rightClick, this.getWidth() / 2 - (rightClick.getWidth(this) / 5), this.getHeight() / 4, this.getHeight() / 6, this.getHeight() / 6, this);
//        g2d.drawImage(pauseClick, this.getWidth() - (this.getWidth() / 10) - leftClick.getWidth(null) / 3, this.getHeight() / 4, this.getHeight() / 6, this.getHeight() / 6, this);
//
//        StringDrawer.drawString(g2d, options[0], font, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() / 2, this.getWidth() / 10, this, StringDrawer.DEFAULT_STROKE);
//
//        StringDrawer.drawString(g2d, options[1], font, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() / 2, 0, this, StringDrawer.CENTER);
//
//        StringDrawer.drawString(g2d, options[2], font, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() / 2, this.getWidth() - StringDrawer.getStringWidth(g2d, "Pause", font) - this.getWidth() / 10, this, StringDrawer.DEFAULT_STROKE);
//
//
//
//        g2d.drawImage(spaceButton, this.getWidth() / 13, this.getHeight() / 2 + this.getHeight() / 10, this.getHeight() / 6, this.getHeight() / 6, this);
//        g2d.drawImage(qButton, this.getWidth() / 2 - (rightClick.getWidth(this) / 5), this.getHeight() / 2 + this.getHeight() / 10, this.getHeight() / 6, this.getHeight() / 6, this);
//        g2d.drawImage(pButton, this.getWidth() - (this.getWidth() / 10) - leftClick.getWidth(null) / 3, this.getHeight() / 2 + this.getHeight() / 10, this.getHeight() / 6, this.getHeight() / 6, this);

        for (int i = 0; i < button.length; i++) {
            if (i == currentChoice) {
                g2d.setColor(Color.WHITE);
                System.out.println(i);
            } else {
                g2d.setColor(Color.BLACK);
            }
            StringDrawer.drawString(g2d, button[i], font2, null, StringDrawer.DEFAULT_STROKE, titleColor, this.getHeight() - this.getHeight() / 8,  ((StringDrawer.getStringWidth(g2d, button[i], font)/2)+this.getWidth() / 10)*(2*i-1), this, StringDrawer.CENTER);
            double strWidth = StringDrawer.getStringWidth(g2d,button[i],font2);
            double strHeight = StringDrawer.getStringHeight(g2d,font2);
            buttons.get(i).setRect((this.getWidth()-strWidth)/2+((StringDrawer.getStringWidth(g2d, button[i], font)/2)+this.getWidth() / 10)*(2*i-1),this.getHeight() - this.getHeight() / 8,strWidth,(strHeight));
//            g2d.draw(buttons.get(i));
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
                currentChoice = button.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == button.length) {
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
}
