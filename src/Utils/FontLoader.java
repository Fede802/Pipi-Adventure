package utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontLoader {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static final String FONT_PATH = "res/font/04B_30__.TTF";
    public static final String GAME_FONT = "04b";

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private FontLoader(){}

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

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

