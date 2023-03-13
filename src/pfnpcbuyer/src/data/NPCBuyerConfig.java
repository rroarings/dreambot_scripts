package pfnpcbuyer.src.data;

import pfnpcbuyer.src.util.PricedItem;

public class NPCBuyerConfig {

    private static NPCBuyerConfig instance;

    private NPCBuyerConfig() {
    }

    public static NPCBuyerConfig getInstance() {
        if (instance == null) {
            instance = new NPCBuyerConfig();
        }
        return instance;
    }

    public PricedItem pricedItem;
    public int profit, itemsBought = 0;
    private String status;

    public String getItemToBuy() {
        return itemToBuy;
    }

    public void setItemToBuy(String itemToBuy) {
        this.itemToBuy = itemToBuy;
    }

    private String itemToBuy = "";

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
