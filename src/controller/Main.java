package controller;


import utils.FontLoader;
import utils.ResouceLoader;
import javax.swing.SwingUtilities;


public class Main {
    private static int i;

    public static void main(String[] args) {
//        File dir = new File("res");
//        File[] listFile = dir.listFiles();
//        System.out.println(listFile.length);
//        for(int i = 0; i < listFile.length; i++){
//            System.out.println(listFile[i].getPath());
//        }
//        ResouceLoader loader = new ResouceLoader();
//        int f = loader.getFile("res");
//
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                i++;
//                System.out.print(i+ " ");
//                if(i == f) {
//                    GameEngine.getInstance().setResources();
//                    System.out.println("loaded");
//                }
//            }
//        };
//        System.out.println();
//        loader.loadResources("res",r);
//        FontLoader.testImport(loader.getRes("res/tilesets/1.png"));
        FontLoader.loadFonts();
//        int count = 0;
//        for(int i = 0; i < 20; i++){
//            count++;
//            if(count == 10)
//                continue;
//            System.out.println(count);
//        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameStateHandler.getInstance().loadResources();
            }
        });

    }

}
