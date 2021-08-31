package View;

import Controller.GameStateHandler;
import Utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class Pause extends JPanel implements KeyListener, IApplicationScreen {
    //TODO later when back there requestFocus()
    public  static final int DEFAULT_WIDTH = 450;
    public  static final int DEFAULT_HEIGHT = 450;
    public static final int DX = 1;
    private final String TITLE = "Game Over";

    private final Image pipiRun, frame;

    private final BackgroundDrawer BG_DRAWER;

    private SoundManager menuScroll;
    private SoundManager menuConfirm;

    private final Timer PAUSE_TIMER = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BG_DRAWER.update();
            repaint();
        }
    });

    private int currentChoice = 1;
    private String[] options = {
            "Restart",
            "Resume",
            "Menu"
    };


    private final Color titleColor;
    private final Font titleFont;

    private final Font font;


    public Pause(){

        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Pause/Pause_BackGround2.png"),this,DX);
        pipiRun = new ImageIcon("Resources/Backgrounds/Pause/Pause_BackGround_GIF.gif").getImage();
        frame = new ImageIcon("Resources/Backgrounds/PictureFrame.png").getImage();


        titleColor = Color.WHITE;
        titleFont = new Font("04b", Font.BOLD, 50);
        font = new Font("04b", Font.PLAIN, 21);

        PAUSE_TIMER.start();

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

        g2d.drawImage(pipiRun ,0,this.getHeight()/2-this.getHeight()/8,this.getWidth(),this.getHeight()/2,this);

        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), this );

        //this.drawGUI(g2d);

        StringDrawer.drawString(g2d, TITLE, titleFont, Color.BLACK,StringDrawer.TITLE_STROKE,titleColor,this.getWidth()/4, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.RED);
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            //g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);
            switch(i){
                case 0:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, 50,this,StringDrawer.PADDALO);
                    break;
                case 2:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, this.getWidth()-100-StringDrawer.getStringWidth(g2d, options[i],font),this,StringDrawer.PADDALO);
                    break;
                default:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, this.getWidth()/7+i*100,this,StringDrawer.CENTER);
                    break;
            }
            //StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, this.getWidth()/7+i*100, this,StringDrawer.PADDALO);;
        }

    }

    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 2) {
            GameStateHandler.getInstance().menu();
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
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            menuScroll.playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
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

    @Override
    public void start() {
        //TODO musichina
        this.PAUSE_TIMER.start();
    }

    @Override
    public void stop() {
        this.PAUSE_TIMER.stop();
    }
}


