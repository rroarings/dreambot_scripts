package pfitemmixer.src.activity.leaf.mixing;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankType;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;

public class MixItemLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Compost", "Saltpetre");
    }

    @Override
    public int onLoop() {

        if (Inventory.contains(13421, 6032)) {
            if (!Inventory.isItemSelected()) {
                if (Inventory.get(13421).interact("Use")) {
                    Sleep.sleepUntil(Inventory::isItemSelected, 3000, 100);
                }
            }
            if (Inventory.isItemSelected()) {
                if (Inventory.get(6032).interact("Use")) {
                    Mouse.move(Bank.getClosestBank(BankType.BOOTH));
                    Sleep.sleepUntil(() -> !Inventory.contains(13421) || !Inventory.contains(6032), 90000, 100);
                }
            }
        }
        return 600;
    }
}
