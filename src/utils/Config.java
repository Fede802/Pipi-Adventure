package utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private final String URL;
    private final SwingWorker WORKER = new SwingWorker<Void,Void>() {
        //todo sout
        @Override
        protected Void doInBackground() {
            try {
                Files.write(Path.of(URL),makeData(PROPERTIES.toString()).getBytes());
            } catch (IOException e) {
                System.out.println("Failed saving properties");
            }
            return null;
        }
        @Override
        public void done() {
            System.out.println("finish");
        }

    };

    protected final Properties PROPERTIES = new Properties();

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public Config(String URL) {
        this.URL = URL;
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Path.of(URL));
            PROPERTIES.load(reader);
        } catch (IOException e) {
            //todo sout
            System.out.println("Failed loading configuration file");
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void saveData() {
        WORKER.execute();
    }

    private String makeData(String str) {
        String data = "";
        str = str.replace("{","");
        str = str.replace("}","");
        str = str.trim();
        str = str.replaceAll(" ","");
        String[] tempData = str.split(",");
        for(int i = 0; i < tempData.length; i++)
            data+=tempData[i]+"\n";
        return data;
    }

}
