package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.GameConfig;
import utils.GameDataConfig;
import utils.SoundManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class GamePanel extends ApplicationPanel{
    int count = 0;
    private int renderedTileSize;
    private boolean isRunning = false;

    private final File tileSet = new File("Resources/TileSet/Tileset_Day.png");
    private final BackgroundDrawer backgroundLayer_1 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Layer1"), this, 5,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_2 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Layer2"), this, 4,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_3 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Layer3"), this, 3,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_4 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Layer4"), this, 1,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_5 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Sky"), this, 2,3*renderedTileSize);
    private final MapDrawer mapDrawer = new MapDrawer(this,tileSet);
    private final GameBar gameBar = new GameBar(this);
    private final EntitiesDrawer entitiesDrawer = new EntitiesDrawer();
    private final Rectangle2D.Double pauseButton;
    private boolean running = true;



    public GamePanel(){
        super(); //redundant call to the superclass constructor
        audio.put(MUSIC_THEME,new SoundManager("resources/audio/Level1.wav"));
        renderedTileSize = GameDataConfig.getInstance().getRenderedTileSize();
        pauseButton = gameBar.getPauseButton();
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        GameEngine.getInstance().updateGameStatus();
        if (running) {
            backgroundLayer_1.update();
            backgroundLayer_2.update();
            backgroundLayer_3.update();
            backgroundLayer_4.update();
            backgroundLayer_5.update();
        }

        repaint();
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
        entitiesDrawer.drawEntities(g2d,this);

    }

    public void updateGameBar(int score, int coin, int life, int bullet) {
        gameBar.updateBar(score,coin,life,bullet);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            GameEngine.getInstance().setJumping(true);
        }
        if(e.getKeyCode() == KeyEvent.VK_P)
            GameStateHandler.getInstance().pause();
        if(e.getKeyCode() == KeyEvent.VK_Q)
            GameEngine.getInstance().shoot();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            if (GameStateHandler.PAUSE_STATE != GameStateHandler.getInstance().getCurrentState() && this.pauseButton.contains(e.getX(),e.getY())){
                GameStateHandler.getInstance().pause();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            if (!this.pauseButton.contains(e.getX(),e.getY()))
                GameEngine.getInstance().setJumping(true);
        }else if (e.getButton() == MouseEvent.BUTTON3){
            GameEngine.getInstance().shoot();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void updateTileSize(){
        renderedTileSize = GameDataConfig.getInstance().getRenderedTileSize();
        backgroundLayer_1.setPaddingBottom(3*renderedTileSize);
        backgroundLayer_2.setPaddingBottom(3*renderedTileSize);
        backgroundLayer_3.setPaddingBottom(3*renderedTileSize);
        backgroundLayer_4.setPaddingBottom(3*renderedTileSize);
        backgroundLayer_5.setPaddingBottom(3*renderedTileSize);
        mapDrawer.updateRenderedTileSize(renderedTileSize);
        entitiesDrawer.updateRenderedTileSize(renderedTileSize);
        System.out.println(renderedTileSize+"updategamepanel");
    }


    void setGameRunning(boolean running){
        this.running = running;
    }


    public void updateDayTime() {
        backgroundLayer_1.updateFrames();
        backgroundLayer_2.updateFrames();
        backgroundLayer_3.updateFrames();
        backgroundLayer_4.updateFrames();
        backgroundLayer_5.updateFrames();
    }

    public void setupDayTime() {
        backgroundLayer_1.reset();
        backgroundLayer_2.reset();
        backgroundLayer_3.reset();
        backgroundLayer_4.reset();
        backgroundLayer_5.reset();
    }

    public void setupGameBar(int currentLife, int currentMaxLife, int currentBullets) {
        gameBar.setupBar(currentLife,currentMaxLife,currentBullets);
    }
}
