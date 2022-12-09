package pfminer.src.leafs.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import pfminer.src.data.MinerConfig;
import pfminer.src.framework.Leaf;

public class DepositItemsLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.isFull() && MinerConfig.soulsBankArea.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Bank.isOpen()) {
            Bank.depositAllExcept(item -> item.getName().contains(" pickaxe"));
            MinerConfig.status = "Deposit items";
            Sleep.sleepUntil(() -> !Inventory.contains(" ore"), 2000);
            Bank.close();
        }

        if (!Bank.isOpen()) {
            Bank.open();
            MinerConfig.status = "Open bank";
            Sleep.sleepUntil(Bank::isOpen, 2000);
        }
        return 1000;
    }
}
