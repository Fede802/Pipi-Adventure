package Utils;

public abstract class Config {

    public abstract void saveData();

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
