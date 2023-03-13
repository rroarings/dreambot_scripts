package pfropebuyer.src.leafs.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfropebuyer.src.data.RopeBuyerConfig;
import pfropebuyer.src.framework.Leaf;

public class WalkToDraynorBankLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Rope") && Inventory.isFull() && !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            RopeBuyerConfig.status = "Walking to Draynor bank";
            Walking.walk(BankLocation.DRAYNOR.getArea(1).getRandomTile());
            Sleep.sleepUntil(() -> BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal()), 2000);
        }
        return 800;
    }
}
