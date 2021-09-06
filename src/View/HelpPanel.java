package View;

import Controller.GameStateHandler;
import Utils.SoundManager;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

public class HelpPanel extends JPanel implements KeyListener, MouseInputListener, ApplicationPanel {

    private final int DX = 1;
    private final String TITLE = "Controls";
    private final ArrayList<Rectangle2D.Double> buttons = new ArrayList<>(){{add(new Rectangle2D.Double());}};

    private final SoundManager menuConfirm = new SoundManager("Resources/Audio/MenuConfirm.wav");
    private final SoundManager menuScroll = new SoundManager("Resources/Audio/MenuScroll.wav");
    private final SoundManager menuTheme = new SoundManager("Resources/Audio/Title_Theme.wav");

    private final BackgroundDrawer BG_DRAWER;
    private final Timer MENU_TIMER = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BG_DRAWER.update();
            repaint();
        }
    });

    private int currentChoice = 0;
    private String[] options = {
            "Jump",
            "Shoot",
            "Pause",
    };

    private final Color titleColor = Color.RED;
    private final Font titleFont = new Font("04b", Font.BOLD, 40);
    private final Font font = new Font("04b", Font.PLAIN,25);

    private final Image leftClick;
    private final Image rightClick;
    private final Image pauseClick;
    private final Image qButton;
    private final Image pButton;
    private final Image spaceButton;

    //private final JButton homeButton;


    public HelpPanel(){

        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Help/grey"),this,DX);
/*
        homeButton = new JButton();
        homeButton.addKeyListener(this);
        homeButton.setBounds(this.getHeight()/2, this.getWidth()/2,200, 200);
        homeButton.setFocusable(false);
        homeButton.setVisible(true);
        this.add(homeButton);
 */
        leftClick = new ImageIcon("Resources/GameImages/Left_Click.png").getImage();
        rightClick = new ImageIcon("Resources/GameImages/Right_Click.png").getImage();
        pauseClick = new ImageIcon("Resources/GameImages/Pause_Click.png").getImage();

        qButton = new ImageIcon("Resources/GameImages/Q_button.png").getImage();
        pButton = new ImageIcon("Resources/GameImages/P_button.png").getImage();
        spaceButton = new ImageIcon("Resources/GameImages/Space_button.png").getImage();


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

        StringDrawer.drawString(g2d, TITLE, titleFont, null,StringDrawer.TITLE_STROKE,titleColor,this.getHeight()/25, 0, this,StringDrawer.CENTER);


        StringDrawer.drawString(g2d, options[0], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/6, this.getWidth()/10, this,StringDrawer.DEFAULT_STROKE);;
        StringDrawer.drawString(g2d, options[1], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/6, 0, this,StringDrawer.CENTER);;
        StringDrawer.drawString(g2d, options[2], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/6,this.getWidth()-StringDrawer.getStringWidth(g2d,"Pause",font) - this.getWidth()/10, this,StringDrawer.DEFAULT_STROKE);;

        StringDrawer.drawString(g2d, options[0], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, this.getWidth()/10, this,StringDrawer.DEFAULT_STROKE);;
        StringDrawer.drawString(g2d, options[1], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2, 0, this,StringDrawer.CENTER);;
        StringDrawer.drawString(g2d, options[2], font, null,StringDrawer.DEFAULT_STROKE,titleColor,this.getHeight()/2,this.getWidth()-StringDrawer.getStringWidth(g2d,"Pause",font) - this.getWidth()/10, this,StringDrawer.DEFAULT_STROKE);;


        g2d.drawImage(leftClick , this.getWidth()/13, this.getHeight()/4,this.getHeight()/6,this.getHeight()/6,this);
        g2d.drawImage(rightClick , this.getWidth()/2 - (rightClick.getWidth(this)/6), this.getHeight()/4,this.getHeight()/6,this.getHeight()/6,this);
        g2d.drawImage(pauseClick , this.getWidth()/2 + this.getWidth()/3, this.getHeight()/4,this.getHeight()/6,this.getHeight()/6,this);

        g2d.drawImage(spaceButton , this.getWidth()/13, this.getHeight()/2 + this.getHeight()/10,this.getHeight()/6,this.getHeight()/6,this);
        g2d.drawImage(qButton , this.getWidth()/2 - (rightClick.getWidth(this)/6), this.getHeight()/2 + this.getHeight()/10,this.getHeight()/6,this.getHeight()/6,this);
        g2d.drawImage(leftClick , this.getWidth()/13, this.getHeight()/2 + this.getHeight()/10,this.getHeight()/6,this.getHeight()/6,this);
    }

    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            // upgrade
        }
        if(currentChoice == 2){
            GameStateHandler.getInstance().openControlView();
        }
        if(currentChoice == 3) {
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

            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {

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
        menuTheme.startLoop();
        this.MENU_TIMER.start();
    }

    @Override
    public void stop() {
        menuTheme.stopLoop();
        this.MENU_TIMER.stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        menuConfirm.playOnce();
        select();
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
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).contains(e.getX(),e.getY())){
                if(currentChoice != i){
                    currentChoice = i;
                    menuScroll.playOnce();
                }
            }
        }
    }
}


