package view;

import controller.GameStateHandler;
import utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

public class GameOverPanel extends SlidableApplicationPanel {
    private final int DX = 1;

    private final String TITLE = "Game Over";

    private final Image bed, frame;

    private final BackgroundDrawer BG_DRAWER;

    private final Color titleColor = Color.WHITE;
    private final Font titleFont = new Font("04b", Font.BOLD, 50);
    private final Font font = new Font("04b", Font.PLAIN, 21);

    private int currentChoice = 0;
    private String[] options = {
            "Restart",
            "Help",
            "Menu"
    };


    private String[] score = {
            "Score : prova",
            "Best Score : provaaa",
            "Coin : 9999"
    };

    public GameOverPanel(ComponentContainer componentContainer){
        super(componentContainer);
        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/GameOver/GameOver_BackGround.png"),this,DX);
        bed = new ImageIcon("Resources/Backgrounds/GameOver/GameOver.gif").getImage();
        frame = new ImageIcon("Resources/Backgrounds/PictureFrame.png").getImage();
        audio.put(MUSIC_THEME,new SoundManager("Resources/Audio/GameOverTheme.wav"));
        audio.put(SCROLL_THEME,new SoundManager("Resources/Audio/MenuScroll.wav"));
        audio.put(CONFIRM_THEME,new SoundManager("Resources/Audio/MenuConfirm.wav"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;



        BG_DRAWER.drawBackground(g2d);

        g2d.drawImage(bed, this.getWidth()/2-this.getWidth()/4,this.getHeight()/3-this.getHeight()/4,this.getWidth()/2,this.getHeight()/2,this);

        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), this );

        StringDrawer.drawString(g2d, TITLE, titleFont, Color.WHITE,StringDrawer.TITLE_STROKE,titleColor,this.getWidth()/7, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.WHITE);
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/2+this.getWidth()/4, this,StringDrawer.PADDING);;
        }

        g2d.setColor(Color.BLACK);
        for(int i = 0; i < score.length; i++) {
            StringDrawer.drawString(g2d, score[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/10, this,StringDrawer.PADDING);;
        }

    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        super.timerActionEvent(e);
        BG_DRAWER.update();

    }

    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            // help
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
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
