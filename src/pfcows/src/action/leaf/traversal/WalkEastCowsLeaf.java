package pfcows.src.action.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfcows.src.data.CowsConfig;
import pfcows.src.data.Location;

public class WalkEastCowsLeaf extends Leaf {

    @Override
    public boolean isValid() {
        if (!Location.EAST_COWS.getArea().contains(Players.getLocal()) && Inventory.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        if (!Location.EAST_COWS.getArea().contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                CowsConfig.getCowsConfig().setStatus("Walking to East cows");
                Walking.walk(Location.EAST_COWS.getArea().getRandomTile());
                Sleep.sleepUntil(() -> Location.EAST_COWS.getArea().contains(Players.getLocal()), 4000, 100);
            }
        }
        return 100;
    }
}
