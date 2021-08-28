package View;

import Controller.GameStateHandler;

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
        titleFont = new Font("04b", Font.BOLD, 60);
        font = new Font("04b", Font.PLAIN, 20);

        GAMEOVER_TIMER.start();

        this.setPreferredSize(new Dimension(800,800));
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        BG_DRAWER.drawBackground(g2d);

        g2d.drawImage(bed, this.getWidth()/2-this.getWidth()/4,this.getHeight()/3-this.getHeight()/4,this.getWidth()/2,this.getHeight()/2,null);

        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), null );

        //this.drawGUI(g2d);

        StringDrawer.drawString(g2d, TITLE, titleFont, Color.WHITE,StringDrawer.TITLE_STROKE,titleColor,65, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.WHITE);
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            //g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);
            StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*60+this.getHeight()/3, 470, this,StringDrawer.PADDALO);;
        }

        g2d.setColor(Color.BLACK);
        for(int i = 0; i < score.length; i++) {
            StringDrawer.drawString(g2d, score[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*60+this.getHeight()/3, 25, this,StringDrawer.PADDALO);;
        }

    }

    public void drawGUI(Graphics2D g2d){


        g2d.setColor(titleColor);
        g2d.setFont(titleFont);

        FontMetrics fm = g2d.getFontMetrics(titleFont);
        AffineTransform transform = g2d.getTransform();
        transform.translate(this.getWidth()/2 - fm.stringWidth(TITLE)/2,100+this.getHeight()/7);
        g2d.transform(transform);
        g2d.setColor(new Color(100, 0, 0));
        FontRenderContext frc = g2d.getFontRenderContext();


        TextLayout tl = new TextLayout(TITLE, g2d.getFont().deriveFont(45F), frc);
        Shape shape = tl.getOutline(null);
        g2d.setStroke(new BasicStroke(10));
        g2d.draw(shape);
        g2d.setColor(Color.RED);
        g2d.fill(shape);


        //g2d.drawString(TITLE, this.getWidth()/2 - fm.stringWidth(TITLE)/2, 100+this.getHeight()/7);

        g2d.setFont(font);
        FontMetrics fm1 = g2d.getFontMetrics(font);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(new Color(255, 198, 26));
            }
            else {
                g2d.setColor(Color.WHITE);
            }
            //g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);
            g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);
        }

        g2d.setColor(Color.WHITE);
        for(int i = 0; i < score.length; i++) {
            g2d.drawString(score[i], this.getWidth()/6 - fm1.stringWidth(score[i])/2, 50+i*60+this.getHeight()/3);
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
}

