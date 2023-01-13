package pfdyemaker.src.activity.buywoadleaves;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class WalkToWysonLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return  Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && !config.WYSON_AREA.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.setStatus("Walking to Wyson");
            Walking.walk(config.WYSON_AREA.getCenter());
            Sleep.sleepUntil(() -> config.WYSON_AREA.contains(Players.getLocal()), 1000);
        }
        return 800;
    }
}
