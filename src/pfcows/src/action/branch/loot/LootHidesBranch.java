package pfcows.src.action.branch.loot;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.wrappers.items.GroundItem;
import pfcows.src.data.Location;

public class LootHidesBranch extends Branch {

    @Override
    public boolean isValid() {
        GroundItem cowhide = GroundItems.closest(item -> item.getName().equals("Cowhide"));

        return !Inventory.isFull() && cowhide != null && !Players.getLocal().isMoving()
                && Location.EAST_COWS.getArea().contains(cowhide.getTile());
    }

}
