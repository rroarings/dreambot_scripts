package pfitemlooter.src.activity.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfitemlooter.src.data.ItemLooterConfig;

public class WalkDraynorBank extends Leaf {
    ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        return Inventory.isFull() && !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Inventory.isFull() && !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                c.setStatus("Walking to Draynor bank");
                if (Walking.walk(BankLocation.DRAYNOR.getArea(1).getRandomTile())) {
                    Sleep.sleepUntil(() -> BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal()), 4000, 50);
                }
            }
        }
        return 600;
    }
}
