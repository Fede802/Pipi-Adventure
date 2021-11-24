package view;

import utils.SoundManager;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ApplicationPanel extends JPanel implements IApplicationPanel, KeyListener, MouseInputListener {

    protected Timer timer;
    protected Map<Integer, SoundManager> audio;
    protected int minSize;

    public ApplicationPanel(){
        timer = new Timer(TIMER_TICK, e -> {
            timerActionEvent(e);
        });
        audio = new HashMap<>();
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    protected abstract void timerActionEvent(ActionEvent e);

    @Override
    public void start() {
        timer.start();
        if(audio.containsKey(MUSIC_THEME))
            audio.get(MUSIC_THEME).startLoop();
    }

    @Override
    public void stop() {
        timer.stop();
        if(audio.containsKey(MUSIC_THEME))
            audio.get(MUSIC_THEME).stopLoop();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        minSize = getWidth();
        int size = getHeight();
        if(size < minSize){
            minSize = size;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing to do
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //nothing to do
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //nothing to do
    }

}
