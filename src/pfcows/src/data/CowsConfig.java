package pfcows.src.data;

import pfcows.src.util.PricedItem;

public class CowsConfig {

    private static final CowsConfig cowsConfig = new CowsConfig();
    public static CowsConfig getCowsConfig() {
        return cowsConfig;
    }
    private CowsConfig() {}

    public int profit = 0;
    private String status;

    public void setPricedItem(PricedItem pricedItem) {
        this.pricedItem = pricedItem;
    }

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
