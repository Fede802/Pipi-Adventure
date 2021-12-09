package utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FontLoader {

    private static final String FONT_PATH = "res/font/04B_30__.TTF";
    public static final String GAME_FONT = "04b";

    private FontLoader(){}
    //todo sout
    public static void loadFonts(){
        GraphicsEnvironment ge;
        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH)));
        }catch (FontFormatException | IOException e) {
            System.out.println("Failed loading font");
        }
    }

}

