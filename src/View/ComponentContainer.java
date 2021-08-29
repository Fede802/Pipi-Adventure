package View;

import Commons.Pairs;
import Controller.GameStateHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;


public class ComponentContainer extends JLayeredPane implements ComponentListener {

    private static final int SLIDING_STEP = 10;
    private int gameOverSliding = 0;
    private int pauseSliding = 0;
    private boolean isGameOverSliding = false;
    private boolean isPauseSliding = false;
    private Dimension size;
    private Component pause,gameOver;
    private ArrayList<Pairs<Integer,Component>> components = new ArrayList<>();


    Timer gameOverSlidingTimer = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameOver.setLocation(gameOver.getX(), gameOver.getY() - SLIDING_STEP);
            if (getComponent(GameStateHandler.GAME_OVER_STATE).getY() <= (int)(size.getHeight()-GameOver.DEFAULT_HEIGHT)/2) {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
            }
            gameOverSliding+=SLIDING_STEP;
        }
    });
    Timer PauseSlidingTimer = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pause.setLocation(pause.getX(), pause.getY() - SLIDING_STEP);
            if (pause.getY() <= (int)(size.getHeight()-Pause.DEFAULT_HEIGHT)/2) {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
            }
            pauseSliding+=SLIDING_STEP;
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
        for(int i = 0; i < this.getComponentCount(); i++){
            this.getComponent(i).setSize(size);
        }
        if(gameOverSlidingTimer.isRunning()) {
            isGameOverSliding = true;
            gameOver.setBounds((int) size.getWidth() - GameOver.DEFAULT_WIDTH / 2, (int) size.getHeight() - gameOverSliding, GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }else if(isGameOverSliding){
            gameOver.setBounds((int) size.getWidth()-GameOver.DEFAULT_WIDTH/2,(int)size.getHeight()-GameOver.DEFAULT_HEIGHT/2,GameOver.DEFAULT_WIDTH,GameOver.DEFAULT_HEIGHT);
        }else{
            gameOver.setBounds((int) size.getWidth() - GameOver.DEFAULT_WIDTH / 2, (int) size.getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }

        if(PauseSlidingTimer.isRunning()) {
            isPauseSliding = true;
            pause.setBounds((int) (size.getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) size.getHeight() - pauseSliding, GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }else if(isPauseSliding){
            pause.setBounds((int) (size.getWidth()-GameOver.DEFAULT_WIDTH)/2,(int)(size.getHeight()-GameOver.DEFAULT_HEIGHT)/2,GameOver.DEFAULT_WIDTH,GameOver.DEFAULT_HEIGHT);
        }else{
            pause.setBounds((int) (size.getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) size.getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
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
        isGameOverSliding = false;
        isPauseSliding = false;
        Component curr = null;
        Component prev = null;
        for(int i = 0; i < components.size(); i++){
            Component tempValue = components.get(i).getValue();
            Integer tempKey = components.get(i).getKey();
            if(tempKey == GameStateHandler.getInstance().getCurrentState())
                curr = tempValue;
            if(tempKey == GameStateHandler.getInstance().getPreviousState())
                prev = tempValue;
        }
        if(curr != null && prev != null){
            if(!(curr instanceof Pause || curr instanceof GameOver))
                prev.setVisible(false);
            setLayer(prev,DEFAULT_LAYER);
            setLayer(curr,DRAG_LAYER);
            curr.setVisible(true);
            curr.requestFocus();
        }
//        this.getComponent(GameStateHandler.PAUSE_STATE).setBounds((int) (size.getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) size.getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);

    }

    public void add(Pairs<Integer,Component> componentPair){
        add(componentPair.getValue());
        components.add(componentPair);
        if(componentPair.getKey() == GameStateHandler.PAUSE_STATE)
            pause = componentPair.getValue();
        if(componentPair.getKey() == GameStateHandler.GAME_OVER_STATE)
            gameOver = componentPair.getValue();
    }

    public void startGame(){
//        Component temp = this.getComponent(GameStateHandler.getInstance().getCurrentState());
        Component temp = this.getComponent(0);

        System.out.println("starting");
        if(temp instanceof GamePanel){
            ((GamePanel) temp).startGame();
            System.out.println("start");
        }
    }
    public void stopGame(){
        Component temp = this.getComponent(0);
        if(temp instanceof GamePanel)
            ((GamePanel) temp).stopGame();
    }

    public void pause(){
//        Component temp = this.getComponent(GameStateHandler.getInstance().getPreviousState());
//        this.getComponent(GameStateHandler.PAUSE_STATE).setBounds((int) (size.getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) size.getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
//        if(temp instanceof Pause)
//            moveToBack(temp);
//            ((Pause) temp).start();
//        this.setLayer(this.getComponent(GameStateHandler.getInstance().getPreviousState()),0,1);
//        this.setLayer(temp,0,0);
//        revalidate();
//        repaint();

        isPauseSliding = true;
        PauseSlidingTimer.start();

    }

    public void gameOver(){
//        Component temp = this.getComponent(GameStateHandler.getInstance().getCurrentState());
//        if(temp instanceof Pause)
//            ((Pause) temp).start();
//        this.setLayer(temp,1,0);
        isGameOverSliding = true;
        gameOverSlidingTimer.start();
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
