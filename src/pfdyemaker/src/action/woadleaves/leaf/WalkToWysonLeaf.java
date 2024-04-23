package pfdyemaker.src.action.woadleaves.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;


public class WalkToWysonLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return  Inventory.contains("Coins")
                && Inventory.count("Coins") >= 50
                && !DyeMakerConfig.dyeConfig().getFaladorParkTile().getArea(10).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            DyeMakerConfig.dyeConfig().setStatus("Walking to Wyson");
            Walking.walk(DyeMakerConfig.dyeConfig().getFaladorParkTile());
            Sleep.sleepUntil(() -> DyeMakerConfig.dyeConfig().getFaladorParkTile().getArea(10).contains(Players.getLocal()), 5000, 600);
        }
        return 600;
    }
}
