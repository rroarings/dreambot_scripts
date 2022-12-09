package pfminer.src.leafs.walking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfminer.src.data.MinerConfig;
import pfminer.src.framework.Leaf;

public class WalkQuarryLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.isEmpty() && !MinerConfig.QUARRY.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            Walking.walk(MinerConfig.QUARRY.getRandomTile());
            MinerConfig.status = "Walking to quarry";
            Sleep.sleepUntil(() -> MinerConfig.QUARRY.contains(Players.getLocal()), 2000);
        }
        return 1000;
    }
}
