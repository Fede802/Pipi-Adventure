package View;


import Controller.GameEngine;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener {

    public static final int GAME_TICK = 16;
    //TODO later make gameBar useful
    //TODO whenRefactor, use background class to draw background
    //TODO later, add animated player
    private final File tileSet = new File("Tileset2.png");
    private final File coin = new File("Monetona.png");
    private final MapDrawer mapDrawer = new MapDrawer(this,tileSet);
    private final GameBar gameBar = new GameBar(this,coin);
    private final Timer gameTimer = new Timer(GAME_TICK, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameEngine.getInstance().updateGameStatus();
            repaint();
        }
    });

    private Image player;

    public GamePanel(){
        //TODO timer and override paint

        //TODO later create method to stop game and so the timer
//        gameTimer.start();

        try {
            player = ImageIO.read(new File("PInguino_Definitivo4.png"));
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
        mapDrawer.drawMap(g2d);
        gameBar.drawGameBar(g2d);
        int[] playerInfo = GameEngine.getInstance().getPlayerInfo();
        //TODO later, maybe move positioning of constant RENDERED_TILE_SIZE?
        //TODO later, find a way to get player startMapX instead of currentMapX
        g2d.drawImage(player,(/*playerInfo[1]-1*/2)*MapDrawer.RENDERED_TILE_SIZE,this.getHeight()-(GameEngine.getInstance().getSectionSize()-playerInfo[3])*MapDrawer.RENDERED_TILE_SIZE+playerInfo[4],MapDrawer.RENDERED_TILE_SIZE,MapDrawer.RENDERED_TILE_SIZE,null);
    }

    public void startGame(){
        gameTimer.start();
        this.requestFocus();
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }
}
