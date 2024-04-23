package pfdyemaker.src.action.redberries.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class WalkToRedberriesLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !DyeMakerConfig.dyeConfig().getRedberryArea().contains(Players.getLocal()) && !Inventory.isFull();
    }

    //todo add combat checks for rats

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            if (Walking.walk(DyeMakerConfig.dyeConfig().getRedberryArea().getRandomTile())) {
                DyeMakerConfig.dyeConfig().setStatus("Walking to onion patch");
                Sleep.sleepUntil(() -> DyeMakerConfig.dyeConfig().getRedberryArea().contains(Players.getLocal()), 5000, 600);
            }
        }
        return 600;
    }
}
