package pfminer.src.branches.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.walking.impl.Walking;
import pfminer.src.data.MinerConfig;
import pfminer.src.framework.Branch;

public class BankBranch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.isFull() && MinerConfig.soulsBankArea.contains(Players.getLocal());
    }
}
