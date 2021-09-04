package View;

import Controller.GameStateHandler;
import Utils.SoundManager;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PausePanel extends JPanel implements KeyListener, MouseInputListener, Slidable, ApplicationPanel {
    private final int DX = 1;

    private final String TITLE = "Pause";

    private final Image pipiRun, frame;

    private final BackgroundDrawer BG_DRAWER;

    private final SoundManager menuScroll = new SoundManager("Resources/Audio/MenuConfirm.wav");
    private final SoundManager menuConfirm = new SoundManager("Resources/Audio/MenuScroll.wav");
    private final SoundManager pauseTheme = new SoundManager("Resources/Audio/Pause_Theme.wav");

    private final int SLIDING_STEP = 10;
    private int currentSlidingStep;
    public static boolean isSliding = false;
    public static boolean isOnScreen = false;

    private final Color titleColor = Color.WHITE;
    private final Font titleFont = new Font("04b", Font.BOLD, 50);
    private final Font font = new Font("04b", Font.PLAIN, 21);

    private int currentChoice = 1;
    private String[] options = {
            "Restart",
            "Resume",
            "Menu"
    };
    private final Timer PAUSE_TIMER = new Timer(16, new ActionListener() {
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
                        pauseTheme.stopLoop();
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

    public PausePanel(){

        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Pause/Pause_BackGround2.png"),this,DX);
        pipiRun = new ImageIcon("Resources/Backgrounds/Pause/Pause_BackGround_GIF.gif").getImage();
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
                case 0:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, 50,this,StringDrawer.PADDING);
                    break;
                case 2:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, this.getWidth()-100-StringDrawer.getStringWidth(g2d, options[i],font),this,StringDrawer.PADDING);
                    break;
                default:StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, this.getWidth()/7+i*100,this,StringDrawer.CENTER);
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
    public void slide() {
        isSliding = true;
        if(!isOnScreen){
            pauseTheme.startLoop();
            PAUSE_TIMER.start();
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
        PAUSE_TIMER.start();
        pauseTheme.startLoop();
    }

    @Override
    public void stop() {
        PAUSE_TIMER.stop();
        pauseTheme.stopLoop();
    }
}
