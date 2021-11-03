package view;

import utils.GameConfig;
import utils.GameDataConfig;
import utils.ResouceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class GameBar {
    private final int MAX_LIFE = GameConfig.getInstance().getMaxLife();
    private int currentMaxLife;
    private Image coin,lifeHeart,lostLifeHeart,missingLifeHeart,pauseButton,bullet;
    private final JPanel panel;
    private int score,coinCollected,life,bullets;
    private final Font  font = new Font("04b", Font.PLAIN, 20 );
    private int padding = 10;
    private Rectangle2D.Double button = new Rectangle2D.Double();

    public GameBar(JPanel panel){
        this.panel = panel;
    }
    public void setupBar(int currentLife, int currentMaxLife, int currentBullets){
        coinCollected = 0;
        score = 0;
        life = currentLife;
        bullets = currentBullets;
        this.currentMaxLife = currentMaxLife;
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
            }else if(i < currentMaxLife){
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

    public void loadRes(){
        coin = ResouceLoader.getInstance().getRes("res\\images\\entities\\coin\\Moneta_img.png").get(0);
        lifeHeart = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Cuoret.png").get(0);
        lostLifeHeart = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Cuore_Vuoto.png").get(0);
        missingLifeHeart = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Bordo_Cuore.png").get(0);
        pauseButton = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\Pause_Button2.png").get(0);
        bullet = ResouceLoader.getInstance().getRes("res\\images\\gameImages\\PIstola_Logo.png\n").get(0);
    }
}
