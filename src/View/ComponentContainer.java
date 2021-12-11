package view;

import controller.GameEngine;
import controller.GameStateHandler;
import utils.GameDataConfig;
import javax.swing.JLayeredPane;
import javax.swing.Timer;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;

public class ComponentContainer extends JLayeredPane implements ComponentListener {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final HashMap<Integer, Component> COMPONENTS = new HashMap<>();
    private final int CLOSING_STEPS = 40;
    private final Rectangle2D.Double TEMP_RECT = new Rectangle2D.Double();
    private final RoundRectangle2D.Double TEMP_ROUND_RECT = new RoundRectangle2D.Double();
    private final Timer TIMER = new Timer(IApplicationPanel.TIMER_TICK, e -> repaint());

    private Component prev,curr;
    private boolean closing = true,transition,notifyChangingScreen;
    private int transitionRectWidth, transitionRectHeight;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public ComponentContainer() {
        super();
        this.addComponentListener(this);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void add(Integer key, Component component) {
        component.setVisible(false);
        add(component);
        COMPONENTS.put(key,component);
    }

    public void loadResources() {
        setupState();
        curr.setVisible(true);
        setLayer(curr,DRAG_LAYER);
        curr.requestFocus();
        ((ApplicationPanel) curr).start();
    }

    public void startApplication() {
        setupState();
        curr.setVisible(true);
        prev.setVisible(false);
        setLayer(prev,DEFAULT_LAYER);
        ((ApplicationPanel) prev).stop();
        setLayer(curr,DRAG_LAYER);
        curr.requestFocus();
        ((ApplicationPanel) curr).start();
    }

    public void switchState() {
        setupState();
        this.requestFocus();
        ((IApplicationPanel) prev).stop();
        if(curr instanceof Slidable){
            curr.setVisible(true);
            setLayer(prev,DEFAULT_LAYER);
            setLayer(curr,DRAG_LAYER);
            ((Slidable) curr).slide();
        }else if(curr instanceof IApplicationPanel){
            transition = true;
            TIMER.start();
        }
    }

    public void resumePreviousState() {
        setupState();
        if(prev instanceof Slidable){
            this.requestFocus();
            ((Slidable) prev).slide();
        }
    }

    public void notifyResume() {
        openCurrentState();
        curr.requestFocus();
        ((IApplicationPanel) curr).start();

    }

    public void hasToNotifyChangingScreen(boolean notify) {
        notifyChangingScreen = notify;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.setSize(Math.round(this.getWidth()),Math.round(this.getHeight()));
        if(!transition){
            transitionRectHeight = this.getHeight();
            transitionRectWidth = this.getWidth();
        }
        for(int i = 0; i < this.getComponentCount(); i++) {
            this.getComponent(i).setSize(this.getSize());
        }
        GameEngine.getInstance().notifySizeChanged((int) (Math.min(this.getWidth(),this.getHeight())/GameDataConfig.getInstance().getMinTileToRender()));
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //nothing to do
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //nothing to do
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //nothing to do
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if(transition){
            TEMP_RECT.setRect(0,0,this.getWidth(),this.getHeight());
            Area shape = new Area(TEMP_RECT);
            TEMP_ROUND_RECT.setRoundRect((this.getWidth()- transitionRectWidth)/2,(this.getHeight()- transitionRectHeight)/2, transitionRectWidth, transitionRectHeight,20,20);
            shape.subtract(new Area(TEMP_ROUND_RECT));
            g2d.fill(shape);
            if(closing){
                if(transitionRectHeight - CLOSING_STEPS < 0 || transitionRectWidth - CLOSING_STEPS < 0){
                    closing = false;
                    if(notifyChangingScreen){
                        GameStateHandler.getInstance().notifyChangingScreen();
                        notifyChangingScreen = false;
                    }
                    openCurrentState();
                }else{
                    transitionRectWidth -= CLOSING_STEPS;
                    transitionRectHeight -= CLOSING_STEPS;
                }
            }else{
                if(transitionRectHeight + CLOSING_STEPS > this.getHeight() || transitionRectWidth + CLOSING_STEPS > this.getWidth()){
                    closing = true;
                    transition = false;
                    curr.requestFocus();
                    TIMER.stop();
                    if(curr instanceof ApplicationPanel)
                        ((ApplicationPanel) curr).start();

                }else{
                    transitionRectWidth += CLOSING_STEPS;
                    transitionRectHeight += CLOSING_STEPS;
                }
            }
        }
    }

    private void openCurrentState() {
        prev.setVisible(false);
        curr.setVisible(true);
        setLayer(prev,DEFAULT_LAYER);
        setLayer(curr,DRAG_LAYER);
    }

    private void setupState() {
        prev = curr;
        curr = null;
        int currentState = GameStateHandler.getInstance().getCurrentState();
        if(COMPONENTS.containsKey(currentState))
            curr = COMPONENTS.get(currentState);
    }

}
