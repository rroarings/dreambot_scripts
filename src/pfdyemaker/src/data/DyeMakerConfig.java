package pfdyemaker.src.data;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import pfdyemaker.src.ui.Frame;
import pfdyemaker.src.util.PricedItem;

public class DyeMakerConfig {

    private static final DyeMakerConfig dyeMakerConfig = new DyeMakerConfig();
    public static DyeMakerConfig getDyeMakerConfig() {
        return dyeMakerConfig;
    }

    public DyeMakerConfig() {}

    public Area AGGIES_HOUSE = new Area(3083, 3261, 3089, 3256);
    public Area REDBERRY_AREA = new Area(3278, 3375, 3267, 3367);
    public Area ONION_AREA = new Area(3186, 3269, 3192, 3265);
    public Tile FALADOR_PARK_TILE = new Tile(3025, 3379, 0);

    public String dyeToMake;
    public String dyeIngredient;
    public static String status = "";
    public String REDBERRIES = "Redberries";
    public String WOAD_LEAVES = "Woad leaf";
    public String ONION = "Onion";

    public static boolean isUseEnergyPotions() {
        if (Frame.isEnergyPotions()) {
            return true;
        }
        return false;
    }

    private static int worldHopDelayMin;
    private static int worldHopDelayMax;

    public static int getWorldHopDelayMin() {
        return worldHopDelayMin;
    }

    public static void setWorldHopDelayMin(int worldHopDelayMin) {
        DyeMakerConfig.worldHopDelayMin = worldHopDelayMin;
    }

    public static int getWorldHopDelayMax() {
        return worldHopDelayMax;
    }

    public static void setWorldHopDelayMax(int worldHopDelayMax) {
        DyeMakerConfig.worldHopDelayMax = worldHopDelayMax;
    }

    public PricedItem getPricedItem() { return pricedItem; }
    public PricedItem pricedItem;

    public int profit = 0;
    public int ingredientPrice;

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
        DyeMakerConfig.status = status;
    }
}
