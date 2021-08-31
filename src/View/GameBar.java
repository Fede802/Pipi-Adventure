package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameBar {
    private Image coin, hearth;
    private final JPanel panel;
    private int score,coinCollected,life,bullets;
    private final Font  font = new Font("04b", Font.PLAIN, 20 );;

    private int padding = 10;
    public GameBar(JPanel panel){
        this.panel = panel;
        try {
            coin = ImageIO.read(new File("Resources/Entities/Coin/Monetona.png"));
            hearth = ImageIO.read(new File("Resources/Entities/Player/Cuoret.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void updateBar(int score,int coinCollected,int life,int bullets){
        this.coinCollected = coinCollected;
        this.score = score;
        this.life = life;
        this.bullets = bullets;
    }
    public void drawGameBar(Graphics2D g2d){
        for(int i = 0; i < life; i++){
            g2d.drawImage(hearth,padding+60*i,padding,50,50,null);
        }
        StringDrawer.drawString(g2d,"Score: "+score,font,Color.DARK_GRAY,StringDrawer.DEFAULT_STROKE,Color.YELLOW,padding,panel.getWidth()-StringDrawer.getStringWidth(g2d,"Score: "+score,font)-padding,panel,StringDrawer.PADDALO);
        int paddingLeft = (int)(panel.getWidth()-StringDrawer.getStringWidth(g2d,"x"+coinCollected,font)-padding);
        int paddingTop = (int)(2*padding+StringDrawer.getStringHeight(g2d,font));
        StringDrawer.drawString(g2d,"x"+coinCollected,font,Color.DARK_GRAY,StringDrawer.DEFAULT_STROKE,Color.YELLOW,paddingTop,paddingLeft,panel,StringDrawer.PADDALO);
        g2d.drawImage(coin,paddingLeft-35,paddingTop,25,25,null);
    }
}
