package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.ImageLoader;
import utils.SoundManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class GamePanel extends ApplicationPanel {

    private final MapDrawer MAP_DRAWER = new MapDrawer(this);
    private final GameBar GAME_BAR = new GameBar(this);
    private final EntitiesDrawer ENTITIES_DRAWER = new EntitiesDrawer(this);
    private final Rectangle2D.Double PAUSE_BUTTON;

    private int renderedTileSize;
    private boolean loadedRes = false;
    private boolean running = true;
    private BackgroundDrawer backgroundLayer_1;
    private BackgroundDrawer backgroundLayer_2;
    private BackgroundDrawer backgroundLayer_3;
    private BackgroundDrawer backgroundLayer_4;
    private BackgroundDrawer backgroundLayer_5;

    public GamePanel() {
        super();
        AUDIO.put(MUSIC_THEME,new SoundManager("res/audio/Level1.wav",SoundManager.SOUND));
        PAUSE_BUTTON = GAME_BAR.getPauseButton();
    }

    public void setupGameBar(int currentLife, int currentMaxLife, int currentBullets) {
        GAME_BAR.setupBar(currentLife,currentMaxLife,currentBullets);
    }

    public void updateGameBar(int score, int coin, int life, int bullet) {
        GAME_BAR.updateBar(score,coin,life,bullet);
    }

    public void updateTileSize(int renderedTileSize) {
        this.renderedTileSize = renderedTileSize;
        ENTITIES_DRAWER.updateRenderedTileSize(renderedTileSize);
        MAP_DRAWER.updateRenderedTileSize(renderedTileSize);
        if(loadedRes) {
            backgroundLayer_1.setPaddingBottom(3 * renderedTileSize);
            backgroundLayer_2.setPaddingBottom(3 * renderedTileSize);
            backgroundLayer_3.setPaddingBottom(3 * renderedTileSize);
            backgroundLayer_4.setPaddingBottom(3 * renderedTileSize);
            backgroundLayer_5.setPaddingBottom(3 * renderedTileSize);
        }
    }

    public void setGameRunning(boolean running){
        this.running = running;
    }

    public void updateDayTime() {
        backgroundLayer_1.updateFrames();
        backgroundLayer_2.updateFrames();
        backgroundLayer_3.updateFrames();
        backgroundLayer_4.updateFrames();
        backgroundLayer_5.updateFrames();
        MAP_DRAWER.updateTileset();
    }

    public void setupDayTime() {
        backgroundLayer_1.reset();
        backgroundLayer_2.reset();
        backgroundLayer_3.reset();
        backgroundLayer_4.reset();
        backgroundLayer_5.reset();
        MAP_DRAWER.reset();
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
            MAP_DRAWER.update();
        }
        repaint();
    }

    @Override
    public void loadResources() {
        MAP_DRAWER.loadResources();
        ENTITIES_DRAWER.loadResources();
        GAME_BAR.loadResources();
        backgroundLayer_1 = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\game\\Layer1"), this, 5,3*renderedTileSize);
        backgroundLayer_2 = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\game\\Layer2"), this, 4,3*renderedTileSize);
        backgroundLayer_3 = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\game\\Layer3"), this, 3,3*renderedTileSize);
        backgroundLayer_4 = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\game\\Layer4"), this, 1,3*renderedTileSize);
        backgroundLayer_5 = new BackgroundDrawer(ImageLoader.getInstance().getImages("res\\images\\backgrounds\\game\\Sky"), this, 2,3*renderedTileSize);
        loadedRes = true;
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
        MAP_DRAWER.drawMap(g2d);
        GAME_BAR.drawBar(g2d);
        ENTITIES_DRAWER.drawEntities(g2d);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            GameEngine.getInstance().jump();
        }
        if(e.getKeyCode() == KeyEvent.VK_P)
            GameStateHandler.getInstance().pause();
        if(e.getKeyCode() == KeyEvent.VK_Q)
            GameEngine.getInstance().shoot();
        if(e.getKeyCode() == KeyEvent.VK_I) {
            GAME_BAR.switchImmortality();
            GameEngine.getInstance().switchImmortality();
        }
        //debug purpose
        if(e.getKeyCode() == KeyEvent.VK_W){
            GAME_BAR.switchWallCollision();
            GameEngine.getInstance().switchWallCollision();
        }
        //debug purpose
        if(e.getKeyCode() == KeyEvent.VK_E){
            GAME_BAR.switchEntityCollision();
            GameEngine.getInstance().switchEntityCollision();
        }
        //debug purpose
        if(e.getKeyCode() == KeyEvent.VK_B){
            GAME_BAR.switchInfiniteBullets();
            GameEngine.getInstance().switchInfiniteBullets();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            if (GameStateHandler.PAUSE_STATE != GameStateHandler.getInstance().getCurrentState() && this.PAUSE_BUTTON.contains(e.getX(),e.getY())){
                GameStateHandler.getInstance().pause();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            if (!this.PAUSE_BUTTON.contains(e.getX(),e.getY()))
                GameEngine.getInstance().jump();
        }else if (e.getButton() == MouseEvent.BUTTON3){
            GameEngine.getInstance().shoot();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //nothing to do
    }

}
