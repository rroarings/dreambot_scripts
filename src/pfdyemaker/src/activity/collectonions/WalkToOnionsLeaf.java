package pfdyemaker.src.activity.collectonions;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class WalkToOnionsLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && !config.ONION_AREA.contains(Players.getLocal());
    }

    //todo add combat checks for Draynor prison guards

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.setStatus("Walking to onion patch");
            Walking.walk(config.ONION_AREA.getCenter());
            Sleep.sleepUntil(() -> config.ONION_AREA.contains(Players.getLocal().getTile()), 1000);
        }
        return 600;
    }
}
