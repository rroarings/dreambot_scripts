package pfdyemaker.src.branches.buying;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Branch;

public class BuyWoadLeavesBranch extends Branch {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return true;
    }
}
