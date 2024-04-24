package pfdyemaker.src.action.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class WalkToAggieLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal())
                && Inventory.contains("Coins")
                && Inventory.count("Coins") >= 5
                && Inventory.contains(DyeMakerConfig.dyeConfig().getDyeIngredient())
                && Inventory.count(DyeMakerConfig.dyeConfig().getDyeIngredient()) >= 2
                && !Inventory.contains(item -> item.getName().contains(" dye"));
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            if (Walking.walk(DyeMakerConfig.dyeConfig().getAggiesHouse().getRandomTile())) {
                DyeMakerConfig.dyeConfig().setStatus("Walking to Aggie");
                Sleep.sleepUntil(() -> DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal()), 5000, 600);
            }
        }
        return 600;
    }
}
