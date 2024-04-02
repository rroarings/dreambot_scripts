package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class WalkToOnionsLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && !config.ONION_AREA.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.setStatus("Walking to onion patch");
            Logger.log("(navigation) walking to onion patch");
            Walking.walk(config.ONION_AREA.getCenter());
            Sleep.sleepUntil(() -> config.ONION_AREA.contains(Players.getLocal().getTile()), 6000, 600);
        }
        return 600;
    }
}
