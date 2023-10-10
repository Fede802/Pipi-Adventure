package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.FontLoader;
import utils.GameConfig;
import utils.GameDataConfig;
import utils.ImageLoader;
import utils.SoundManager;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class UpgradePanel extends ApplicationPanel {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final String TITLE = "Upgrade";
    private final Color DEFAULT_COLOR = Color.MAGENTA;
    private final Font TITLE_FONT = new Font(FontLoader.GAME_FONT, Font.BOLD, 40);
    private final Font DEFAULT_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 22);
    private final Font ERROR_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 16);
    private final Font PRICE_FONT = new Font(FontLoader.GAME_FONT, Font.PLAIN, 12);
    private final Font BAR_PLACEHOLDER_FONT = new Font("Arial", Font.PLAIN, 12);
    private final int MAX_LIVES = GameDataConfig.getInstance().getMaxLives();
    private final int MAX_BULLETS = GameDataConfig.getInstance().getMaxBullets();
    private final int MIN_BULLETS = GameDataConfig.getInstance().getMinBullets();
    private final int DISSOLUTION_STEP = GameConfig.getInstance().getDissolutionStep();
    private final ArrayList<Rectangle2D.Double> BUTTONS = new ArrayList<>(){{
        add(new Rectangle2D.Double());
        add(new Rectangle2D.Double());
        add(new Rectangle2D.Double());
        add(new Rectangle2D.Double());
    }};
    private final Stroke BOX_STROKE = new BasicStroke(4);

    private float dissolutionGrade = 1f;
    private int currentDissolutionStep;
    private int currentLives;
    private int currentBullets;
    private int currentChoice = 0;
    private Image heart;
    private Image bullet;
    private Image coin;
    private String lifePrice = "MAX";
    private String bulletPrice = lifePrice;
    private boolean lowBudget;
    private BackgroundDrawer background;
    private boolean upgrading;
    private Animation pipi;
    private Animation pedestal;
    private int totalCoins;

    //debug purpose
    private boolean freeUpgrade;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public UpgradePanel() {
        super();
        AUDIO.put(MUSIC_THEME, new SoundManager("res/audio/Upgrade_Theme.wav",SoundManager.SOUND));
        AUDIO.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        AUDIO.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
        AUDIO.put(ERROR_THEME,new SoundManager("res/audio/NoCoin.wav",SoundManager.MUSIC));
        AUDIO.put(SUCCESS_THEME,new SoundManager("res/audio/LevelUp.wav",SoundManager.MUSIC));
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void setup(int currentLives,int currentBullets,int totalCoins) {
        this.totalCoins = totalCoins;
        this.currentLives = currentLives;
        this.currentBullets = currentBullets;
        if(currentLives < MAX_LIVES)
            lifePrice = String.valueOf(calculatePrice(currentLives));
        if(this.currentBullets < MAX_BULLETS)
            bulletPrice = String.valueOf(calculatePrice(this.currentBullets - MIN_BULLETS));

    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        background.update();
        if(lowBudget){
            if(currentDissolutionStep == DISSOLUTION_STEP){
                resetLowBudgetAnimation();
            }
            if(currentDissolutionStep > DISSOLUTION_STEP/2){
                if(currentDissolutionStep%2 == 0)
                    dissolutionGrade-=0.1f;
            }

            currentDissolutionStep++;
        }
        if (upgrading) {
            pipi.update();
            pedestal.update();
            if (pipi.isFinish()) {
                upgrading = false;
                pipi.resetAnimation();
                pedestal.resetAnimation();
            }
        }
        repaint();
    }

    @Override
    public void loadResources() {
        background = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\upgrade\\Background"),this, 0);
        background.updateFrames(true, false);
        pipi = new Animation("res\\images\\backgrounds\\upgrade\\Pipi");
        pedestal = new Animation("res\\images\\backgrounds\\upgrade\\Pedestal");
        heart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Cuoret.png").get(0);
        bullet = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Proiettile_logo.png").get(0);
        coin = ImageLoader.getInstance().getImages("res\\images\\entities\\coin\\Moneta_img.png").get(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(BOX_STROKE);

        //draw bg, title, coin, player

        background.drawBackground(g2d);
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, Color.BLACK, StringDrawer.TITLE_STROKE, DEFAULT_COLOR, this.getHeight() / 25, 0, this, StringDrawer.CENTER);
        StringDrawer.drawString(g2d,"Coins: "+ totalCoins, DEFAULT_FONT,Color.BLACK, StringDrawer.DEFAULT_STROKE,Color.YELLOW,this.getHeight()/4-minSize*0.05,this.getWidth()/4,this,StringDrawer.CENTER);
        g2d.drawImage(pedestal.getFrame(),(int)(this.getWidth()/4-minSize*0.15),this.getHeight()-(minSize/4),(int)(minSize*0.5),minSize/4,this);
        g2d.drawImage(pipi.getFrame(),(int)(this.getWidth()/4-minSize*0.09),this.getHeight()-((3*minSize/10)+(9*minSize/128)),3*minSize/10,3*minSize/10,this);

        //set buyLifeButton, draw coin and heart
        BUTTONS.get(0).setRect(this.getWidth()/4*3+minSize*0.09,this.getHeight()/2-minSize*0.12,minSize*0.13,minSize*0.06);
        g2d.drawImage(coin,(int)(this.getWidth()/4*3+minSize*0.09),(int)(this.getHeight()/2-minSize*0.11),(int)(minSize*0.04),(int)(minSize*0.04),null);
        g2d.drawImage(heart, (int)(this.getWidth()/4*3-minSize*0.23),(int)(this.getHeight()/2-minSize*0.12),(int)(minSize*0.06),(int)(minSize*0.06),null);

        //set buyBulletsButton, draw coin and bullet
        BUTTONS.get(1).setRect((this.getWidth()/4*3+minSize*0.09),(this.getHeight()/2+minSize*0.09),(minSize*0.13),(minSize*0.06));
        g2d.drawImage(coin,(int)(this.getWidth()/4*3+minSize*0.09),(int)(this.getHeight()/2+minSize*0.1),(int)(minSize*0.04),(int)(minSize*0.04),null);
        g2d.drawImage(bullet,(int)(this.getWidth()/4*3-minSize*0.23),(int)(this.getHeight()/2+minSize*0.09),(int)(minSize*0.06),(int)(minSize*0.06),null);

        //set menu/play buttons
        BUTTONS.get(2).setRect(this.getWidth()/2+this.getWidth()/4-minSize*0.1-StringDrawer.getStringWidth(g2d,"Menu", DEFAULT_FONT)/2,this.getHeight()/2+minSize*0.29,StringDrawer.getStringWidth(g2d,"Menu", DEFAULT_FONT),StringDrawer.getStringHeight(g2d, DEFAULT_FONT));
        BUTTONS.get(3).setRect(this.getWidth()/2+this.getWidth()/4+minSize*0.1-StringDrawer.getStringWidth(g2d,"Play", DEFAULT_FONT)/2,this.getHeight()/2+minSize*0.29,StringDrawer.getStringWidth(g2d,"Menu", DEFAULT_FONT),StringDrawer.getStringHeight(g2d, DEFAULT_FONT));

        //draw fistLine placeholder
        StringDrawer.drawString(g2d,"0", BAR_PLACEHOLDER_FONT,Color.BLACK,0,Color.BLACK,this.getHeight()/2-minSize*0.07,this.getWidth()/4*3-minSize*0.16,this,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,String.valueOf(MAX_LIVES), BAR_PLACEHOLDER_FONT,Color.BLACK,0,Color.BLACK,this.getHeight()/2-minSize*0.07,this.getWidth()/4*3-minSize*0.16+minSize*0.08* MAX_LIVES -StringDrawer.getStringWidth(g2d,"3", BAR_PLACEHOLDER_FONT),this,StringDrawer.PADDING);

        //draw secondLine placeholder
        StringDrawer.drawString(g2d,String.valueOf(MIN_BULLETS), BAR_PLACEHOLDER_FONT,Color.BLACK,0,Color.BLACK,this.getHeight()/2+minSize*0.14,this.getWidth()/4*3-minSize*0.16,this,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,String.valueOf(MAX_BULLETS), BAR_PLACEHOLDER_FONT,Color.BLACK,0,Color.BLACK,this.getHeight()/2+minSize*0.14,this.getWidth()/4*3-minSize*0.16+minSize*0.04*(MAX_BULLETS -4)-StringDrawer.getStringWidth(g2d,"10", BAR_PLACEHOLDER_FONT),this,StringDrawer.PADDING);

        for(int i = 0; i < BUTTONS.size(); i++){
            Color boundColor = Color.BLACK;
            if(i == currentChoice)
                boundColor = Color.MAGENTA;
            switch(i){
                case 0: StringDrawer.drawString(g2d,lifePrice, PRICE_FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2-minSize*0.09-StringDrawer.getStringHeight(g2d, PRICE_FONT)/2,this.getWidth()/4*3+minSize*0.1+minSize*0.045,this,StringDrawer.PADDING);
                case 1: StringDrawer.drawString(g2d,bulletPrice, PRICE_FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.12-StringDrawer.getStringHeight(g2d, PRICE_FONT)/2,this.getWidth()/4*3+minSize*0.1+minSize*0.045,this,StringDrawer.PADDING);
                case 2: StringDrawer.drawString(g2d,"Menu", DEFAULT_FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.29,this.getWidth()/4-minSize*0.1,this,StringDrawer.CENTER);
                case 3: StringDrawer.drawString(g2d,"Play", DEFAULT_FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.29,this.getWidth()/4+minSize*0.1,this,StringDrawer.CENTER);
            }
        }
        for(int i = 0; i < MAX_LIVES; i++) {
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.11),(int)(minSize*0.08),(int)(minSize*0.04));
            if(currentLives >i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.11),(int)(minSize*0.08),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }

        }
        for(int i = 0; i < MAX_BULLETS - MIN_BULLETS; i++) {
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2+minSize*0.1),(int)(minSize*0.04),(int)(minSize*0.04));
            if(currentBullets - MIN_BULLETS >i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2+minSize*0.1),(int)(minSize*0.04),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }
        }
        if(lowBudget) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, dissolutionGrade));
            StringDrawer.drawString(g2d, "Not enough coins",ERROR_FONT,Color.BLACK,StringDrawer.DEFAULT_STROKE,Color.RED,this.getHeight()/4-minSize*0.01+StringDrawer.getStringHeight(g2d, DEFAULT_FONT),this.getWidth()/4,this,StringDrawer.CENTER);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            AUDIO.get(CONFIRM_THEME).playOnce();
            select();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            AUDIO.get(SCROLL_THEME).playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = BUTTONS.size() - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            AUDIO.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == BUTTONS.size()) {
                currentChoice = 0;
            }
        }
        //debug purpose
        if(e.getKeyCode() == KeyEvent.VK_C) {
            freeUpgrade = !freeUpgrade;
            if(currentLives < MAX_LIVES)
                lifePrice = String.valueOf(calculatePrice(currentLives));
            if(currentBullets < MAX_BULLETS)
                bulletPrice = String.valueOf(calculatePrice(currentBullets - MIN_BULLETS));

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AUDIO.get(CONFIRM_THEME).playOnce();
        select();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(int i = 0; i < BUTTONS.size(); i++){
            if(BUTTONS.get(i).contains(e.getX(),e.getY())){
                if(currentChoice != i){
                    currentChoice = i;
                    AUDIO.get(SCROLL_THEME).playOnce();
                }
            }
        }
    }

    private void select() {
        if(currentChoice == 0) {
            if(currentLives < MAX_LIVES)
                if(buy(calculatePrice(currentLives))){
                    AUDIO.get(SUCCESS_THEME).playOnce();
                    currentLives++;
                    pipi.resetAnimation();
                    pedestal.resetAnimation();
                    upgrading = true;
                    if(currentLives < MAX_LIVES)
                        lifePrice = String.valueOf(calculatePrice(currentLives));
                    else
                        lifePrice = "MAX";
                    GameEngine.getInstance().updateCurrentLives();
                }else{
                    resetLowBudgetAnimation();
                    lowBudget = true;
                    AUDIO.get(ERROR_THEME).playOnce();
                }
        }
        if(currentChoice == 1) {
            if(currentBullets < MAX_BULLETS)
                if(buy(calculatePrice(currentBullets - MIN_BULLETS))){
                    AUDIO.get(SUCCESS_THEME).playOnce();
                    currentBullets++;
                    pipi.resetAnimation();
                    pedestal.resetAnimation();
                    upgrading = true;
                    if(currentBullets < MAX_BULLETS)
                        bulletPrice = String.valueOf(calculatePrice(currentBullets - MIN_BULLETS));
                    else
                        bulletPrice = "MAX";
                    GameEngine.getInstance().updateCurrentBullets();
                }else{
                    resetLowBudgetAnimation();
                    lowBudget = true;
                    AUDIO.get(ERROR_THEME).playOnce();
                }
        }
        if(currentChoice == 2){
            GameEngine.getInstance().saveDataConfig();
            GameStateHandler.getInstance().openMenuPanel();
        }
        if(currentChoice == 3) {
            GameEngine.getInstance().saveDataConfig();
            GameStateHandler.getInstance().startGame();
        }
    }

    private boolean buy(int price) {
        boolean bought = false;
        if(price < totalCoins){
            totalCoins -=price;
            //todo useless if but semplify methods stack
            if(!freeUpgrade)
                GameEngine.getInstance().updateTotalCoins(price);
            bought = true;
        }
        return  bought;
    }

    private int calculatePrice(int step) {
        int price = (int)(50*(1+Math.pow(step,2)));
        if(freeUpgrade)
            price = 0;
        return price;
    }

    private void resetLowBudgetAnimation()  {
        dissolutionGrade = 1f;
        currentDissolutionStep = 0;
        lowBudget = false;
    }

}
