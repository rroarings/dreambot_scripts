package pfropebuyer.src.branches.collection;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import pfropebuyer.src.data.RopeBuyerConfig;
import pfropebuyer.src.framework.Branch;

public class CollectRopesBranch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 1000
                && !Inventory.isFull()
                && RopeBuyerConfig.NEDS_HOUSE.contains(Players.getLocal());
    }
}