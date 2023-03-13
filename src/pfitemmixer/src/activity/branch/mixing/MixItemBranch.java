package pfitemmixer.src.activity.branch.mixing;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class MixItemBranch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.contains(13421);
    }
}
