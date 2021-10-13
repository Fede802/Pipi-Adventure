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
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class ComponentContainer extends JLayeredPane implements ComponentListener {
    private Component prev,curr;
    private boolean isClosing = true,transition = false,notifyChangingScreen=false;
    private final ArrayList<Pair<Integer,Component>> components = new ArrayList<>();
    private int transitionRectWidth, transitionRectHeight;
    private final int CLOSING_STEP = 40;
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

    public void startApplication(){
        setupState();
        curr.setVisible(true);
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
        System.out.println("COMPONENTRESIZED");
        this.setSize(Math.round(this.getWidth()),Math.round(this.getHeight()));
        if(!transition){
            transitionRectHeight = this.getHeight();
            transitionRectWidth = this.getWidth();
        }
        for(int i = 0; i < this.getComponentCount(); i++){
            this.getComponent(i).setSize(this.getSize());
        }
        GameDataConfig.getInstance().setRenderedTileSize((int) (Math.min(this.getWidth(),this.getHeight())/GameDataConfig.getInstance().getMinTileToRender()));
        GameEngine.getInstance().notifySizeChanged();
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if(transition){
            Area shape = new Area(new Rectangle(0,0,this.getWidth(),this.getHeight()));
            shape.subtract(new Area(new RoundRectangle2D.Double((this.getWidth()- transitionRectWidth)/2,(this.getHeight()- transitionRectHeight)/2, transitionRectWidth, transitionRectHeight,20,20)));
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