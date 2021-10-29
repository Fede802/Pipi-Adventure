package view;

import utils.GameConfig;
import utils.GameDataConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class GameBar {
    private final int MAX_LIFE = GameConfig.getInstance().getMaxLife();
    private Image coin,lifeHeart,lostLifeHeart,missingLifeHeart,pauseButton,bullet;
    private final JPanel panel;
    private int score,coinCollected,life,bullets;
    private final Font  font = new Font("04b", Font.PLAIN, 20 );
    private int padding = 10;
    private Rectangle2D.Double button = new Rectangle2D.Double();

    public GameBar(JPanel panel){
        this.panel = panel;
        try {
            coin = ImageIO.read(new File("Resources/Entities/Coin/Monetona.png"));
            lifeHeart = ImageIO.read(new File("Resources/GameImages/Cuoret.png"));
            lostLifeHeart = ImageIO.read(new File("Resources/GameImages/Cuore_Vuoto.png"));
            missingLifeHeart = ImageIO.read(new File("Resources/GameImages/Bordo_Cuore.png"));
            pauseButton = ImageIO.read(new File("Resources/GameImages/Pause_Button2.png"));
            bullet = ImageIO.read(new File("Resources/GameImages/PIstola_Logo.png"));
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
    public void drawBar(Graphics2D g2d){
        double fontHeight = StringDrawer.getStringHeight(g2d,font);
        for(int i = 0; i < MAX_LIFE; i++){
            Image heart;
            if(i < life){
                heart = lifeHeart;
            }else if(i < GameConfig.getInstance().getCurrentMaxLife()){
                heart = lostLifeHeart;
            }else{
                heart = missingLifeHeart;
            }
            g2d.drawImage(heart,(int)(padding+(fontHeight+fontHeight/5)*i),padding,(int)(fontHeight),(int)(fontHeight),null);
        }
        button.setRect((panel.getWidth()-fontHeight)/2,padding,fontHeight*2,fontHeight*2);
        g2d.drawImage(pauseButton,(int)((panel.getWidth()-fontHeight*2)/2),padding,(int)(fontHeight)*2,(int)(fontHeight)*2,null);
        StringDrawer.drawString(g2d,"Score: "+score,font,Color.DARK_GRAY,StringDrawer.DEFAULT_STROKE,Color.YELLOW,padding,panel.getWidth()-StringDrawer.getStringWidth(g2d,"Score: "+score,font)-padding,panel,StringDrawer.PADDING);
        int paddingLeft = (int)(panel.getWidth()-StringDrawer.getStringWidth(g2d,"x"+coinCollected,font)-padding);
        int paddingTop = (int)(2*padding+fontHeight);
        g2d.drawImage(bullet,padding,paddingTop,(int)(fontHeight),(int)(fontHeight),null);
        StringDrawer.drawString(g2d,"x"+bullets,font,Color.DARK_GRAY,StringDrawer.DEFAULT_STROKE,Color.YELLOW,paddingTop,2*padding+fontHeight,panel,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,"x"+coinCollected,font,Color.DARK_GRAY,StringDrawer.DEFAULT_STROKE,Color.YELLOW,paddingTop,paddingLeft,panel,StringDrawer.PADDING);
        g2d.drawImage(coin,(int)(paddingLeft-fontHeight/5-fontHeight),paddingTop,(int)(fontHeight),(int)(fontHeight),null);
    }

    public Rectangle2D.Double getPauseButton() {
        return button;
    }
}
