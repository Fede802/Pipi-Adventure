package View;

import Commons.Animation;
import Commons.EntityCoordinates;
import Controller.GameEngine;
import Controller.GameStateHandler;
import Utils.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, IApplicationScreen{

    public static final int GAME_TICK = 16;
    private static final ArrayList<Integer> KEYS = new ArrayList<>();
    private static final ArrayList<Animation> ANIMATIONS = new ArrayList<>();
    private static final ArrayList<EntityCoordinates> COORDS = new ArrayList<>();
    //TODO later make gameBar useful

    //TODO later, add animated player
    private final File tileSet = new File("Resources/TileSet/Tileset_Day.png");

    private final BackgroundDrawer backgroundLayer_1 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_1.png"), this, 5,3*MapDrawer.RENDERED_TILE_SIZE);
    private final BackgroundDrawer backgroundLayer_2 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_2.png"), this, 4,3*MapDrawer.RENDERED_TILE_SIZE);
    private final BackgroundDrawer backgroundLayer_3 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_3.png"), this, 3,3*MapDrawer.RENDERED_TILE_SIZE);
    private final BackgroundDrawer backgroundLayer_4 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_4.png"), this, 1,3*MapDrawer.RENDERED_TILE_SIZE);
    private final BackgroundDrawer backgroundLayer_5 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/menubg.gif"), this, 2,3*MapDrawer.RENDERED_TILE_SIZE);

    private final File coin = new File("Monetona.png");
    private final MapDrawer mapDrawer = new MapDrawer(this,tileSet);
    private final GameBar gameBar = new GameBar(this);



    private SoundManager level1Theme = new SoundManager("Resources/Audio/Level1.wav");

    private Image gun;
    private  Image bullet;

    private final Timer gameTimer = new Timer(GAME_TICK, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameEngine.getInstance().updateGameStatus();
//            System.out.println(tick);

//            gameBar.setScore(gameBar.getScore()+5);
            backgroundLayer_1.update();
            backgroundLayer_2.update();
            backgroundLayer_3.update();
            backgroundLayer_4.update();
            backgroundLayer_5.update();
            repaint();
        }
    });

    private Image player,coins,slimo;

    public GamePanel(){


        //TODO later create method to stop game and so the timer

        coins = new ImageIcon("Resources/Entities/Coin/Monetina.gif").getImage();
        slimo = new ImageIcon("Resources/Entities/Enemy/Slimo/Slime2t.gif").getImage();

        //
        gun = new ImageIcon("Resources/Entities/Player/Guns/Pistola_2.png").getImage();
        bullet = new ImageIcon("Resources/Entities/Player/Guns/Proiettile2.gif").getImage();
        //

        try {
            player = ImageIO.read(new File("Resources/Entities/Player/PInguino_Definitivo1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setFocusable(true);
        this.addKeyListener(this);
        this.setPreferredSize(MainFrame.MINIMUM_SIZE);
        this.setMinimumSize(MainFrame.MINIMUM_SIZE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
//        System.out.println("paintUpdate");
        backgroundLayer_5.drawBackground(g2d);
        backgroundLayer_4.drawBackground(g2d);
        backgroundLayer_3.drawBackground(g2d);
        backgroundLayer_2.drawBackground(g2d);
        backgroundLayer_1.drawBackground(g2d);
        mapDrawer.drawMap(g2d);
        gameBar.drawGameBar(g2d);
        EntitiesDrawer.drawEntities(g2d,this);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO later, add P for pause the game
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            GameEngine.getInstance().setJumping(true);
        if(e.getKeyCode() == KeyEvent.VK_P){
            GameStateHandler.getInstance().pause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    @Override
    public void start() {
        this.level1Theme.startLoop();
        this.gameTimer.start();
    }

    @Override
    public void stop() {
        this.level1Theme.stopLoop();
        this.gameTimer.stop();
    }
    public void updateGameBar(int score, int coin, int life, int bullet) {
        gameBar.updateBar(score,coin,life,bullet);
    }
}

