package View;


import Controller.GameEngineForView;
import Model.MapGenerator;
import Utils.CostantField;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener {
    private Image background,player;
    private Image[] tileArray;
    public static final int NUM_TILES = CostantField.NUM_COLUMNS_OF_PNG*CostantField.NUM_ROWS_OF_PNG;



    private Timer timer;
//    private int img;

    private int traslmg;

    public GamePanel(){
        this.setFocusable(true);
        tileArray = new Image[NUM_TILES];
        System.out.println("num tiles: "+NUM_TILES);
        this.addKeyListener(this);

        try {
            background = ImageIO.read(new File("Sfondo_Prova.png"));
            player = ImageIO.read(new File("PInguino_Definitivo4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int rows = 1;
            int col = 0;

            for(int i = 0; i < NUM_TILES; i++){
             tileArray[i] = ImageIO.read(new File(CostantField.MAP_URL)).getSubimage(CostantField.TILE_SIZE *col,(rows-1)*CostantField.TILE_SIZE,CostantField.TILE_SIZE,CostantField.TILE_SIZE);
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

        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traslmg+=10;
                GameEngineForView.getInstance().setMapTraslation();


                if(traslmg>=getWidth()){
                    traslmg = 0;
                }
//                traslY -= 2;
//                if(traslY == 0)
//                    traslY = 16;

                repaint();
            }
        });
        timer.start();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(background, - (traslmg), 0,this.getWidth(),this.getHeight()+100, null);
        g2d.drawImage(background, this.getWidth()- (traslmg), 0,this.getWidth(),this.getHeight()+100, null);
        for(int i = 0; i < CostantField.SIZE_OF_GENERATED_MAP; i++){
            for(int k = 0; k < CostantField.NUM_SECTION_COLUMN; k++)
                for(int j = 0; j < CostantField.NUM_SECTION_ROWS_TO_DRAW; j++){
//                    if((img = generator.getTileData(i,j,k)) != CostantField.EMPTY_TILE_CODE)
                    g2d.drawImage(tileArray[GameEngineForView.getInstance().getTileData(i,j,k)],CostantField.RENDERED_TILE_SIZE *(16*i+k)-(int) GameEngineForView.getInstance().getMapTraslation(),this.getHeight()-CostantField.RENDERED_TILE_SIZE *(j+1),CostantField.RENDERED_TILE_SIZE,CostantField.RENDERED_TILE_SIZE,this);
                }
        }
        g2d.drawImage(player,GameEngineForView.getInstance().getPlayerPosition()[0],this.getHeight()-GameEngineForView.getInstance().getPlayerPosition()[1],40,40,null);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        GameEngineForView.getInstance().jump();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}