package View;

import Controller.GameStateHandler;
import Utils.SoundManager;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.File;

public class GameOver extends JPanel implements KeyListener {
    //TODO later when back there requestFocus()
    public  static final int DEFAULT_WIDTH = 450;
    public  static final int DEFAULT_HEIGHT = 450;
    public static final int DX = 1;
    private final String TITLE = "Game Over";

    private final Image bed, frame;

    private final BackgroundDrawer BG_DRAWER;

    private SoundManager menuScroll;
    private SoundManager menuConfirm;

    private final Timer GAMEOVER_TIMER = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BG_DRAWER.update();
            repaint();
        }
    });

    private int currentChoice = 0;
    private String[] options = {
            "Restart",
            "Help",
            "Quit"
    };


    private String[] score = {
            "Score : prova",
            "Best Score : provaaa",
            "Coin : 9999"
    };

    private final Color titleColor;
    private final Font titleFont;

    private final Font font;


    public GameOver(){

        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/GameOver/GameOver_BackGround.png"),this,DX);
        bed = new ImageIcon("Resources/Backgrounds/GameOver/GameOver.gif").getImage();
        frame = new ImageIcon("Resources/Backgrounds/PictureFrame.png").getImage();


        titleColor = Color.RED;
        titleFont = new Font("04b", Font.BOLD, 50);
        font = new Font("04b", Font.PLAIN, 15);

        GAMEOVER_TIMER.start();

        menuConfirm = new SoundManager("Resources/Audio/MenuConfirm.wav");
        menuScroll =  new SoundManager("Resources/Audio/MenuScroll.wav");

        this.setPreferredSize(new Dimension(800,800));
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;



        BG_DRAWER.drawBackground(g2d);

        g2d.drawImage(bed, this.getWidth()/2-this.getWidth()/4,this.getHeight()/3-this.getHeight()/4,this.getWidth()/2,this.getHeight()/2,this);

        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), this );

        //this.drawGUI(g2d);

        StringDrawer.drawString(g2d, TITLE, titleFont, Color.WHITE,StringDrawer.TITLE_STROKE,titleColor,this.getWidth()/7, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.WHITE);
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            //g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);
            StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/2+this.getWidth()/4, this,StringDrawer.PADDALO);;
        }

        g2d.setColor(Color.BLACK);
        for(int i = 0; i < score.length; i++) {
            StringDrawer.drawString(g2d, score[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/10, this,StringDrawer.PADDALO);;
        }

    }



    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().openGameWindow();
        }
        if(currentChoice == 1) {
            // help
        }
        if(currentChoice == 2) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing to do
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            menuConfirm.playOnce();
            select();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            menuScroll.playOnce();
           currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            menuScroll.playOnce();
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //nothing to do
    }
}

