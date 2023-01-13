package pfdyemaker.src.activity.collectredberries;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Branch;

public class CollectRedberriesBranch extends Branch {

    @Override
    public boolean isValid() {
        return true;
    }
}
