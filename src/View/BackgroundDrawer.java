package View;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BackgroundDrawer {

    private BufferedImage image;
    private JPanel panel;

    private int x;
    private int dx = 1;

    public BackgroundDrawer(File background, JPanel panel) {
        this.panel = panel;
        try {
            image = ImageIO.read(
                    background
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x += dx;
        if(x>=panel.getWidth()){
            x = 0;
        }
    }

    public void drawBackground(Graphics2D g2d) {
        g2d.drawImage(image, - (x), 0,panel.getWidth(),panel.getHeight()+100, null);
        g2d.drawImage(image, panel.getWidth()- (x), 0,panel.getWidth(),panel.getHeight()+100, null);
    }
}