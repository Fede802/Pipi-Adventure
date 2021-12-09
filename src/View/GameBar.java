package view;

import utils.FontLoader;
import utils.GameDataConfig;
import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameBar {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final int MAX_LIFE = GameDataConfig.getInstance().getMaxLives();
    private final JPanel PARENT_PANEL;
    private final int DEFAULT_PADDING = 10;
    private final Color DEFAULT_COLOR = Color.YELLOW;
    private final Color DEFAULT_BOUND_COLOR = Color.DARK_GRAY;
    private final Font DEFAULT_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 20 );
    private final Font DEBUG_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 15);
    private final Rectangle2D.Double PAUSE_BUTTON = new Rectangle2D.Double();

    private int currentMaxLife;
    private Image coin,lifeHeart,lostLifeHeart,missingLifeHeart,pauseButton,bullet;
    private int score,coinCollected,life,bullets;

    //debug purpose
    private boolean wallCollision = true;
    private boolean infiniteBullets = false;
    private boolean entityCollision = true;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public GameBar(JPanel PARENT_PANEL){
        this.PARENT_PANEL = PARENT_PANEL;
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void setupBar(int currentLife, int currentMaxLife, int currentBullets) {
        coinCollected = 0;
        score = 0;
        life = currentLife;
        bullets = currentBullets;
        this.currentMaxLife = currentMaxLife;
    }

    public void updateBar(int score,int coinCollected,int life,int bullets) {
        this.coinCollected = coinCollected;
        this.score = score;
        this.life = life;
        this.bullets = bullets;
    }

    public void drawBar(Graphics2D g2d) {
        double fontHeight = StringDrawer.getStringHeight(g2d, DEFAULT_FONT);
        for(int i = 0; i < MAX_LIFE; i++){
            Image heart;
            if(i < life){
                heart = lifeHeart;
            }else if(i < currentMaxLife){
                heart = lostLifeHeart;
            }else{
                heart = missingLifeHeart;
            }
            g2d.drawImage(heart,(int)(DEFAULT_PADDING +(fontHeight+fontHeight/5)*i), DEFAULT_PADDING,(int)(fontHeight),(int)(fontHeight),null);
        }
        PAUSE_BUTTON.setRect((PARENT_PANEL.getWidth()-fontHeight)/2, DEFAULT_PADDING,fontHeight*2,fontHeight*2);
        g2d.drawImage(pauseButton,(int)((PARENT_PANEL.getWidth()-fontHeight*2)/2), DEFAULT_PADDING,(int)(fontHeight)*2,(int)(fontHeight)*2,null);
        StringDrawer.drawString(g2d,"Score: "+score, DEFAULT_FONT,DEFAULT_BOUND_COLOR,StringDrawer.DEFAULT_STROKE,DEFAULT_COLOR, DEFAULT_PADDING, PARENT_PANEL.getWidth()-StringDrawer.getStringWidth(g2d,"Score: "+score, DEFAULT_FONT)- DEFAULT_PADDING, PARENT_PANEL,StringDrawer.PADDING);
        int paddingLeft = (int)(PARENT_PANEL.getWidth()-StringDrawer.getStringWidth(g2d,"x"+coinCollected, DEFAULT_FONT)- DEFAULT_PADDING);
        int paddingTop = (int)(2* DEFAULT_PADDING +fontHeight);
        g2d.drawImage(bullet, DEFAULT_PADDING,paddingTop,(int)(fontHeight),(int)(fontHeight),null);
        StringDrawer.drawString(g2d,"x"+bullets, DEFAULT_FONT,DEFAULT_BOUND_COLOR,StringDrawer.DEFAULT_STROKE,DEFAULT_COLOR,paddingTop,2* DEFAULT_PADDING +fontHeight, PARENT_PANEL,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,"x"+coinCollected, DEFAULT_FONT,DEFAULT_BOUND_COLOR,StringDrawer.DEFAULT_STROKE,DEFAULT_COLOR,paddingTop,paddingLeft, PARENT_PANEL,StringDrawer.PADDING);
        g2d.drawImage(coin,(int)(paddingLeft-fontHeight/5-fontHeight),paddingTop,(int)(fontHeight),(int)(fontHeight),null);
        paddingTop = (int)(3* DEFAULT_PADDING +2*fontHeight);
        paddingLeft = 0;
        int debugint = 0;
        if(infiniteBullets){
            StringDrawer.drawString(g2d,"B", DEBUG_FONT,DEFAULT_BOUND_COLOR,StringDrawer.DEFAULT_STROKE,Color.CYAN,paddingTop, DEFAULT_PADDING, PARENT_PANEL,StringDrawer.PADDING);
            debugint++;
            paddingLeft = (int)(StringDrawer.getStringWidth(g2d,"B", DEBUG_FONT));
        }
        if(!wallCollision && !entityCollision){
            StringDrawer.drawString(g2d,"I", DEBUG_FONT,DEFAULT_BOUND_COLOR,StringDrawer.DEFAULT_STROKE,Color.RED,paddingTop,(1+debugint)* DEFAULT_PADDING +paddingLeft, PARENT_PANEL,StringDrawer.PADDING);
        }else if(!wallCollision){
            StringDrawer.drawString(g2d,"W", DEBUG_FONT,DEFAULT_BOUND_COLOR,StringDrawer.DEFAULT_STROKE,Color.ORANGE,paddingTop,(1+debugint)* DEFAULT_PADDING +paddingLeft, PARENT_PANEL,StringDrawer.PADDING);
        }else if(!entityCollision){
            StringDrawer.drawString(g2d,"E", DEBUG_FONT,DEFAULT_BOUND_COLOR,StringDrawer.DEFAULT_STROKE,Color.GREEN,paddingTop,(1+debugint)* DEFAULT_PADDING +paddingLeft, PARENT_PANEL,StringDrawer.PADDING);
        }
    }

    public Rectangle2D.Double getPauseButton() {
        return PAUSE_BUTTON;
    }

    public void loadResources() {
        coin = ImageLoader.getInstance().getImages("res\\images\\entities\\coin\\Moneta_img.png").get(0);
        lifeHeart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Cuoret.png").get(0);
        lostLifeHeart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Cuore_Vuoto.png").get(0);
        missingLifeHeart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Bordo_Cuore.png").get(0);
        pauseButton = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Pause_Button2.png").get(0);
        bullet = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Proiettile_logo.png").get(0);
    }

    //debug purpose
    public void switchWallCollision() {
        wallCollision = !wallCollision;
    }

    //debug purpose
    public void switchInfiniteBullets() {
        infiniteBullets = !infiniteBullets;
    }

    //debug purpose
    public void switchEntityCollision() {
        entityCollision = !entityCollision;
    }

    //debug purpose
    public void switchImmortality() {
        if(!wallCollision && !entityCollision){
            wallCollision = true;
            entityCollision = true;
        }else{
            wallCollision = false;
            entityCollision = false;
        }
    }

}
