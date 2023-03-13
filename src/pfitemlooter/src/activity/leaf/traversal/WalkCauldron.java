package pfitemlooter.src.activity.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfitemlooter.src.data.ItemLooterConfig;

public class WalkCauldron extends Leaf {
    ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        GameObject cauldron = GameObjects.closest(go -> go.getName().equals("Spooky cauldron") && go.hasAction("Rummage"));

        return Inventory.isEmpty() && cauldron.canReach() && cauldron.distance(Players.getLocal()) >= 10;
    }

    @Override
    public int onLoop() {
        GameObject cauldron = GameObjects.closest(go -> go.getName().equals("Spooky cauldron") && go.hasAction("Rummage"));

        if (cauldron != null) {
            if (Walking.shouldWalk()) {
                c.setStatus("Walking to cauldron");
                Walking.walk(cauldron.getTile());
            }
        }
        return 600;
    }
}
