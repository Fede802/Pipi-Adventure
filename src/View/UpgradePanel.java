package view;


import controller.GameEngine;
import controller.GameStateHandler;
import utils.GameConfig;
import utils.ImageLoader;
import utils.SoundManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class UpgradePanel extends ApplicationPanel{
    private final String TITLE = "Upgrade";
    private final Color TITLE_COLOR = Color.MAGENTA;
    //todo config
    private final int DISSOLUTION_STEP = 40;
    private float dissolutionGrade = 1f;
    private int currentDissolutionStep;
    private final int MAX_LIFE = GameConfig.getInstance().getMaxLife();
    private final int MAX_BULLET = GameConfig.getInstance().getMaxBullet();
    private final int MIN_BULLET = GameConfig.getInstance().getMinBullet();
    private int currentLife;
    private int currentBullet;
    private final Font TITLE_FONT = new Font("04b", Font.BOLD, 40);
    private final Font FONT = new Font("04b", Font.PLAIN, 22);
    private final Font ERROR_FONT = new Font("04b", Font.PLAIN, 16);
    private final Font PRICE_FONT = new Font("04b", Font.PLAIN, 12);
    private final Font BAR_PLACEHOLDER_FONT = new Font("Arial", Font.PLAIN, 12);
    private int currentChoice = 0;

    private final ArrayList<Rectangle2D.Double> BUTTONS = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};
    private Image heart;
    private Image bullet;
    private Image coin;
    private String lifePrice = "MAX";
    private String bulletPrice = lifePrice;
    private boolean lowbudget = false;
    private BackgroundDrawer BG_DRAWER;
    private boolean upgrading = false;
    private Animation pipi;
    private Animation pedestal;

    private int totalCoin;

    //debug purpose
    private boolean freeUpgrade;

    public UpgradePanel(){
        super();
        audio.put(MUSIC_THEME, new SoundManager("res/audio/Upgrade_Theme.wav",SoundManager.SOUND));
        audio.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        audio.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
        audio.put(ERROR_THEME,new SoundManager("res/audio/NoCoin.wav",SoundManager.MUSIC));
        audio.put(SUCCESS_THEME,new SoundManager("res/audio/LevelUp.wav",SoundManager.MUSIC));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //todo e rimasto un grigio somewhere
        g2d.setColor(Color.BLACK);

        //draw bg, title, coin, player

        BG_DRAWER.drawBackground(g2d);
        StringDrawer.drawString(g2d, TITLE, TITLE_FONT, null, StringDrawer.TITLE_STROKE, TITLE_COLOR, this.getHeight() / 25, 0, this, StringDrawer.CENTER);
        StringDrawer.drawString(g2d,"Coins: "+totalCoin, FONT,null, StringDrawer.DEFAULT_STROKE,Color.YELLOW,this.getHeight()/4-minSize*0.05,this.getWidth()/4,this,StringDrawer.CENTER);

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
        BUTTONS.get(2).setRect(this.getWidth()/2+this.getWidth()/4-minSize*0.1-StringDrawer.getStringWidth(g2d,"Menu", FONT)/2,this.getHeight()/2+minSize*0.29,StringDrawer.getStringWidth(g2d,"Menu", FONT),StringDrawer.getStringHeight(g2d, FONT));
        BUTTONS.get(3).setRect(this.getWidth()/2+this.getWidth()/4+minSize*0.1-StringDrawer.getStringWidth(g2d,"Play", FONT)/2,this.getHeight()/2+minSize*0.29,StringDrawer.getStringWidth(g2d,"Menu", FONT),StringDrawer.getStringHeight(g2d, FONT));





        //draw fistLine pedici
        StringDrawer.drawString(g2d,"0", BAR_PLACEHOLDER_FONT,null,0,Color.BLACK,this.getHeight()/2-minSize*0.07,this.getWidth()/4*3-minSize*0.16,this,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,"3", BAR_PLACEHOLDER_FONT,null,0,Color.BLACK,this.getHeight()/2-minSize*0.07,this.getWidth()/4*3-minSize*0.16+minSize*0.08*MAX_LIFE-StringDrawer.getStringWidth(g2d,"3", BAR_PLACEHOLDER_FONT),this,StringDrawer.PADDING);

        //draw seconLine pedici
        StringDrawer.drawString(g2d,"4", BAR_PLACEHOLDER_FONT,null,0,Color.BLACK,this.getHeight()/2+minSize*0.14,this.getWidth()/4*3-minSize*0.16,this,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,"10", BAR_PLACEHOLDER_FONT,null,0,Color.BLACK,this.getHeight()/2+minSize*0.14,this.getWidth()/4*3-minSize*0.16+minSize*0.04*(MAX_BULLET-4)-StringDrawer.getStringWidth(g2d,"10", BAR_PLACEHOLDER_FONT),this,StringDrawer.PADDING);

        for(int i = 0; i < BUTTONS.size(); i++){
            Color boundColor = Color.BLACK;
            if(i == currentChoice)
                boundColor = Color.MAGENTA;
            switch(i){
                case 0: StringDrawer.drawString(g2d,lifePrice, PRICE_FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2-minSize*0.09-StringDrawer.getStringHeight(g2d, PRICE_FONT)/2,this.getWidth()/4*3+minSize*0.1+minSize*0.045,this,StringDrawer.PADDING);
                case 1: StringDrawer.drawString(g2d,bulletPrice, PRICE_FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.12-StringDrawer.getStringHeight(g2d, PRICE_FONT)/2,this.getWidth()/4*3+minSize*0.1+minSize*0.045,this,StringDrawer.PADDING);
                case 2: StringDrawer.drawString(g2d,"Menu", FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.29,this.getWidth()/4-minSize*0.1,this,StringDrawer.CENTER);
                case 3: StringDrawer.drawString(g2d,"Play", FONT,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.29,this.getWidth()/4+minSize*0.1,this,StringDrawer.CENTER);
            }
        }

        for(int i = 0; i < MAX_LIFE; i++){
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.11),(int)(minSize*0.08),(int)(minSize*0.04));
            if(currentLife>i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.11),(int)(minSize*0.08),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }

        }
        for(int i = 0; i < MAX_BULLET-MIN_BULLET;i++){
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2+minSize*0.1),(int)(minSize*0.04),(int)(minSize*0.04));
            if(currentBullet-MIN_BULLET>i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2+minSize*0.1),(int)(minSize*0.04),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }
        }
        if(lowbudget){
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, dissolutionGrade));
            StringDrawer.drawString(g2d, "Not enough coins",ERROR_FONT,null,StringDrawer.DEFAULT_STROKE,Color.RED,this.getHeight()/4-minSize*0.01+StringDrawer.getStringHeight(g2d,FONT),this.getWidth()/4,this,StringDrawer.CENTER);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
//        g2d.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
//        g2d.drawLine(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
    }


    private boolean buy(int price){
        boolean bought = false;
        if(price < totalCoin){
            totalCoin-=price;
            if(!freeUpgrade)
                GameEngine.getInstance().updateTotalCoin(price);
            bought = true;
        }
        return  bought;
    }
    private int calculatePrice(int step){
        int price = (int)(50*(1+Math.pow(step,2)));
        if(freeUpgrade)
            price = 0;
        return price;
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        BG_DRAWER.update();
        if(lowbudget){
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
    private void resetLowBudgetAnimation(){
        dissolutionGrade = 1f;
        currentDissolutionStep = 0;
        lowbudget = false;
    }
    private void select() {
        if(currentChoice == 0) {
            if(currentLife < MAX_LIFE)
                if(buy(calculatePrice(currentLife))){
                    audio.get(SUCCESS_THEME).playOnce();
                    currentLife++;
                    pipi.resetAnimation();
                    pedestal.resetAnimation();
                    upgrading = true;
                    if(currentLife < MAX_LIFE)
                        lifePrice = String.valueOf(calculatePrice(currentLife));
                    else
                        lifePrice = "MAX";
                    GameEngine.getInstance().updateCurrentLife();
                }else{
                    resetLowBudgetAnimation();
                    lowbudget = true;
                    audio.get(ERROR_THEME).playOnce();
                }
        }
        if(currentChoice == 1) {
            if(currentBullet <MAX_BULLET)
                if(buy(calculatePrice(currentBullet-MIN_BULLET))){
                    audio.get(SUCCESS_THEME).playOnce();
                    currentBullet++;
                    pipi.resetAnimation();
                    pedestal.resetAnimation();
                    upgrading = true;
                    if(currentBullet <MAX_BULLET)
                        bulletPrice = String.valueOf(calculatePrice(currentBullet-MIN_BULLET));
                    else
                        bulletPrice = "MAX";
                    GameEngine.getInstance().updateCurrentBullets();
                }else{
                    resetLowBudgetAnimation();
                    lowbudget = true;
                    audio.get(ERROR_THEME).playOnce();
                }
        }
        if(currentChoice == 2){
            GameEngine.getInstance().saveDataConfig();
            GameStateHandler.getInstance().menu();
        }
        if(currentChoice == 3) {
            GameEngine.getInstance().saveDataConfig();
            GameStateHandler.getInstance().startGame();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            audio.get(CONFIRM_THEME).playOnce();
            select();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = BUTTONS.size() - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == BUTTONS.size()) {
                currentChoice = 0;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_C){
            freeUpgrade = !freeUpgrade;
            if(currentLife < MAX_LIFE)
                lifePrice = String.valueOf(calculatePrice(currentLife));
            if(currentBullet <MAX_BULLET)
                bulletPrice = String.valueOf(calculatePrice(currentBullet-MIN_BULLET));
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        audio.get(CONFIRM_THEME).playOnce();
        select();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(int i = 0; i < BUTTONS.size(); i++){
            if(BUTTONS.get(i).contains(e.getX(),e.getY())){
                if(currentChoice != i){
                    currentChoice = i;
                    audio.get(SCROLL_THEME).playOnce();
                }
            }
        }
    }

    public void setup(int currentLife,int currentBullets,int totalCoin){
        this.totalCoin = totalCoin;
        this.currentLife = currentLife;
        this.currentBullet = currentBullets;
        if(currentLife < MAX_LIFE)
            lifePrice = String.valueOf(calculatePrice(currentLife));
        if(currentBullet <MAX_BULLET)
            bulletPrice = String.valueOf(calculatePrice(currentBullet-MIN_BULLET));

    }

    @Override
    public void loadResources() {

        BG_DRAWER = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\upgrade\\Background"),this, 0);
        BG_DRAWER.updateFrames(true);

        pipi = new Animation("res\\images\\backgrounds\\upgrade\\Pipi");
        pedestal = new Animation("res\\images\\backgrounds\\upgrade\\Pedestal");

        heart = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Cuoret.png").get(0);
        bullet = ImageLoader.getInstance().getImages("res\\images\\gameImages\\Proiettile_logo.png").get(0);
        coin = ImageLoader.getInstance().getImages("res\\images\\entities\\coin\\Moneta_img.png").get(0);

    }
}
