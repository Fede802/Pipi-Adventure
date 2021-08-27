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

public class MainMenu extends JPanel implements KeyListener {

    //TODO later when back there requestFocus()
    public static final int DX = 1;
    private final String TITLE = "Pipi Adventure";

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
            "Help",
            "Quit"
    };

    private final Color titleColor;
    private final Font titleFont;
    private final Font font;

    private Image menuGIF;


    private SoundManager menuConfirm;
    private SoundManager menuScroll;




    public MainMenu(){

        BG_DRAWER = new BackgroundDrawer(new File("Resources/Backgrounds/Menu/menubg.gif"),this,DX);

        menuGIF = new ImageIcon("Resources/Backgrounds/Menu/Sfondo_Menu.gif").getImage();

        titleColor = new Color(255, 216, 0);
        titleFont = new Font("04b", Font.BOLD, 45);
        font = new Font("04b", Font.PLAIN, 30 );

        MENU_TIMER.start();

        menuConfirm = new SoundManager("Resources/Audio/MenuConfirm.wav");
        menuScroll = new SoundManager("Resources/Audio/MenuScroll.wav");

        this.setPreferredSize(new Dimension(800,800));
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        BG_DRAWER.drawBackground(g2d);
        g2d.drawImage(menuGIF, 0,100,this.getWidth(),this.getHeight()-100 , this);

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
        }
        /*Area shape = new Area(new Rectangle(0,0,this.getWidth(),this.getHeight()));
        shape.subtract(new Area(new RoundRectangle2D.Double(0,0,100,100,20,20)));
        g2d.fill(shape);*/



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

