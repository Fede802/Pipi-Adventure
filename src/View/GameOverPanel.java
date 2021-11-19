package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.ResourceLoader;
import utils.SoundManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GameOverPanel extends SlidableApplicationPanel {
    private final int DX = 1;

    private final String TITLE = "Game Over";

    private Image bed, frame;

    private BackgroundDrawer BG_DRAWER;

    private final Color titleColor = Color.RED;
    private final Font titleFont = new Font("04b", Font.BOLD, 50);
    private final Font font = new Font("04b", Font.PLAIN, 21);

    private int currentChoice = 0;
    private String[] options = {
            "Reset",
            "Help",
            "Menu"
    };


    private String[] gameDataString = {
            "Score : ",
            "Record : ",
            "Coin : "
    };

    private String record = "NEW RECORD";

    private String[] gameDataValue = new String[3];

    private final ArrayList<Rectangle2D.Double> buttons = new ArrayList<>(){{add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());add(new Rectangle2D.Double());}};


    public GameOverPanel(ComponentContainer componentContainer){
        super(componentContainer);
        audio.put(MUSIC_THEME,new SoundManager("res/audio/GameOverTheme.wav",SoundManager.SOUND));
        audio.put(SCROLL_THEME,new SoundManager("res/audio/MenuScroll.wav",SoundManager.MUSIC));
        audio.put(CONFIRM_THEME,new SoundManager("res/audio/MenuConfirm.wav",SoundManager.MUSIC));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;



        BG_DRAWER.drawBackground(g2d);

        g2d.drawImage(bed, this.getWidth()/2-this.getWidth()/4,this.getHeight()/3-this.getHeight()/4,this.getWidth()/2,this.getHeight()/2,this);

        g2d.drawImage(frame,0 , 0,this.getWidth(),this.getHeight(), this );

        StringDrawer.drawString(g2d, TITLE, titleFont, Color.WHITE,StringDrawer.TITLE_STROKE,titleColor,this.getWidth()/7, 0, this,StringDrawer.CENTER);

        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g2d.setColor(Color.WHITE);
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            StringDrawer.drawString(g2d, options[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/2+this.getWidth()/4, this,StringDrawer.PADDING);
            double strWidth = StringDrawer.getStringWidth(g2d,options[i],font);
            double strHeight = StringDrawer.getStringHeight(g2d,font);
            buttons.get(i).setRect((this.getWidth()/2+this.getWidth()/4),180+i*(this.getWidth()/12)+this.getHeight()/3,strWidth,(strHeight));

        }

        g2d.setColor(Color.BLACK);
        for(int i = 0; i < gameDataString.length; i++) {
            StringDrawer.drawString(g2d, gameDataString[i] + gameDataValue[i], font, null,StringDrawer.DEFAULT_STROKE,titleColor,180+i*(this.getWidth()/12)+this.getHeight()/3, this.getWidth()/10, this,StringDrawer.PADDING);;
        }

        if (Integer.parseInt(String.valueOf(gameDataValue[0])) >= Integer.parseInt(String.valueOf(gameDataValue[1]))){
            StringDrawer.drawString(g2d, record , font, new Color(255, 0, 0), StringDrawer.DEFAULT_STROKE,Color.WHITE,180+this.getHeight()/4, 0,this,StringDrawer.CENTER);
        }

    }

    @Override
    protected void timerActionEvent(ActionEvent e) {
        super.timerActionEvent(e);
        BG_DRAWER.update();

    }

    private void select() {
        if(currentChoice == 0) {
            GameStateHandler.getInstance().startGame();
        }
        if(currentChoice == 1) {
            GameStateHandler.getInstance().openControlView();
        }
        if(currentChoice == 2) {
            GameStateHandler.getInstance().menu();
        }
        GameEngine.getInstance().saveDataConfig();
        System.out.println("datasaved");
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
                currentChoice = options.length - 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            audio.get(SCROLL_THEME).playOnce();
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        audio.get(CONFIRM_THEME).playOnce();
        select();
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

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void loadResources() {
        BG_DRAWER = new BackgroundDrawer(ResourceLoader.getInstance().getRes("res\\images\\backgrounds\\gameOver\\GameOver_BG.png"), this,DX);
        bed = ResourceLoader.getInstance().getRes("res\\images\\backgrounds\\gameOver\\GameOver.gif").get(0);
        frame = ResourceLoader.getInstance().getRes("res\\images\\backgrounds\\PictureFrame.png").get(0);

    }

    public void setData(int currentScore, int recordScore, int currentCoin) {
        gameDataValue[0]=String.valueOf(currentScore);
        gameDataValue[1]=String.valueOf(recordScore);
        gameDataValue[2]=String.valueOf(currentCoin);
    }

}
