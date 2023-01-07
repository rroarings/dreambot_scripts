package pfdyemaker.src.leafs.walking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class WalkToRedberriesLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return !config.REDBERRY_AREA.contains(Players.getLocal()) && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            DyeMakerConfig.status = "Walking to redberries";
            Walking.walk(config.REDBERRY_AREA.getRandomTile());
            Sleep.sleepUntil(() -> config.REDBERRY_AREA.distance(Players.getLocal().getTile()) > 3, 1000);
        }
        return 600;
    }
}
