package pfdyemaker.src.branches.making;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Branch;

public class MakeBlueDyeBranch extends Branch {

    @Override
    public boolean isValid() {
        return true;
    }
}
