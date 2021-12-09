package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class SlidableApplicationPanel extends ApplicationPanel implements Slidable {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final ComponentContainer componentContainer;

    protected int currentSlidingStep;
    protected boolean isSliding = false;
    protected boolean isOnScreen = false;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public SlidableApplicationPanel(ComponentContainer componentContainer) {
        super();
        this.componentContainer = componentContainer;
        setPreferredSize(new Dimension(Slidable.DEFAULT_WIDTH,Slidable.DEFAULT_HEIGHT));
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    protected void timerActionEvent(ActionEvent e) {
        if(isSliding){
            if(isOnScreen) {
                setLocation(getX(), getY() + SLIDING_STEP);
                currentSlidingStep-=SLIDING_STEP;
                if (getY() > componentContainer.getHeight()) {
                    stop();
                    componentContainer.notifyResume();
                }
            }else{
                setLocation(getX(), getY() - SLIDING_STEP);
                currentSlidingStep+=SLIDING_STEP;

                if (getY() <= (componentContainer.getHeight() - Slidable.DEFAULT_HEIGHT) / 2) {
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
    public void setSize(Dimension d) {
        super.setSize(d);
        if(isOnScreen)
            setBounds( (getWidth()-Slidable.DEFAULT_WIDTH)/2,(getHeight()-Slidable.DEFAULT_HEIGHT)/2,Slidable.DEFAULT_WIDTH,Slidable.DEFAULT_HEIGHT);
        else if(isSliding)
            setBounds( (getWidth() - Slidable.DEFAULT_WIDTH) / 2,  getHeight() - currentSlidingStep, Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
        else
            setBounds((getWidth() - Slidable.DEFAULT_WIDTH) / 2, componentContainer.getHeight(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
    }

    @Override
    public void stop() {
        super.stop();
        currentSlidingStep = 0;
        isSliding = false;
        isOnScreen = false;
        setSize(componentContainer.getSize());
    }

    @Override
    public void slide() {
        isSliding = true;
        if(!isOnScreen){
            super.start();
        }
    }

}
