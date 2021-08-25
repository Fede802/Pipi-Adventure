package View;

import Controller.GameStateHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class MainFrame extends JFrame{
    private int dx = 0;
    private int dy = 0;
    private static final int SLIDING_STEP = 20;
    private int gameOverSliding = 0;
    private Dimension size = MainFrame.MINIMUM_SIZE;


    Timer t = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            GAME_OVER.setLocation(GAME_OVER.getX(), GAME_OVER.getY() - SLIDING_STEP);
            if (GAME_OVER.getY() <= 200) {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
            }
            gameOverSliding+=SLIDING_STEP;
        }
    });

    public static final Dimension MINIMUM_SIZE = new Dimension(600,600);
    public static final String FRAME_TITLE = "Pipi Adventure";

    private final MainMenu MAIN_MENU = new MainMenu();
    private final GameOver GAME_OVER = new GameOver();
    private final GamePanel GAME_PANEL = new GamePanel();
    private final JLayeredPane GAME_SCREEN = new JLayeredPane();


    private final ArrayList<Component> STATE_LIST = new ArrayList<>();

    public MainFrame(){
        super(FRAME_TITLE);
        GAME_SCREEN.setBounds(0,0,this.getWidth(),this.getHeight());
        GAME_SCREEN.setOpaque(true);
        GAME_SCREEN.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                size = GAME_SCREEN.getSize();
                for(int i = 0; i < GAME_SCREEN.getComponentCount(); i++){
                    GAME_SCREEN.getComponent(i).setSize(size);
                }
                dx =(int)size.getWidth()/10;
                dy =(int)size.getHeight()/10;
                if(t.isRunning())
                    GAME_OVER.setBounds((int) (size.getWidth()-GameOver.DEFAULT_WIDTH)/2,(int) size.getHeight()-gameOverSliding,GameOver.DEFAULT_WIDTH+dx,GameOver.DEFAULT_HEIGHT+dy);
                else
                    GAME_OVER.setBounds((int) (size.getWidth()-GameOver.DEFAULT_WIDTH)/2,(int)(size.getHeight()-GameOver.DEFAULT_HEIGHT)/2,GameOver.DEFAULT_WIDTH+dx,GameOver.DEFAULT_HEIGHT+dy);
                GAME_SCREEN.moveToFront(GAME_OVER);
                revalidate();
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                ;
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        GAME_OVER.setVisible(false);
        GAME_SCREEN.add(GAME_PANEL);
        GAME_SCREEN.add(GAME_OVER,Integer.valueOf(0));
        STATE_LIST.add(MAIN_MENU);
        STATE_LIST.add(GAME_SCREEN);
        this.add(STATE_LIST.get(GameStateHandler.getInstance().getCurrentState()));
        this.setPreferredSize(MINIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

    }

    public  void switchState(){
        this.remove(STATE_LIST.get(GameStateHandler.getInstance().getPreviousState()));



        this.add(STATE_LIST.get(GameStateHandler.getInstance().getCurrentState()));
        //this.setContentPane(GAME_SCREEN);
//        this.revalidate();
        this.pack();
    }

    public void startGame(){
        GAME_PANEL.startGame();
    }

    public void moveScreen(){
        GAME_PANEL.stopGame();
        GAME_PANEL.setOpaqueScreen(true);
        GAME_OVER.setVisible(true);

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        GAME_SCREEN.moveToFront(GAME_OVER);
        GAME_OVER.requestFocus();
        t.start();
        //TODO slide gameOver and maybe pause
    }


}

