package pfdyemaker.src.activity.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class WalkToAggieLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && Inventory.contains(config.getDyeIngredient())
                && Inventory.count(config.getDyeIngredient()) >= 2
                && !Inventory.contains(item -> item.getName().contains(" dye"))
                && !config.AGGIES_HOUSE.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.setStatus("Walking to Aggie");
            Walking.walk(config.AGGIES_HOUSE.getCenter());
            Sleep.sleepUntil(() -> config.AGGIES_HOUSE.contains(Players.getLocal()), 1000);
        }
        return 600;
    }
}
