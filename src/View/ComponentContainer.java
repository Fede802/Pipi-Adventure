package View;

import Controller.GameStateHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


public class ComponentContainer extends JLayeredPane implements ComponentListener {



    private static final int SLIDING_STEP = 10;
    private int gameOverSliding = 0;
    private Dimension size;
    private boolean isGameOverSliding = false;

    Timer gameOverSlidingTimer = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            getComponent(GameStateHandler.GAME_OVER_STATE).setLocation(getComponent(GameStateHandler.GAME_OVER_STATE).getX(), getComponent(GameStateHandler.GAME_OVER_STATE).getY() - SLIDING_STEP);
            if (getComponent(GameStateHandler.GAME_OVER_STATE).getY() <= (int)(size.getHeight()-GameOver.DEFAULT_HEIGHT)/2) {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
            }
            gameOverSliding+=SLIDING_STEP;
        }
    });

    public ComponentContainer(){
        super();
        //this.setBounds(0,0,(int)MainFrame.MINIMUM_SIZE.getWidth(),(int)MainFrame.MINIMUM_SIZE.getHeight());
        this.setOpaque(true);
        this.addComponentListener(this);

    }


    @Override
    public void componentResized(ComponentEvent e) {
        size = this.getSize();
        System.out.println("BLA");
        for(int i = 0; i < this.getComponentCount(); i++){
            this.getComponent(i).setSize(size);
        }
        if(gameOverSlidingTimer.isRunning()) {
            isGameOverSliding = true;
            this.getComponent(GameStateHandler.GAME_OVER_STATE).setBounds((int) size.getWidth() - GameOver.DEFAULT_WIDTH / 2, (int) size.getHeight() - gameOverSliding, GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }else if(isGameOverSliding){
            this.getComponent(GameStateHandler.GAME_OVER_STATE).setBounds((int) size.getWidth()-GameOver.DEFAULT_WIDTH/2,(int)size.getHeight()-GameOver.DEFAULT_HEIGHT/2,GameOver.DEFAULT_WIDTH,GameOver.DEFAULT_HEIGHT);
        }else{
            this.getComponent(GameStateHandler.GAME_OVER_STATE).setBounds((int) size.getWidth() - GameOver.DEFAULT_WIDTH / 2, (int) size.getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }
//        revalidate();
//        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public  void switchState(){
        Component temp = this.getComponent(GameStateHandler.getInstance().getPreviousState());
        if(temp instanceof MainMenu || temp instanceof GamePanel)
            temp.setVisible(false);
        this.getComponent(GameStateHandler.getInstance().getCurrentState()).setVisible(true);
        this.getComponent(GameStateHandler.getInstance().getCurrentState()).requestFocus();
    }

    public void startGame(){
        Component temp = this.getComponent(GameStateHandler.getInstance().getCurrentState());
        if(temp instanceof GamePanel)
            ((GamePanel) temp).startGame();
    }

//    public void moveScreen(){
//        GAME_PANEL.stopGame();
//        GAME_PANEL.setOpaqueScreen(true);
//        GAME_OVER.setVisible(true);
//
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        GAME_SCREEN.moveToFront(GAME_OVER);
//        GAME_OVER.requestFocus();
//        t.start();
//        //TODO slide gameOver and maybe pause
//    }

}
