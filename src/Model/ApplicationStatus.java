package Model;

import Utils.Config;

public class ApplicationStatus {

    private int totalCoin = Config.getInstance().getTotalCoin();

    private static ApplicationStatus instance = null;
    private ApplicationStatus(){}
    public static ApplicationStatus getInstance() {
        if (instance == null){
            instance = new ApplicationStatus();
        }
        return instance;
    }

    public int getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(int totalCoin) {
        this.totalCoin = totalCoin;
    }

    public void updateTotalCoin(final int coinVariation){
        //TODO this kind of method to update entity traslations
        this.totalCoin+=coinVariation;
    }
}
