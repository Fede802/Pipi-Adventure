package View;


import Model.MapGenerator;
import Utils.CostantField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    private Image pinguino;
    private Image[] tileArray;
    public static final int NUM_TILES = CostantField.NUM_COLUMNS_OF_PNG*CostantField.NUM_ROWS_OF_PNG;
    private MapGenerator generator;

    private int traslX;
    private int traslY = 16;
    private static final int VEL_X = 25;
    private Timer timer;
    private int img;

    public GamePanel(){
        generator = new MapGenerator();
        tileArray = new Image[NUM_TILES];
        System.out.println(NUM_TILES);

        try {
            int rows = 1;
            int col = 0;
            for(int i = 0; i < NUM_TILES; i++){
             tileArray[i] = ImageIO.read(new File(CostantField.MAP_URL)).getSubimage(320*col,(rows-1)*320,320,320);
             col++;
             if(rows*CostantField.NUM_COLUMNS_OF_PNG-1 == i){
                 rows++;
                 col = 0;
             }
                System.out.println(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(200,200));
//        pinguino = new ImageIcon("piok.gif").getImage();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traslX+=VEL_X;
                if(traslX == 5300) {
                    traslX = 0;
                    System.out.println("ok");
                    generator.updateMap();
                }
                traslY -= 2;
                if(traslY == 0)
                    traslY = 16;

                repaint();
            }
        });
        timer.start();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < CostantField.SIZE_OF_GENERETED_MAP; i++){
            for(int k = 0; k < CostantField.NUM_SECTION_COLUMN; k++)
                for(int j = 0; j < CostantField.NUM_SECTION_ROWS_TO_DRAW; j++){
                    if((img = generator.getTileData(i,j,k)) != CostantField.EMPTY_TILE_CODE)
                    g2d.drawImage(tileArray[img],50*(16*i+k)-traslX,this.getHeight()-50*(j+1),50,50,this);
                }
        }

    }
}