package pfenchanter.src.data;

import org.dreambot.api.methods.magic.Spell;
import pfenchanter.src.util.PricedItem;


public class EnchanterConfig {

    private static EnchanterConfig instance;

    private EnchanterConfig() {
        // private constructor to prevent instantiation from outside
    }

    public static EnchanterConfig getInstance() {
        if (instance == null) {
            instance = new EnchanterConfig();
        }
        return instance;
    }

    public int getStopAtLevel() {
        return stopAtLevel;
    }

    public void setStopAtLevel(int stopAtLevel) {
        this.stopAtLevel = stopAtLevel;
    }

    public int getStopAtMinutesRan() {
        return stopAtMinutesRan;
    }

    public void setStopAtMinutesRan(int stopAtMinutesRan) {
        this.stopAtMinutesRan = stopAtMinutesRan;
    }

    private int stopAtLevel;
    private int stopAtMinutesRan;
    private int itemsEnchanted;
    private Spell spellToCast;
    private String itemToEnchant;
    private String enchantedItem;
    private String status;
    public int profit = 0;

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public PricedItem getPricedItem() {
        return pricedItem;
    }

    public PricedItem pricedItem;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getItemsEnchanted() {
        return itemsEnchanted;
    }

    public Spell getSpellToCast() {
        return spellToCast;
    }

    public void setSpellToCast(Spell spellToCast) {
        this.spellToCast = spellToCast;
    }

    public String getItemToEnchant() {
        return itemToEnchant;
    }

    public void setItemToEnchant(String itemToEnchant) {
        this.itemToEnchant = itemToEnchant;
    }

    public String getEnchantedItem() {
        return enchantedItem;
    }

    public void setEnchantedItem(String enchantedItem) {
        this.enchantedItem = enchantedItem;
    }

}
