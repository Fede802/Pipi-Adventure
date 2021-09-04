package View;

import javax.swing.*;
import java.awt.*;

public class ControlsView extends JPanel {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
        g2d.drawLine(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
    }
}
