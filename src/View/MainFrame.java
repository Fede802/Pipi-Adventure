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
    int ao = 600;
    Timer t = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            GAME_OVER.setLocation(200, GAME_OVER.getY() - 20);
            if (GAME_OVER.getY() <= 200) {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
            }
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
        GAME_PANEL.setBounds(0,0,600,600);
        GAME_PANEL.setOpaque(true);
        GAME_OVER.setBounds(200,600,200,200);
        GAME_OVER.setOpaque(true);
        GAME_SCREEN.setBounds(0,0,600,600);
        GAME_SCREEN.setOpaque(true);
        GAME_SCREEN.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = GAME_SCREEN.getSize();
                for(int i = 0; i < GAME_SCREEN.getComponentCount(); i++){
                    GAME_SCREEN.getComponent(i).setSize(size);
                }
                GAME_OVER.setBounds(200,ao,200,200);
                GAME_SCREEN.moveToFront(GAME_OVER);
                revalidate();
                repaint();
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
        });
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
        ao = 200;

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        GAME_SCREEN.moveToFront(GAME_OVER);
        t.start();
        //TODO slide gameOver and maybe pause
    }


}

