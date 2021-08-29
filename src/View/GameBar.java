package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class GameBar {
    //TODO later refactor this class
    private final JPanel panel;
    private int score,coinCollected;
    private Image coin;
    private final Font font;
    private int leftShift;
    private int padding = 10;

    public GameBar(JPanel panel, File coin){
        this.panel = panel;


        importImage(coin);
        font = new Font("Arial", Font.PLAIN, 20 );
    }
    private void importImage(File coin){
        try {
            this.coin = ImageIO.read(new File("Resources/Entities/Coin/Monetona.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCoinCollected() {
        return coinCollected;
    }

    public void setCoinCollected(int coinCollected) {
        this.coinCollected = coinCollected;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void drawGameBar(Graphics2D g2d){

        drawString(g2d,"Score: "+score,font,Color.DARK_GRAY,Color.YELLOW,0);
        drawString(g2d,"x"+coinCollected,font,Color.DARK_GRAY,Color.YELLOW,1);
        g2d.drawImage(coin,leftShift-30,0,25,25,null);

    }
    //TODO when merged, make a StringDrawer class
    private void drawString(Graphics2D g2d,String text,Font font, Color boundColor, Color fillColor, int i){
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();
        AffineTransform affineTransform = g2d.getTransform();

        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics(font);
        int downShift = fm.getAscent();
        leftShift = padding;
        if(i == 1){
            leftShift = panel.getWidth()-fm.stringWidth(text)-2*padding;
        }
        affineTransform.translate(leftShift,downShift);
        g2d.setTransform(affineTransform);
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(text, g2d.getFont(), frc);
        Shape shape = tl.getOutline(null);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(boundColor);
        g2d.draw(shape);
        g2d.setColor(fillColor);
        g2d.fill(shape);

        affineTransform.translate(-affineTransform.getTranslateX(),-affineTransform.getTranslateY());
        g2d.setTransform(affineTransform);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }
}
