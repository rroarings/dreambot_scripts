package pfitemlooter.src.activity.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfitemlooter.src.data.ItemLooterConfig;

public class WalkDraynorManor extends Leaf {
    ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        Area area = new Area(3107, 3357, 3110, 3354, 1);
        return !Inventory.isFull() && !area.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        Area area = new Area(3107, 3357, 3110, 3354, 1);
        if (Walking.shouldWalk()) {
            c.setStatus("Walking to Draynor manor");
            if (Walking.walk(area.getCenter())) {
                Sleep.sleepUntil(() -> area.contains(Players.getLocal()), 5000, 50);
            }
        }
        return 100;
    }
}
