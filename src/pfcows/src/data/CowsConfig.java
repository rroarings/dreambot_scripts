package pfcows.src.data;

import pfcows.src.util.PricedItem;

public class CowsConfig {

    private static CowsConfig instance;

    private CowsConfig() {
    }

    public static CowsConfig getInstance() {
        if (instance == null) {
            instance = new CowsConfig();
        }
        return instance;
    }

    public int profit = 0;
    private String status;
    public PricedItem pricedItem;

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PricedItem getPricedItem() {
        return pricedItem;
    }

}
