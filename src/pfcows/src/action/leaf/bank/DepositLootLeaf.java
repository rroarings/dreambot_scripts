package pfcows.src.action.leaf.bank;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfcows.src.data.CowsConfig;

public class DepositLootLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.isFull() && BankLocation.LUMBRIDGE.getArea(3).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                CowsConfig.getCowsConfig().setStatus("Opening bank");
                Sleep.sleepUntil(Bank::isOpen, 3000, 300);
            }
        }

        if (Bank.isOpen()) {
            if (Bank.depositAllItems()) {
                CowsConfig.getCowsConfig().setStatus("Depositing items");
                Sleep.sleepUntil(Inventory::isEmpty, 3000, 300);
                Bank.close();
            }
        }

        return 300;
    }
}
