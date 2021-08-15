package View;

import javax.swing.*;

public class MainFrame extends JFrame {
    private GamePanel gamePanel;

    public MainFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        this.addKeyListener(gamePanel.getKeyListeners()[0]);
        this.add(gamePanel);
        this.pack();

    }


}
