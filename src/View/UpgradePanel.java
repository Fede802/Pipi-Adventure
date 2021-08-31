package View;

import Utils.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UpgradePanel extends JPanel implements MouseInputListener {
    private final int MAX_LIFE = 3;
    private final int MAX_BULLET = 10;
    private Image player;
    private int currentLife = Config.getInstance().getCurrentLife();
    private int currentMaxBullet = Config.getInstance().getCurrentMaxBullet();
    private ArrayList<Rectangle2D.Double> buttons = new ArrayList<>();

    public UpgradePanel(){
        try {
            player = ImageIO.read(new File("Resources/Entities/Player/PInguino_Definitivo1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttons.add(new Rectangle2D.Double());
        buttons.add(new Rectangle2D.Double());
        buttons.add(new Rectangle2D.Double());
        buttons.add(new Rectangle2D.Double());
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int minSize = this.getWidth();
        int size = this.getHeight();
        if(size < minSize){
            minSize = size;
        }
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(player,(int)(this.getWidth()/4-minSize*0.15),(int)(this.getHeight()/2-minSize*0.15),(int)(minSize*0.3),(int)(minSize*0.3),null);

//        g2d.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
//        g2d.drawLine(this.getWidth()*3/4,0,this.getWidth()*3/4,this.getHeight());
//        g2d.drawLine(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);

        g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.23),(int)(this.getHeight()/2-minSize*0.20),(int)(minSize*0.06),(int)(minSize*0.06));
        buttons.get(0).setRect(this.getWidth()/4*3+minSize*0.09,this.getHeight()/2-minSize*0.20,minSize*0.12,minSize*0.06);
        g2d.draw(buttons.get(0));

        g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.23),(int)(this.getHeight()/2-minSize*0.03),(int)(minSize*0.06),(int)(minSize*0.06));
        buttons.get(1).setRect((this.getWidth()/4*3+minSize*0.09),(this.getHeight()/2-minSize*0.03),(minSize*0.12),(minSize*0.06));
        g2d.draw(buttons.get(1));


        buttons.get(2).setRect(this.getWidth()/4*3-minSize*0.19,this.getHeight()/2+minSize*0.17,minSize*0.14,minSize*0.08);
        g2d.draw(buttons.get(2));
        buttons.get(3).setRect(this.getWidth()/4*3+minSize*0.05,this.getHeight()/2+minSize*0.17,minSize*0.14,minSize*0.08);
        g2d.draw(buttons.get(3));

        for(int i = 0; i < MAX_LIFE; i++){
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.19),(int)(minSize*0.08),(int)(minSize*0.04));
            if(currentLife>i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.08*i),(int)(this.getHeight()/2-minSize*0.19),(int)(minSize*0.08),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }

        }
        for(int i = 0; i < 5;i++){
            g2d.drawRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2-minSize*0.02),(int)(minSize*0.08),(int)(minSize*0.04));
            if(currentMaxBullet-4>i){
                g2d.setColor(Color.ORANGE);
                g2d.fillRect((int)(this.getWidth()/4*3-minSize*0.16+minSize*0.04*i),(int)(this.getHeight()/2-minSize*0.02),(int)(minSize*0.08),(int)(minSize*0.04));
                g2d.setColor(Color.BLACK);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked");
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).contains(e.getX(),e.getY())){
                if(i == 0 && currentLife < MAX_LIFE)
                    currentLife++;
                if(i == 1 && currentMaxBullet-5 < 6)
                    currentMaxBullet++;
            }
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
