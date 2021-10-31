package view;

import controller.GameStateHandler;
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
    private final ArrayList<Rectangle2D.Double> buttons = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};
    private final BackgroundDrawer BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Menu/menubg.gif"),this,DX);;

    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Upgrade",
            "Help",
            "Quit"
    };

    private final Color titleColor = new Color(255, 216, 0);
    private final Font titleFont = new Font("04b", Font.BOLD, 45);
    private final Font font = new Font("04b", Font.PLAIN,30);
    private Image menuGIF = new ImageIcon("Resources/Backgrounds/Menu/Sfondo_Menu.gif").getImage();

    public MenuPanel(){
        super();
        audio.put(MUSIC_THEME,new SoundManager("Resources/Audio/Title_Theme.wav"));
        audio.put(SCROLL_THEME,new SoundManager("Resources/Audio/MenuScroll.wav"));
        audio.put(CONFIRM_THEME,new SoundManager("Resources/Audio/MenuConfirm.wav"));
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        BG_DRAWER.update();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        BG_DRAWER.drawBackground(g2d);
        g2d.drawImage(menuGIF, 0,100,this.getWidth(),this.getHeight()-100 , null);
        StringDrawer.drawString(g2d, TITLE, titleFont, new Color(255, 120, 0),StringDrawer.TITLE_STROKE,titleColor,100, 0, this,StringDrawer.CENTER);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice)
                g2d.setColor(Color.DARK_GRAY);
            else
                g2d.setColor(new Color(255, 120, 0));
            StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,50+i*60+this.getHeight()/3, 0, this,StringDrawer.CENTER);
            double strWidth = StringDrawer.getStringWidth(g2d,options[i],font);
            double strHeight = StringDrawer.getStringHeight(g2d,font);
            buttons.get(i).setRect((this.getWidth()-strWidth)/2,50+i*60+this.getHeight()/3,strWidth,(strHeight));
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
}
