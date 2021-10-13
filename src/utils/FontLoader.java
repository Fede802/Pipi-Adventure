package utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {
    private FontLoader(){}
    public static void loadFonts(){
        //TODO add font file, read and iterate there, or iterate font folder
        GraphicsEnvironment ge = null;
        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Resources/Font/04B_30__.TTF")));
        } catch(FontFormatException e){} catch (IOException e){
            System.out.println("Failed loading font");
        }
    }
}

