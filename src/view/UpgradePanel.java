package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.GameConfig;
import utils.ResourceLoader;
import utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class UpgradePanel extends ApplicationPanel{
    private final String TITLE = "Upgrade";
    //todo fix bg
    private Image BG;
    private final Color titleColor = Color.MAGENTA;
    private final int MAX_LIFE = GameConfig.getInstance().getMaxLife();
    private final int MAX_BULLET = GameConfig.getInstance().getMaxBullet();
    private final int MIN_BULLET = GameConfig.getInstance().getMinBullet();
    private int currentLife;
    private int currentBullet;
    private final Font titleFont = new Font("04b", Font.BOLD, 40);
    private final Font font = new Font("04b", Font.PLAIN, 22);
    private final Font fontSign = new Font("04b", Font.PLAIN, 12);
    private final Font fontNum = new Font("Arial", Font.PLAIN, 12);
    private int currentChoice = 0;

    private ArrayList<Rectangle2D.Double> buttons = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};
    private Image player;
    private Image heart;
    private Image bullet;
    private Image coin;
    private String lifePrice = "MAX";
    private String bulletPrice = lifePrice;
    private boolean lowbudget = false;

    private int totalCoin;


    public UpgradePanel(){
        super();
        audio.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        audio.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        int minSize = this.getWidth();
        int size = this.getHeight();
        if(size < minSize){
            minSize = size;
        }
        //todo e rimasto un grigio somewhere
        g2d.setColor(Color.BLACK);

        //draw bg, title, coin, player
        g2d.drawImage(BG,0,0,this.getWidth(),this.getHeight(),this);
        StringDrawer.drawString(g2d, TITLE, titleFont, null, StringDrawer.TITLE_STROKE, titleColor, this.getHeight() / 25, 0, this, StringDrawer.CENTER);
        StringDrawer.drawString(g2d,"Coins: "+totalCoin,font,null, StringDrawer.DEFAULT_STROKE,Color.YELLOW,this.getHeight()/4-minSize*0.05,this.getWidth()/4,this,StringDrawer.CENTER);
        g2d.drawImage(player,(int)(this.getWidth()/4-minSize*0.15),(int)(3*this.getHeight()/4-minSize*0.15),(int)(minSize*0.3),(int)(minSize*0.3),this);



        //set buyLifeButton, draw coin and heart
        buttons.get(0).setRect(this.getWidth()/4*3+minSize*0.09,this.getHeight()/2-minSize*0.16,minSize*0.13,minSize*0.06);
        g2d.drawImage(coin,(int)(this.getWidth()/4*3+minSize*0.09),(int)(this.getHeight()/2-minSize*0.15),(int)(minSize*0.04),(int)(minSize*0.04),null);
        g2d.drawImage(heart, (int)(this.getWidth()/4*3-minSize*0.23),(int)(this.getHeight()/2-minSize*0.16),(int)(minSize*0.06),(int)(minSize*0.06),null);

        //set buyBulletsButton, draw coin and bullet
        buttons.get(1).setRect((this.getWidth()/4*3+minSize*0.09),(this.getHeight()/2+minSize*0.05),(minSize*0.13),(minSize*0.06));
        g2d.drawImage(coin,(int)(this.getWidth()/4*3+minSize*0.09),(int)(this.getHeight()/2+minSize*0.06),(int)(minSize*0.04),(int)(minSize*0.04),null);
        g2d.drawImage(bullet,(int)(this.getWidth()/4*3-minSize*0.23),(int)(this.getHeight()/2+minSize*0.05),(int)(minSize*0.06),(int)(minSize*0.06),null);

        //set menu/play buttons
        buttons.get(2).setRect(this.getWidth()/2+this.getWidth()/4-minSize*0.1-StringDrawer.getStringWidth(g2d,"Menu",font)/2,this.getHeight()/2+minSize*0.25,StringDrawer.getStringWidth(g2d,"Menu",font),StringDrawer.getStringHeight(g2d,font));
        buttons.get(3).setRect(this.getWidth()/2+this.getWidth()/4+minSize*0.1-StringDrawer.getStringWidth(g2d,"Play",font)/2,this.getHeight()/2+minSize*0.25,StringDrawer.getStringWidth(g2d,"Menu",font),StringDrawer.getStringHeight(g2d,font));
//        g2d.draw(buttons.get(2));
//        g2d.draw(buttons.get(3));




        //draw fistLine pedici
        StringDrawer.drawString(g2d,"0",fontNum,null,0,Color.BLACK,this.getHeight()/2-minSize*0.11,this.getWidth()/4*3-minSize*0.16,this,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,"3",fontNum,null,0,Color.BLACK,this.getHeight()/2-minSize*0.11,this.getWidth()/4*3-minSize*0.16+minSize*0.08*MAX_LIFE-StringDrawer.getStringWidth(g2d,"3",fontNum),this,StringDrawer.PADDING);

        //draw seconLine pedici
        StringDrawer.drawString(g2d,"4",fontNum,null,0,Color.BLACK,this.getHeight()/2+minSize*0.1,this.getWidth()/4*3-minSize*0.16,this,StringDrawer.PADDING);
        StringDrawer.drawString(g2d,"10",fontNum,null,0,Color.BLACK,this.getHeight()/2+minSize*0.1,this.getWidth()/4*3-minSize*0.16+minSize*0.04*(MAX_BULLET-4)-StringDrawer.getStringWidth(g2d,"10",fontNum),this,StringDrawer.PADDING);

        for(int i = 0; i < buttons.size();i++){
            Color boundColor = Color.BLACK;
            if(i == currentChoice)
                boundColor = Color.MAGENTA;
            switch(i){
                case 0: StringDrawer.drawString(g2d,lifePrice,fontSign,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2-minSize*0.13-StringDrawer.getStringHeight(g2d,fontSign)/2,this.getWidth()/4*3+minSize*0.1+minSize*0.045,this,StringDrawer.PADDING);
                case 1: StringDrawer.drawString(g2d,bulletPrice,fontSign,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.08-StringDrawer.getStringHeight(g2d,fontSign)/2,this.getWidth()/4*3+minSize*0.1+minSize*0.045,this,StringDrawer.PADDING);
                case 2: StringDrawer.drawString(g2d,"Menu",font,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.25,this.getWidth()/4-minSize*0.1,this,StringDrawer.CENTER);
                case 3: StringDrawer.drawString(g2d,"Play",font,boundColor,StringDrawer.DEFAULT_STROKE,Color.yellow,this.getHeight()/2+minSize*0.25,this.getWidth()/4+minSize*0.1,this,StringDrawer.CENTER);
            }
        }

        for(int i = 0; i < MAX_LIFE; i++){
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.15),(int)(minSize*0.08),(int)(minSize*0.04));
            if(currentLife>i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.15),(int)(minSize*0.08),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }

        }
        for(int i = 0; i < MAX_BULLET-MIN_BULLET;i++){
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2+minSize*0.06),(int)(minSize*0.04),(int)(minSize*0.04));
            if(currentBullet-MIN_BULLET>i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2+minSize*0.06),(int)(minSize*0.04),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }
        }

//        g2d.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
//        g2d.drawLine(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
    }


    private boolean buy(int price){
        boolean bought = false;
        if(price < totalCoin){
            totalCoin-=price;
            GameEngine.getInstance().updateTotalCoin(price);
            bought = true;
        }
        return  bought;
    }
    private int calculatePrice(int step){
        int price = (int)(50*(1+Math.pow(step,2)));
        return price;
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        //updatea background
        repaint();
    }

    private void select() {
        if(currentChoice == 0) {
            if(currentLife < MAX_LIFE)
                if(buy(calculatePrice(currentLife))){
                    currentLife++;
                    if(currentLife < MAX_LIFE)
                        lifePrice = String.valueOf(calculatePrice(currentLife));
                    else
                        lifePrice = "MAX";
                    GameEngine.getInstance().updateCurrentLife();
                }else
                    lowbudget = true;
        }
        if(currentChoice == 1) {
            if(currentBullet <MAX_BULLET)
                if(buy(calculatePrice(currentBullet-MIN_BULLET))){
                    currentBullet++;
                    if(currentBullet <MAX_BULLET)
                        bulletPrice = String.valueOf(calculatePrice(currentBullet-MIN_BULLET));
                    else
                        bulletPrice = "MAX";
                    GameEngine.getInstance().updateCurrentBullets();
                }else
                    lowbudget = true;
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
                currentChoice = buttons.size() - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == buttons.size()) {
                currentChoice = 0;
            }
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
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).contains(e.getX(),e.getY())){
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
        BG = ResourceLoader.getInstance().getRes("res\\images\\backgrounds\\upgrade\\LAB_BG.gif").get(0);new ImageIcon("resources/backgrounds/Upgrade/LAB1.gif").getImage();
        //bb.updateframes = ture

        player = ResourceLoader.getInstance().getRes("res\\images\\entities\\player\\Walk\\Pinguino_Walk1.png").get(0);
        heart = ResourceLoader.getInstance().getRes("res\\images\\gameImages\\Cuoret.png").get(0);
        bullet = ResourceLoader.getInstance().getRes("res\\images\\gameImages\\Proiettile_logo.png").get(0);
        coin = ResourceLoader.getInstance().getRes("res\\images\\entities\\coin\\Moneta_img.png").get(0);

    }
}
