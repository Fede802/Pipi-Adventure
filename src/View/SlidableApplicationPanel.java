package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

public abstract class SlidableApplicationPanel extends ApplicationPanel implements Slidable {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final ComponentContainer CONTAINER;

    protected int currentSlidingStep;
    protected boolean sliding;
    protected boolean onScreen;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public SlidableApplicationPanel(ComponentContainer container) {
        super();
        this.CONTAINER = container;
        setPreferredSize(new Dimension(Slidable.DEFAULT_WIDTH,Slidable.DEFAULT_HEIGHT));
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    protected void timerActionEvent(ActionEvent e) {
        if(sliding){
            if(onScreen) {
                setLocation(getX(), getY() + SLIDING_STEP);
                currentSlidingStep-=SLIDING_STEP;
                if (getY() > CONTAINER.getHeight()) {
                    stop();
                    CONTAINER.notifyResume();
                }
            }else{
                setLocation(getX(), getY() - SLIDING_STEP);
                currentSlidingStep+=SLIDING_STEP;

                if (getY() <= (CONTAINER.getHeight() - Slidable.DEFAULT_HEIGHT) / 2) {
                    currentSlidingStep = 0;
                    requestFocus();
                    sliding = false;
                    onScreen = true;
                }
            }
        }
        repaint();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        if(onScreen)
            setBounds( (getWidth()-Slidable.DEFAULT_WIDTH)/2,(getHeight()-Slidable.DEFAULT_HEIGHT)/2,Slidable.DEFAULT_WIDTH,Slidable.DEFAULT_HEIGHT);
        else if(sliding)
            setBounds( (getWidth() - Slidable.DEFAULT_WIDTH) / 2,  getHeight() - currentSlidingStep, Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
        else
            setBounds((getWidth() - Slidable.DEFAULT_WIDTH) / 2, CONTAINER.getHeight(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);
    }

    @Override
    public void stop() {
        super.stop();
        currentSlidingStep = 0;
        sliding = false;
        onScreen = false;
        setSize(CONTAINER.getSize());
    }

    @Override
    public void slide() {
        sliding = true;
        if(!onScreen){
            super.start();
        }
    }

}
