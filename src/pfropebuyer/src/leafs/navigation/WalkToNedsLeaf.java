package pfropebuyer.src.leafs.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfropebuyer.src.data.RopeBuyerConfig;
import pfropebuyer.src.framework.Leaf;

public class WalkToNedsLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 1000
                && !Inventory.isFull()
                && !RopeBuyerConfig.NEDS_HOUSE.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            RopeBuyerConfig.status = "Walking to Neds";
            Walking.walk(RopeBuyerConfig.NEDS_HOUSE.getRandomTile());
            Sleep.sleepUntil(() -> RopeBuyerConfig.NEDS_HOUSE.contains(Players.getLocal()), 2000);
        }
        return 800;
    }
}
