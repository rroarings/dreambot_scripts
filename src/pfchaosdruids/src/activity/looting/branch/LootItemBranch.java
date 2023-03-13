package pfchaosdruids.src.activity.looting.branch;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;
import pfchaosdruids.src.PFChaosDruids;
import pfchaosdruids.src.util.PricedItem;

import java.util.ArrayList;
import java.util.List;

public class LootItemBranch extends Branch {

    PFChaosDruids chaosDruids = new PFChaosDruids();

    Area druidArea = new Area(new Tile(2560, 3358, 0), new Tile(2564, 3354, 0));
    Condition attacking = () -> Players.getLocal().isInCombat();

    @Override
    public boolean isValid() {
        GroundItem loot = GroundItems.closest(groundItem -> groundItem.exists() && groundItem.getName().contains(chaosDruids.lootTrack.stream().toString()));

        if (loot != null) {
            return true;
        }
        return false;
    }
}
