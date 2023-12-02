package pfcows.src.action.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfcows.src.data.CowsConfig;

public class WalkLumbridgeBankLeaf extends Leaf {
    @Override
    public boolean isValid() {
        if (Inventory.isFull() && !BankLocation.LUMBRIDGE.getArea(3).contains(Players.getLocal())) {
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        if (!BankLocation.LUMBRIDGE.getArea(1).contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                CowsConfig.getCowsConfig().setStatus("Walking to Lumbridge Bank");
                Walking.walk(BankLocation.LUMBRIDGE.getArea(3).getCenter());
                Sleep.sleepUntil(() -> BankLocation.LUMBRIDGE.getArea(3).contains(Players.getLocal()), 4000, 100);
            }
        }
        return 100;
    }
}
