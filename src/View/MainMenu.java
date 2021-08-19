package View;

import Controller.GameStateHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {

    private JButton startButton;

    public MainMenu(){

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        this.add(startButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startButton){
            GameStateHandler.getInstance().openGameWindow();
        }
    }
}
