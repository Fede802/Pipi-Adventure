package View;

import Commons.Animation;
import Commons.EntityCoordinates;
import Controller.GameEngine;
import Controller.GameStateHandler;
import Utils.Config;
import Utils.GameConfig;
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

public class GamePanel extends JPanel implements KeyListener, ApplicationPanel {
    private final int GAME_TICK = 16;
    private int renderedTileSize;// = Config.getInstance().getRenderedTileSize();
    private final File tileSet = new File("Resources/TileSet/Tileset_Day.png");

    private final BackgroundDrawer backgroundLayer_1 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_1.png"), this, 5);
    private final BackgroundDrawer backgroundLayer_2 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_2.png"), this, 4);
    private final BackgroundDrawer backgroundLayer_3 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_3.png"), this, 3);
    private final BackgroundDrawer backgroundLayer_4 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_4.png"), this, 1);
    private final BackgroundDrawer backgroundLayer_5 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/menubg.gif"), this, 2);

    private final MapDrawer mapDrawer = new MapDrawer(this,tileSet);
    private final GameBar gameBar = new GameBar(this);

    private SoundManager level1Theme = new SoundManager("Resources/Audio/Level1.wav");

    private final Timer gameTimer = new Timer(GAME_TICK, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            renderedTileSize = GameConfig.getInstance().getRenderedTileSize();
            GameEngine.getInstance().updateGameStatus();
            backgroundLayer_1.setPaddingBottom(3*renderedTileSize);
            backgroundLayer_2.setPaddingBottom(3*renderedTileSize);
            backgroundLayer_3.setPaddingBottom(3*renderedTileSize);
            backgroundLayer_4.setPaddingBottom(3*renderedTileSize);
            backgroundLayer_5.setPaddingBottom(3*renderedTileSize);
            backgroundLayer_1.update();
            backgroundLayer_2.update();
            backgroundLayer_3.update();
            backgroundLayer_4.update();
            backgroundLayer_5.update();
            repaint();
        }
    });

    public GamePanel(){
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        backgroundLayer_5.drawBackground(g2d);
        backgroundLayer_4.drawBackground(g2d);
        backgroundLayer_3.drawBackground(g2d);
        backgroundLayer_2.drawBackground(g2d);
        backgroundLayer_1.drawBackground(g2d);
        mapDrawer.drawMap(g2d);
        gameBar.drawBar(g2d);
        EntitiesDrawer.drawEntities(g2d,this);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO later, add P for pause the game
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            System.out.println("space");
            GameEngine.getInstance().setJumping(true);
        }
        if(e.getKeyCode() == KeyEvent.VK_P)
            GameStateHandler.getInstance().pause();
//        if(e.getKeyCode() == KeyEvent.VK_Q)
//            GameEngine.getInstance().shoot();

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

