/*
*
* @author Nezzima (https://github.com/Nezzima/DreamBot)
 */

package pfdyemaker.src.util;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.utilities.Logger;
import pfdyemaker.src.data.DyeMakerConfig;

import java.util.HashMap;
import java.util.Map;

public class PricedItem {

    private static final Map<String, Integer> itemPriceCache = new HashMap<>();
    private static final long PRICE_CACHE_DURATION = 180000; // Cache price for 3 minutes

    private String name;
    private int lastCount = 0;
    private int amount = 0;
    private int price = 0;
    private int id = 0;

    public PricedItem(String name, boolean getPrice) {
        this.name = name;
        if (Inventory.contains(name)) {
            lastCount = Inventory.count(name);
        }
        if (getPrice) {
            updatePrice();
        } else
            price = 0;
    }

    private void updatePrice() {
        if (itemPriceCache.containsKey(name)) {
            long cachedTime = itemPriceCache.get(name + "_time");
            if (System.currentTimeMillis() - cachedTime <= PRICE_CACHE_DURATION) {
                price = itemPriceCache.get(name);
                return;
            }
        }

        String tempName = name;
        if (name.contains("arrow"))
            tempName += "s";
        price = LivePrices.get(tempName);
        itemPriceCache.put(name, price);
        itemPriceCache.put(name + "_time", (int) System.currentTimeMillis());
        Logger.log("PI: " + name + " |  price: " + price);
    }

    public void update() {
        int increase;
        if (id == 0)
            increase = Inventory.count(name) - lastCount;
        else
            increase = Inventory.count(id) - lastCount;
        if (increase < 0)
            increase = 0;
        amount += increase;
        if (id == 0)
            lastCount = Inventory.count(name);
        else
            lastCount = Inventory.count(id);

        DyeMakerConfig.dyeConfig().setProfit(amount * price);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amt) {
        this.amount = amt;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public int getValue() {
        if (amount <= 0)
            return 0;
        return amount * price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
