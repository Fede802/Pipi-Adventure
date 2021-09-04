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

public class MainMenu extends JPanel implements KeyListener, MouseInputListener, ApplicationPanel {

    private final int DX = 1;
    private final String TITLE = "Pipi Adventure";
    private final ArrayList<Rectangle2D.Double> buttons = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};

    private final SoundManager menuScroll = new SoundManager("Resources/Audio/MenuConfirm.wav");
    private final SoundManager menuConfirm = new SoundManager("Resources/Audio/MenuScroll.wav");
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
            "Start",
            "Upgrade",
            "Help",
            "Quit"
    };

    private final Color titleColor = new Color(255, 216, 0);
    private final Font titleFont = new Font("04b", Font.BOLD, 45);
    private final Font font = new Font("04b", Font.PLAIN,30);

    private Image menuGIF;

    public MainMenu(){

        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Menu/menubg.gif"),this,DX);
        menuGIF = new ImageIcon("Resources/Backgrounds/Menu/Sfondo_Menu.gif").getImage();

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



        g2d.drawImage(menuGIF, 0,100,this.getWidth(),this.getHeight()-100 , null);

        StringDrawer.drawString(g2d, TITLE, titleFont, new Color(255, 120, 0),StringDrawer.TITLE_STROKE,titleColor,100, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.DARK_GRAY);
            }
            else {
                g2d.setColor(new Color(255, 120, 0));
            }
            //g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);

            StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,50+i*60+this.getHeight()/3, 0, this,StringDrawer.CENTER);
            double strWidth = StringDrawer.getStringWidth(g2d,options[i],font);
            double strHeight = StringDrawer.getStringHeight(g2d,font);
            buttons.get(i).setRect((this.getWidth()-strWidth)/2,50+i*60+this.getHeight()/3,strWidth,(strHeight));
        }
        /*Area shape = new Area(new Rectangle(0,0,this.getWidth(),this.getHeight()));
        shape.subtract(new Area(new RoundRectangle2D.Double(0,0,100,100,20,20)));
        g2d.fill(shape);*/



    }





    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            // upgrade
        }
        if(currentChoice == 2){
            //help
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


