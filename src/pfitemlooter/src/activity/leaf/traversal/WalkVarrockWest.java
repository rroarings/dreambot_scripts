package pfitemlooter.src.activity.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfitemlooter.src.data.ItemLooterConfig;

public class WalkVarrockWest extends Leaf {
    ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        return Inventory.isFull() && !BankLocation.VARROCK_WEST.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Inventory.isFull() && !BankLocation.VARROCK_WEST.getArea(1).contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                c.setStatus("Walking to Varrock West");
                if (Walking.walk(BankLocation.VARROCK_WEST.getArea(0).getRandomTile())) {
                    Sleep.sleepUntil(() -> BankLocation.VARROCK_WEST.getArea(0).contains(Players.getLocal()), 4000, 50);
                }
            }
        }
        return 600;
    }
}
