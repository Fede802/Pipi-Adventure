package View;

import Controller.GameStateHandler;
import Utils.SoundManager;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GameOver extends JPanel implements KeyListener, MouseInputListener, Slidable, ApplicationPanel{
    private final int DX = 1;

    private final String TITLE = "Game Over";

    private final Image bed, frame;

    private final BackgroundDrawer BG_DRAWER;

    private SoundManager menuScroll = new SoundManager("Resources/Audio/MenuConfirm.wav");
    private SoundManager menuConfirm = new SoundManager("Resources/Audio/MenuScroll.wav");
    private SoundManager gameoverTheme = new SoundManager("Resources/Audio/GameOverTheme.wav");

    private final int SLIDING_STEP = 10;
    private int currentSlidingStep;
    public static boolean isSliding = false;
    public static boolean isOnScreen = false;

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

    private final Timer GAME_OVER_TIMER = new Timer(16, new ActionListener() {
        //TODO maybe instead of using size take container as a params
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isSliding){
                if(isOnScreen) {
                    setLocation(getX(), getY() + SLIDING_STEP);
                    currentSlidingStep-=SLIDING_STEP;
                    setBounds((int) (ComponentContainer.size.getWidth() - DEFAULT_WIDTH) / 2, (int) getY(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
                    if (getY() > (int) (ComponentContainer.size.getHeight())) {
                        ((Timer) e.getSource()).stop();
                        setBounds((getWidth() - DEFAULT_WIDTH) / 2, (int) ComponentContainer.size.getHeight(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
                        currentSlidingStep = 0;
                        gameoverTheme.stopLoop();
                        isSliding = false;
                        isOnScreen = false;
                    }
                }else{
                    setLocation(getX(), getY() - SLIDING_STEP);
                    currentSlidingStep+=SLIDING_STEP;
                    setBounds((int) (ComponentContainer.size.getWidth() - DEFAULT_WIDTH) / 2, (int) ComponentContainer.size.getHeight() - currentSlidingStep, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                    if (getY() <= (int) (ComponentContainer.size.getHeight() - DEFAULT_HEIGHT) / 2) {
                        currentSlidingStep = 0;
                        requestFocus();
                        isSliding = false;
                        isOnScreen = true;
                    }
                }
            }
            BG_DRAWER.update();
            repaint();
        }
    });

    public GameOver(){
        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/GameOver/GameOver_BackGround.png"),this,DX);
        bed = new ImageIcon("Resources/Backgrounds/GameOver/GameOver.gif").getImage();
        frame = new ImageIcon("Resources/Backgrounds/PictureFrame.png").getImage();

        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
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

    @Override
    public void slide() {
        isSliding = true;
        if(!isOnScreen){
            gameoverTheme.startLoop();
            GAME_OVER_TIMER.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    @Override
    public boolean isOnScreen() {
        return isOnScreen;
    }

    @Override
    public boolean isSliding() {
        return isSliding;
    }

    @Override
    public int getSlidingStep() {
        return currentSlidingStep;
    }

    @Override
    public void start() {
        GAME_OVER_TIMER.start();
        gameoverTheme.startLoop();
    }

    @Override
    public void stop() {
        GAME_OVER_TIMER.stop();
        gameoverTheme.stopLoop();
    }

}
