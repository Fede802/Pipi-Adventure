package utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private final String url;
    protected final Properties properties = new Properties();
    private final SwingWorker worker = new SwingWorker<Void,Void>() {
        @Override
        protected Void doInBackground()  {
            try {
                Files.write(Path.of(url),makeData(properties.toString()).getBytes());
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

    public Config(String url){
        this.url = url;
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Path.of(url));
            properties.load(reader);
        } catch (IOException e) {
            System.out.println("Failed loading configuration file");
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveData(){
        worker.execute();
    }

    protected String makeData(String str){
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
