package pfdyemaker.src.action.woadleaves.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;


public class WalkToWysonLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return  Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && !config.FALADOR_PARK_TILE.getArea(5).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.setStatus("Walking to Wyson");
            Walking.walk(config.FALADOR_PARK_TILE);
            Sleep.sleepUntil(() -> config.FALADOR_PARK_TILE.getArea(10).contains(Players.getLocal()), 5000, 600);
        }
        return 800;
    }
}
