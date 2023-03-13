package pfitemlooter.src.data;

import org.dreambot.api.utilities.Logger;
import pfitemlooter.src.util.PricedItem;

import java.util.ArrayList;
import java.util.List;

public class ItemLooterConfig {

    private static ItemLooterConfig instance;
    public List<PricedItem> pricedItems;

    private ItemLooterConfig() {
        takingEgg = true;
        takingSock = false;
        pricedItems = new ArrayList<>();
        pricedItems.add(new PricedItem("Smelly sock", false));
        pricedItems.add(new PricedItem("Spooky egg", false));
        pricedItems.add(new PricedItem("Burnt bones", true));
    }

    public static ItemLooterConfig getInstance() {
        if (instance == null) {
            instance = new ItemLooterConfig();
        }
        return instance;
    }

    public PricedItem pricedItem;
    public int profit, eggsTaken, socksTaken = 0;
    private String status;
    private boolean takingEgg;
    private boolean takingSock;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    private boolean alive = true;

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

    public boolean isTakingEgg() {
        return takingEgg;
    }

    public void setTakingEgg(boolean takingEgg) {
        this.takingEgg = takingEgg;
    }

    public boolean isTakingSock() {
        return takingSock;
    }

    public void setTakingSock(boolean takingSock) {
        this.takingSock = takingSock;
    }

    public int getEggsTaken() {
        return eggsTaken;
    }

    public int getSocksTaken() {
        return socksTaken;
    }

    public void setEggsTaken(int eggsTaken) {
        this.eggsTaken = eggsTaken;
    }

    public void setSocksTaken(int socksTaken) {
        this.socksTaken = socksTaken;
    }

}
