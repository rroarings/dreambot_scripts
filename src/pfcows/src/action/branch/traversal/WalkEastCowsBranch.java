package pfcows.src.action.branch.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import pfcows.src.data.Location;

public class WalkEastCowsBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Location.EAST_COWS.getArea().contains(Players.getLocal()) && Inventory.isEmpty()) {
            return true;
        }
        return false;

    }
}
