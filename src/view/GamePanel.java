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

    private int renderedTileSize;
    private boolean isRunning = false;

    private final File tileSet = new File("Resources/TileSet/Tileset_Day.png");
    private final BackgroundDrawer backgroundLayer_1 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_1.png"), this, 5,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_2 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_2.png"), this, 4,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_3 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_3.png"), this, 3,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_4 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/Nuovo_Sfondo_4.png"), this, 1,3*renderedTileSize);
    private final BackgroundDrawer backgroundLayer_5 = new BackgroundDrawer(new File("Resources/Backgrounds/Game/Day/menubg.gif"), this, 2,3*renderedTileSize);
    private final MapDrawer mapDrawer = new MapDrawer(this,tileSet);
    private final GameBar gameBar = new GameBar(this);
    private final EntitiesDrawer entitiesDrawer = new EntitiesDrawer();
    private final Rectangle2D.Double pauseButton;



    public GamePanel(){
        super(); //redundant call to the superclass constructor
        audio.put(MUSIC_THEME,new SoundManager("resources/audio/Level1.wav"));
        renderedTileSize = GameDataConfig.getInstance().getRenderedTileSize();
        pauseButton = gameBar.getPauseButton();
    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        GameEngine.getInstance().updateGameStatus();
        backgroundLayer_1.update();
        backgroundLayer_2.update();
        backgroundLayer_3.update();
        backgroundLayer_4.update();
        backgroundLayer_5.update();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        System.out.println("repaint");
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
            }else{
                GameEngine.getInstance().setJumping(true);
            }
        }else if (e.getButton() == MouseEvent.BUTTON3){
            GameEngine.getInstance().shoot();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
