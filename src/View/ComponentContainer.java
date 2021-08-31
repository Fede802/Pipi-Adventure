package View;

import Commons.Pair;
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
    private ArrayList<Pair<Integer,Component>> components = new ArrayList<>();

    Timer gameOverSlidingTimer = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameOver.setLocation(gameOver.getX(), gameOver.getY() - SLIDING_STEP);
            if (gameOver.getY() <= (int)(size.getHeight()-GameOver.DEFAULT_HEIGHT)/2) {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
            }
            gameOverSliding+=SLIDING_STEP;
        }
    });
    Timer pauseSlidingTimer = new Timer(16, new ActionListener() {
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
            gameOver.setBounds((int) (size.getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) size.getHeight() - gameOverSliding, GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }else if(isGameOverSliding){
            gameOver.setBounds((int) (size.getWidth()-GameOver.DEFAULT_WIDTH)/2,(int)(size.getHeight()-GameOver.DEFAULT_HEIGHT)/2,GameOver.DEFAULT_WIDTH,GameOver.DEFAULT_HEIGHT);
        }else{
            gameOver.setBounds((int) (size.getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) size.getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }

        if(pauseSlidingTimer.isRunning()) {
            isPauseSliding = true;
            pause.setBounds((int) (size.getWidth() - Pause.DEFAULT_WIDTH) / 2, (int) size.getHeight() - pauseSliding, Pause.DEFAULT_WIDTH, Pause.DEFAULT_HEIGHT);
        }else if(isPauseSliding){
            pause.setBounds((int) (size.getWidth()-Pause.DEFAULT_WIDTH)/2,(int)(size.getHeight()-Pause.DEFAULT_HEIGHT)/2,Pause.DEFAULT_WIDTH,Pause.DEFAULT_HEIGHT);
        }else{
            pause.setBounds((int) (size.getWidth() - Pause.DEFAULT_WIDTH) / 2, (int) size.getHeight(), Pause.DEFAULT_WIDTH, Pause.DEFAULT_HEIGHT);
        }
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
            if (prev instanceof IApplicationScreen){
                ((IApplicationScreen) prev).stop();
            }
            if (curr instanceof IApplicationScreen){
                ((IApplicationScreen) curr).start();
            }
            curr.setVisible(true);
            curr.requestFocus();
        }
    }

    public void add(Pair<Integer,Component> componentPair){
        add(componentPair.getValue());
        components.add(componentPair);
        if(componentPair.getKey() == GameStateHandler.PAUSE_STATE)
            pause = componentPair.getValue();
        if(componentPair.getKey() == GameStateHandler.GAME_OVER_STATE)
            gameOver = componentPair.getValue();
    }

    public void pause(){
        isPauseSliding = true;
        pauseSlidingTimer.start();

    }

    public void gameOver(){
        isGameOverSliding = true;
        gameOverSlidingTimer.start();
    }
    public void updateGameBar(int score, int coin, int life, int bullet) {
        Component c = this.getComponent(0);
        if(c instanceof GamePanel)
            ((GamePanel) c).updateGameBar(score,coin,life,bullet);
    }


}