package view;

import commons.Pair;
import controller.GameEngine;
import controller.GameStateHandler;
import utils.GameDataConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class ComponentContainer extends JLayeredPane implements ComponentListener {

    private Component prev,curr;
    private boolean isClosing = true,transition = false,notifyChangingScreen=false;
    private final ArrayList<Pair<Integer,Component>> components = new ArrayList<>();
    private int transitionRectWidth, transitionRectHeight;
    private final int CLOSING_STEP = 40;
    private final Rectangle2D.Double tempRect = new Rectangle2D.Double();
    private final RoundRectangle2D.Double tempRoundRect = new RoundRectangle2D.Double();
    //todo timer delay config?
    private final Timer TIMER = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    public ComponentContainer(){
        super();
        this.addComponentListener(this);
    }

    public void add(Pair<Integer,Component> componentPair){
        add(componentPair.getValue());
        components.add(componentPair);
    }

    private void setupState(){
        prev = curr;
        curr = null;
        for(int i = 0; i < components.size(); i++){
            Component tempValue = components.get(i).getValue();
            Integer tempKey = components.get(i).getKey();
            if(tempKey == GameStateHandler.getInstance().getCurrentState())
                curr = tempValue;
        }
    }

    public void loadResources() {
        setupState();
        curr.setVisible(true);
        setLayer(curr,DRAG_LAYER);
        curr.requestFocus();
        ((ApplicationPanel) curr).start();
    }

    public void startApplication(){
        setupState();
        curr.setVisible(true);
        prev.setVisible(false);
        setLayer(prev,DEFAULT_LAYER);
        ((ApplicationPanel) prev).stop();
        setLayer(curr,DRAG_LAYER);
        curr.requestFocus();
        ((ApplicationPanel) curr).start();
    }

    public void switchState(){
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

    public void resumePreviousState(){
        setupState();
        if(prev instanceof Slidable){
            this.requestFocus();
            ((Slidable) prev).slide();
        }
    }
    public void notifyResume(){
        openCurrentState();
        curr.requestFocus();
        ((IApplicationPanel) curr).start();

    }
    private void openCurrentState(){
        prev.setVisible(false);
        curr.setVisible(true);
        setLayer(prev,DEFAULT_LAYER);
        setLayer(curr,DRAG_LAYER);
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
            tempRect.setRect(0,0,this.getWidth(),this.getHeight());
            Area shape = new Area(tempRect);
            tempRoundRect.setRoundRect((this.getWidth()- transitionRectWidth)/2,(this.getHeight()- transitionRectHeight)/2, transitionRectWidth, transitionRectHeight,20,20);
            shape.subtract(new Area(tempRoundRect));
            g2d.fill(shape);
            if(isClosing){
                if(transitionRectHeight -CLOSING_STEP < 0 || transitionRectWidth -CLOSING_STEP < 0){
                    isClosing = false;
                    if(notifyChangingScreen){
                        GameStateHandler.getInstance().notifyChangingScreen();
                        notifyChangingScreen = false;
                    }
                    openCurrentState();
                }else{
                    transitionRectWidth -=CLOSING_STEP;
                    transitionRectHeight -=CLOSING_STEP;
                }
            }else{
                if(transitionRectHeight +CLOSING_STEP > this.getHeight() || transitionRectWidth +CLOSING_STEP > this.getWidth()){
                    isClosing = true;
                    transition = false;
                    curr.requestFocus();
                    TIMER.stop();
                    if(curr instanceof ApplicationPanel)
                        ((ApplicationPanel) curr).start();

                }else{
                    transitionRectWidth +=CLOSING_STEP;
                    transitionRectHeight +=CLOSING_STEP;
                }
            }
        }

    }

    public void hasToNotifyChangingScreen(boolean notify) {
        notifyChangingScreen = notify;
    }


}
