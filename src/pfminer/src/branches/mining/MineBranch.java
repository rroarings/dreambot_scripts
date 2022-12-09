package pfminer.src.branches.mining;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import pfminer.src.PFMiner;
import pfminer.src.data.MinerConfig;
import pfminer.src.framework.Branch;

public class MineBranch extends Branch {

    @Override
    public boolean isValid() {
        return !Inventory.isFull();
    }
}
