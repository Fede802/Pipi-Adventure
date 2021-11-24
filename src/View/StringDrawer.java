package view;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class StringDrawer {

    public static final int CENTER = 0;
    public static final int PADDING = 1;

    public static final int TITLE_STROKE=10;
    public static final int DEFAULT_STROKE=4;


    private StringDrawer(){}

    public static double getStringWidth(final Graphics2D g2d, final String text, final Font textFont){
        return g2d.getFontMetrics(textFont).stringWidth(text);
    }

    public static double getStringHeight(final Graphics2D g2d, final Font textFont){
        return g2d.getFontMetrics(textFont).getHeight();
    }

    public static void drawString(Graphics2D g2d, String text, Font font, Color boundColor, int strokeWidth, Color fillColor, final double paddingTop, final double paddingRight, final JPanel panel, int position){
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();
        AffineTransform affineTransform = g2d.getTransform();
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics(font);
        int downShift = fm.getAscent();
        if(position == CENTER){
            affineTransform.translate(panel.getWidth()/2- fm.stringWidth(text)/2+paddingRight,downShift+paddingTop);
        }else{
            affineTransform.translate(paddingRight,downShift+paddingTop);
        }
        g2d.setTransform(affineTransform);
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(text, g2d.getFont(), frc);
        Shape shape = tl.getOutline(null);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.setColor(boundColor);
        g2d.draw(shape);
        g2d.setColor(fillColor);
        g2d.fill(shape);
        affineTransform.translate(-affineTransform.getTranslateX(),-affineTransform.getTranslateY());
        g2d.setTransform(affineTransform);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }
}
