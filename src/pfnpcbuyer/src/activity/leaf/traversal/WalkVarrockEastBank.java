package pfnpcbuyer.src.activity.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfnpcbuyer.src.data.NPCBuyerConfig;

public class WalkVarrockEastBank extends Leaf {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    @Override
    public boolean isValid() {
        return Inventory.isFull() && !BankLocation.VARROCK_EAST.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Inventory.isFull() && !BankLocation.VARROCK_EAST.getArea(1).contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                if (Walking.walk(BankLocation.VARROCK_EAST.getArea(1).getRandomTile())) {
                    nbc.setStatus("Walking to Varrock east bank");
                    Sleep.sleepUntil(() -> BankLocation.VARROCK_EAST.getArea(1).contains(Players.getLocal()), 4000, 50);
                }
            }
        }
        return 600;
    }
}
