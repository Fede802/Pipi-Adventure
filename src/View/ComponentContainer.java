package View;

import Commons.Pair;
import Controller.GameStateHandler;
import Utils.Config;
import Utils.GameConfig;

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

    public static Dimension size;
    private Component prev,curr;
    private boolean isClosing = true,transition = false;
    private int rectWidth,rectHeight;
    private ArrayList<Pair<Integer,Component>> components = new ArrayList<>();
    //todo change timer name
    Timer t = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(transition){
                repaint();
            }else if(prev instanceof Slidable)
                if(!((Slidable) prev).isOnScreen()){
                    curr.requestFocus();
                    if(curr instanceof ApplicationPanel){
                        ((ApplicationPanel) curr).start();
                    }
//                    prev.setBounds((int) (getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
                    setLayer(prev,DEFAULT_LAYER);
                    setLayer(curr,DRAG_LAYER);
                    prev.setVisible(false);
                    ((Timer) e.getSource()).stop();

                }

        }
    });

    public ComponentContainer(){
        super();
        rectWidth = this.getWidth();
        rectHeight = this.getHeight();
        this.setOpaque(true);
        this.setPreferredSize(MainFrame.MINIMUM_SIZE);
        this.addComponentListener(this);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        size = this.getSize();
        int width = (int) Math.round(size.getWidth());
        int height = (int) Math.round(size.getHeight());
        rectWidth = width;
        rectHeight = height;
        size.setSize(width,height);
        for(int i = 0; i < this.getComponentCount(); i++){
            this.getComponent(i).setSize(size);
        }
        updateComponentBounds();
        GameConfig.getInstance().setRenderedTileSize((int) (Math.min(size.getWidth(),size.getHeight())/GameConfig.getInstance().getMinTileToRender()));
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


    private void updateComponentBounds(){
        //TODO idk if this method is needed
        if(curr instanceof Slidable){
            if(((Slidable) curr).isOnScreen()) {
                curr.setBounds((int) (getWidth()-Slidable.DEFAULT_WIDTH)/2,(int)(getHeight()-Slidable.DEFAULT_HEIGHT)/2,Slidable.DEFAULT_WIDTH,Slidable.DEFAULT_HEIGHT);

            }else if(((Slidable) curr).isSliding()){
                curr.setBounds((int) (getWidth() - Slidable.DEFAULT_WIDTH) / 2, (int) getHeight() - ((Slidable) curr).getSlidingStep(), Slidable.DEFAULT_WIDTH, Slidable.DEFAULT_HEIGHT);

            }
        }else{
//            pause.setBounds((int) (getWidth() - GameOver.DEFAULT_WIDTH) / 2, (int) getHeight(), GameOver.DEFAULT_WIDTH, GameOver.DEFAULT_HEIGHT);
        }

    }

    public void switchState(){

        prev = curr;
        curr = null;
        for(int i = 0; i < components.size(); i++){
            Component tempValue = components.get(i).getValue();
            Integer tempKey = components.get(i).getKey();
            if(tempKey == GameStateHandler.getInstance().getCurrentState())
                curr = tempValue;
        }
        if(prev == null && curr instanceof ApplicationPanel){
            openCurrState();
            curr.requestFocus();
            ((ApplicationPanel) curr).start();
        }else if(curr instanceof Slidable && prev instanceof ApplicationPanel){
            ((ApplicationPanel) prev).stop();
            this.requestFocus();
            curr.setVisible(true);
            setLayer(prev,DEFAULT_LAYER);
            setLayer(curr,DRAG_LAYER);
            ((Slidable) curr).slide();
        }else if(curr instanceof ApplicationPanel && prev instanceof ApplicationPanel){
            this.requestFocus();
            ((ApplicationPanel) prev).stop();
            transition = true;
            t.start();

        }
    }
    private void openCurrState(){
        if(prev != null)
            prev.setVisible(false);
        curr.setVisible(true);
        if(prev != null)
            setLayer(prev,DEFAULT_LAYER);
        setLayer(curr,DRAG_LAYER);
    }

    public void resume(){
        prev = curr;
        curr = null;
        for(int i = 0; i < components.size(); i++){
            Component tempValue = components.get(i).getValue();
            Integer tempKey = components.get(i).getKey();
            if(tempKey == GameStateHandler.getInstance().getCurrentState())
                curr = tempValue;
        }
        if(prev instanceof Slidable){
            this.requestFocus();
            ((Slidable) prev).slide();
            t.start();
        }

    }

    public void add(Pair<Integer,Component> componentPair){
        add(componentPair.getValue());
        components.add(componentPair);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if(transition){
            Area shape = new Area(new Rectangle(0,0,this.getWidth(),this.getHeight()));
            shape.subtract(new Area(new RoundRectangle2D.Double((this.getWidth()-rectWidth)/2,(this.getHeight()-rectHeight)/2,rectWidth,rectHeight,20,20)));
            g2d.fill(shape);
            if(isClosing){
                if(rectHeight-40 < 0 || rectWidth-40 < 0){
                    isClosing = false;
                    openCurrState();
                }else{
                    rectWidth-=40;
                    rectHeight-=40;
                }
            }else{
                if(rectHeight+40 > size.getHeight() || rectWidth+40 > size.getWidth()){
                    isClosing = true;
                    transition = false;
                    curr.requestFocus();
                    t.stop();
                    if(curr instanceof ApplicationPanel)
                        ((ApplicationPanel) curr).start();

                }else{
                    rectWidth+=40;
                    rectHeight+=40;
                }
            }
        }

    }

}
