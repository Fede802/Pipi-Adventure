package view;

import utils.GameConfig;
import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameBar {

    private final int MAX_LIFE = GameConfig.getInstance().getMaxLife();
    private int currentMaxLife;
    private Image coin,lifeHeart,lostLifeHeart,missingLifeHeart,pauseButton,bullet;
    private final JPanel panel;
    private int score,coinCollected,life,bullets;
    private final Font  font = new Font("04b", Font.PLAIN, 20 );
    private final int padding = 10;
    private final Rectangle2D.Double button = new Rectangle2D.Double();

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

    public void loadResources(){
        coin = ImageLoader.getInstance().getImages("res\\images\\entities\\coin\\Moneta_img.png").get(0);
        lifeHeart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Cuoret.png").get(0);
        lostLifeHeart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Cuore_Vuoto.png").get(0);
        missingLifeHeart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Bordo_Cuore.png").get(0);
        pauseButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Pause_Button2.png").get(0);
        bullet = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Proiettile_logo.png").get(0);
    }
}
