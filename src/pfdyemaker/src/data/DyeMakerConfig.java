package pfdyemaker.src.data;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import pfdyemaker.src.ui.Frame;
import pfdyemaker.src.util.PricedItem;

public class DyeMakerConfig {

    private static final DyeMakerConfig dyeConfig = new DyeMakerConfig();
    private Area aggiesHouse;
    private Area redberryArea;
    private Area onionArea;
    private Tile faladorParkTile;
    private String dyeToMake;
    private String dyeIngredient;
    private String status;

    public String getRedberries() {
        return "Redberries";
    }

    public String getWoadLeaves() {
        return "Woad leaf";
    }

    public String getOnion() {
        return "Onion";
    }

    private int worldHopDelayMin;
    private int worldHopDelayMax;
    private int profit;
    private int ingredientPrice;
    private PricedItem pricedItem;

    public static DyeMakerConfig dyeConfig() {
        return dyeConfig;
    }

    private DyeMakerConfig() {
        aggiesHouse = new Area(3083, 3261, 3089, 3256);
        redberryArea = new Area(3278, 3375, 3267, 3367);
        onionArea = new Area(3186, 3269, 3192, 3265);
        faladorParkTile = new Tile(3025, 3379, 0);
        status = "";
    }

    public static boolean isUseEnergyPotions() {
        if (Frame.isEnergyPotions()) {
            return true;
        }
        return false;
    }

    public Area getAggiesHouse() {
        return aggiesHouse;
    }

    public Area getRedberryArea() {
        return redberryArea;
    }

    public Area getOnionArea() {
        return onionArea;
    }

    public Tile getFaladorParkTile() {
        return faladorParkTile;
    }


    public int getWorldHopDelayMin() {
        return worldHopDelayMin;
    }

    public void setWorldHopDelayMin(int worldHopDelayMin) {
        this.worldHopDelayMin = worldHopDelayMin;
    }

    public int getWorldHopDelayMax() {
        return worldHopDelayMax;
    }

    public void setWorldHopDelayMax(int worldHopDelayMax) {
        this.worldHopDelayMax = worldHopDelayMax;
    }

    public PricedItem getPricedItem() {
        return pricedItem;
    }

    public void setPricedItem(PricedItem pricedItem) {
        this.pricedItem = pricedItem;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(int ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public void setDyeToMake(String dyeToMake) {
        this.dyeToMake = dyeToMake;
    }

    public String getDyeIngredient() {
        return dyeIngredient;
    }

    public void setDyeIngredient(String dyeIngredient) {
        this.dyeIngredient = dyeIngredient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
