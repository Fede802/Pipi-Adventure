package view;

import controller.GameStateHandler;
import utils.SoundManager;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

public class PausePanel extends SlidableApplicationPanel{
    private final int DX = 1;

    private final String TITLE = "Pause";

    private final Image pipiRun, frame;

    private final BackgroundDrawer BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Pause/Pause_BackGround2.png"),this,DX);;
    private final Color titleColor = Color.WHITE;
    private final Font titleFont = new Font("04b", Font.BOLD, 50);
    private final Font font = new Font("04b", Font.PLAIN, 21);
    private int currentChoice = 1;
    private String[] options = {
            "Restart",
            "Resume",
            "Menu"
    };
    public PausePanel(ComponentContainer componentContainer){
        super(componentContainer);
        pipiRun = new ImageIcon("Resources/Backgrounds/Pause/Pause_BackGround_GIF.gif").getImage();
        frame = new ImageIcon("Resources/Backgrounds/PictureFrame.png").getImage();
        audio.put(MUSIC_THEME,new SoundManager("resources/audio/Pause_Theme.wav"));
        audio.put(SCROLL_THEME,new SoundManager("Resources/Audio/MenuScroll.wav"));
        audio.put(CONFIRM_THEME,new SoundManager("Resources/Audio/MenuConfirm.wav"));
    }
    @Override
    protected void timerActionEvent(ActionEvent e) {
        super.timerActionEvent(e);
        BG_DRAWER.update();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;



        BG_DRAWER.drawBackground(g2d);

        g2d.drawImage(pipiRun ,0,this.getHeight()/2-this.getHeight()/8,this.getWidth(),this.getHeight()/2,this);

        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), this );



        StringDrawer.drawString(g2d, TITLE, titleFont, Color.BLACK,StringDrawer.TITLE_STROKE,titleColor,this.getWidth()/4, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.RED);
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            switch(i){
                case 0:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2 + i*50, this.getWidth()/2 - StringDrawer.getStringWidth(g2d, options[i],font)/2,this,StringDrawer.PADDING);
                    break;
                case 2:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2 + i*50, this.getWidth()/2 - StringDrawer.getStringWidth(g2d, options[i],font)/2,this,StringDrawer.PADDING);
                    break;
                default:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2 + i*50, this.getWidth()/2 - StringDrawer.getStringWidth(g2d, options[i],font)/2,this,StringDrawer.CENTER);
                    break;
            }
        }

    }

    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            GameStateHandler.getInstance().resumeGame();
        }
        if(currentChoice == 2) {
            GameStateHandler.getInstance().menu();
        }
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

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
