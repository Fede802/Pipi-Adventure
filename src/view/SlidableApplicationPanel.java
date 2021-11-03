package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class SlidableApplicationPanel extends ApplicationPanel implements Slidable{

    protected int currentSlidingStep;
    protected boolean isSliding = false;
    protected boolean isOnScreen = false;
    protected ComponentContainer componentContainer;

    public SlidableApplicationPanel(ComponentContainer componentContainer){
        super();
        this.componentContainer = componentContainer;
        setPreferredSize(new Dimension(Slidable.DEFAULT_WIDTH,Slidable.DEFAULT_HEIGHT));
    }
    @Override
    protected void timerActionEvent(ActionEvent e) {
        if(isSliding){
            if(isOnScreen) {
                setLocation(getX(), getY() + SLIDING_STEP);
                currentSlidingStep-=SLIDING_STEP;
//                setBounds((int) (componentContainer.getWidth() - Slidable.DEFAULT_WIDTH) / 2, (int) getY(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
                if (getY() > (int) (componentContainer.getHeight())) {
                    stop();
//                    setBounds((getWidth() - Slidable.DEFAULT_WIDTH) / 2, componentContainer.getHeight(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
                    componentContainer.notifyResume();
                }
            }else{
                setLocation(getX(), getY() - SLIDING_STEP);
                currentSlidingStep+=SLIDING_STEP;
//                setBounds((int) (componentContainer.getWidth() - Slidable.DEFAULT_WIDTH) / 2, componentContainer.getHeight() - currentSlidingStep, Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
                if (getY() <= (int) (componentContainer.getHeight() - Slidable.DEFAULT_HEIGHT) / 2) {
                    currentSlidingStep = 0;
                    requestFocus();
                    isSliding = false;
                    isOnScreen = true;
                }
            }
        }
        repaint();
    }

    @Override
    public void slide() {
        isSliding = true;
        if(!isOnScreen){
            super.start();
        }
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        if(isOnScreen)
            setBounds((int) (getWidth()-Slidable.DEFAULT_WIDTH)/2,(int)(getHeight()-Slidable.DEFAULT_HEIGHT)/2,Slidable.DEFAULT_WIDTH,Slidable.DEFAULT_HEIGHT);
        else if(isSliding)
            setBounds((int) (getWidth() - Slidable.DEFAULT_WIDTH) / 2, (int) getHeight() - currentSlidingStep, Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
        else
            setBounds((getWidth() - Slidable.DEFAULT_WIDTH) / 2, componentContainer.getHeight(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
    }

    @Override
    public void stop(){
        super.stop();
//        setBounds((getWidth() - Slidable.DEFAULT_WIDTH) / 2, (int) ComponentContainers.size.getHeight(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
        currentSlidingStep = 0;
        isSliding = false;
        isOnScreen = false;
        //todo fix without sfarfallamento
        setSize(componentContainer.getSize());
//        setBounds((getWidth() - Slidable.DEFAULT_WIDTH) / 2, componentContainer.getHeight(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
    }
}
