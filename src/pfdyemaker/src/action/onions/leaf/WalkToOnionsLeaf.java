package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class WalkToOnionsLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !Inventory.isFull()
                && !DyeMakerConfig.dyeConfig().getOnionArea().contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            if (Walking.walk(DyeMakerConfig.dyeConfig().getOnionArea().getRandomTile())) {
                DyeMakerConfig.dyeConfig().setStatus("Walking to onion patch");
                Sleep.sleepUntil(() -> DyeMakerConfig.dyeConfig().getOnionArea().contains(Players.getLocal()), 5000, 600);
            }
        }
        return 600;
    }
}
