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

public class MainMenu extends JPanel implements KeyListener {

    public static final double DX = -0.1;
    private final String TITLE = "Pipi Adventure";

    private BackgroundDrawer bg;
    private Timer timerBg;

    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Help",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MainMenu(){

        bg = new BackgroundDrawer(new File("menubg.gif"),this);

        titleColor = new Color(255, 215, 0);
        titleFont = new Font("Comic Sans", Font.BOLD, 75);
        font = new Font("Arial", Font.PLAIN, 40 );

        timerBg = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bg.update();
                repaint();
            }
        });
        timerBg.start();

        this.setPreferredSize(new Dimension(800,800));
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        bg.drawBackground(g2d);
        this.drawGUI(g2d);
    }

    public void drawGUI(Graphics2D g2d){
        g2d.setColor(titleColor);
        g2d.setFont(titleFont);

        FontMetrics fm = g2d.getFontMetrics(titleFont);
        AffineTransform transform = g2d.getTransform();
        transform.translate(this.getWidth()/2 - fm.stringWidth(TITLE)/2,100+this.getHeight()/7);
        g2d.transform(transform);
        g2d.setColor(new Color(230, 172, 0));
        FontRenderContext frc = g2d.getFontRenderContext();

        TextLayout tl = new TextLayout(TITLE, g2d.getFont().deriveFont(45F), frc);
        Shape shape = tl.getOutline(null);
        g2d.setStroke(new BasicStroke(10));
        g2d.draw(shape);
        g2d.setColor(Color.yellow);
        g2d.fill(shape);


        //g2d.drawString(TITLE, this.getWidth()/2 - fm.stringWidth(TITLE)/2, 100+this.getHeight()/7);

        g2d.setFont(font);
        FontMetrics fm1 = g2d.getFontMetrics(font);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.BLACK);
            }
            else {
                g2d.setColor(Color.RED);
            }
            //g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);
            g2d.drawString(options[i], this.getWidth()/2 - fm1.stringWidth(options[i])/2, 50+i*60+this.getHeight()/3);
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

    }

}
