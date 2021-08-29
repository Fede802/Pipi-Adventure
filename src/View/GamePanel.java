package View;


import Commons.Animation;
import Commons.EntityCoordinates;
import Commons.Pairs;
import Controller.GameEngine;
import Controller.GameStateHandler;
import Utils.SoundManager;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GamePanel extends JPanel implements KeyListener, IApplicationScreen{

    public static final int GAME_TICK = 16;
    int tick;
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
    private final GameBar gameBar = new GameBar(this,coin);
    private boolean opaqueScreen = false;

    private SoundManager level1Theme = new SoundManager("Resources/Audio/Level1.wav");

    private Image gun;
    private  Image bullet;

    private final Timer gameTimer = new Timer(GAME_TICK, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameEngine.getInstance().updateGameStatus();
            System.out.println(tick);
            tick++;
            if(tick == 4)
                tick = 0;

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
        System.out.println("paintUpdate");
        backgroundLayer_5.drawBackground(g2d);
        backgroundLayer_4.drawBackground(g2d);
        backgroundLayer_3.drawBackground(g2d);
        backgroundLayer_2.drawBackground(g2d);
        backgroundLayer_1.drawBackground(g2d);
        mapDrawer.drawMap(g2d);
//        gameBar.drawGameBar(g2d);
        //int[] playerInfo = GameEngine.getInstance().getPlayerInfo();
        //TODO later, maybe move positioning of constant RENDERED_TILE_SIZE?
        //TODO later, find a way to get player startMapX instead of currentMapX
        ArrayList<Pairs<EntityCoordinates,Animation>> entityCoordinates = GameEngine.getInstance().getEntityCoordinates();
//        for(int i = 0; i < entityCoordinates.size(); i++){
//            KEYS.add(entityCoordinates.get(i).getKey());
//            ANIMATIONS.add(entityCoordinates.get(i).getValue().getValue());
//            COORDS.add(entityCoordinates.get(i).getValue().getKey());
//
//        }
        EntityCoordinates entityPos;

        g2d.drawImage(gun, 3 * MapDrawer.RENDERED_TILE_SIZE -5, this.getHeight() - (GameEngine.getInstance().getSectionSize() - 12) * MapDrawer.RENDERED_TILE_SIZE , MapDrawer.RENDERED_TILE_SIZE, MapDrawer.RENDERED_TILE_SIZE, null);
        g2d.drawImage(bullet, 3 * MapDrawer.RENDERED_TILE_SIZE -20, this.getHeight() - (GameEngine.getInstance().getSectionSize() - 12) * MapDrawer.RENDERED_TILE_SIZE, MapDrawer.RENDERED_TILE_SIZE, MapDrawer.RENDERED_TILE_SIZE, null);

        for (Pairs<EntityCoordinates, Animation> entityCoordinate : entityCoordinates) {
            entityPos = entityCoordinate.getKey();
            if (entityCoordinate.getKey().getID() == 0) {
//                System.out.println("pipi");

                g2d.drawImage(entityCoordinate.getValue().getFrame(), entityPos.getSTART_MAP_X() * MapDrawer.RENDERED_TILE_SIZE, this.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * MapDrawer.RENDERED_TILE_SIZE + entityPos.getTraslY(), MapDrawer.RENDERED_TILE_SIZE, MapDrawer.RENDERED_TILE_SIZE, null);
            } else {

//                System.out.println("x: "+((entityPos.getSTART_MAP_X()+GameEngine.getInstance().getSectionSize()*entityPos.getMapIndex())) +" y: "+(this.getHeight()-(GameEngine.getInstance().getSectionSize()- entityPos.getMapY())*MapDrawer.RENDERED_TILE_SIZE));
                g2d.drawImage(entityCoordinate.getValue().getFrame(), (entityPos.getSTART_MAP_X() + GameEngine.getInstance().getSectionSize() * entityPos.getMapIndex()) * MapDrawer.RENDERED_TILE_SIZE - GameEngine.getInstance().getMapTraslX() + entityPos.getTraslX(), this.getHeight() - (GameEngine.getInstance().getSectionSize() - entityPos.getMapY()) * MapDrawer.RENDERED_TILE_SIZE - entityPos.getTraslY(), mapDrawer.RENDERED_TILE_SIZE, mapDrawer.RENDERED_TILE_SIZE, null);
            }
            if(tick == 3){
                entityCoordinate.getValue().update();

            }
        }





//        g2d.drawImage(player,(entityPos.getSTART_MAP_X())*MapDrawer.RENDERED_TILE_SIZE,this.getHeight()-(GameEngine.getInstance().getSectionSize()- entityPos.getMapY())*MapDrawer.RENDERED_TILE_SIZE+ entityPos.getTraslY(),MapDrawer.RENDERED_TILE_SIZE,MapDrawer.RENDERED_TILE_SIZE,null);
//        if(opaqueScreen){
//            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
//            g2d.setColor(Color.black);
//            g2d.fillRect(0,0,this.getWidth(), this.getHeight());
//            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//        }
    }
    public void setOpaqueScreen(final boolean opaqueScreen){
        this.opaqueScreen = opaqueScreen;
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
        tick = 0;
    }
}
