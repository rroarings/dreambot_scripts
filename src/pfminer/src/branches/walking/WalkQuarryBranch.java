package pfminer.src.branches.walking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import pfminer.src.data.MinerConfig;
import pfminer.src.framework.Branch;

public class WalkQuarryBranch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.isEmpty() && !MinerConfig.QUARRY.contains(Players.getLocal());
    }
}
