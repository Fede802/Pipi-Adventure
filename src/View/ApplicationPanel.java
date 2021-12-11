package view;

import utils.SoundManager;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public abstract class ApplicationPanel extends JPanel implements IApplicationPanel, KeyListener, MouseInputListener {

    //    --------------------------------------------------------
    //                      INSTANCE FIELDS
    //    --------------------------------------------------------

    private final Timer TIMER = new Timer(TIMER_TICK, this::timerActionEvent);

    protected final Map<Integer, SoundManager> AUDIO = new HashMap<>();

    protected int minSize;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public ApplicationPanel() {
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    @Override
    public void start() {
        TIMER.start();
        if(AUDIO.containsKey(MUSIC_THEME))
            AUDIO.get(MUSIC_THEME).startLoop();
    }

    @Override
    public void stop() {
        TIMER.stop();
        if(AUDIO.containsKey(MUSIC_THEME))
            AUDIO.get(MUSIC_THEME).stopLoop();
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

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    protected abstract void timerActionEvent(ActionEvent e);

}
