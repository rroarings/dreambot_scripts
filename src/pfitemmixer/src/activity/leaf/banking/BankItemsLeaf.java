package pfitemmixer.src.activity.leaf.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

public class BankItemsLeaf extends Leaf {

    @Override
    public boolean isValid() {
        Item sf = Inventory.get(item -> item.getName().contains("Sulphurous "));
        return sf != null;
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                Sleep.sleepUntil(Bank::isOpen, 3000, 50);
            }
        }

        if (Bank.isOpen()) {
            if (Bank.depositAllItems()) {
                Sleep.sleepUntil(Inventory::isEmpty, 4000, 50);
            }
        }
        if (Bank.isOpen() && Bank.contains("Compost")) {
            if (!Inventory.contains("Compost")) {
                if (Bank.withdraw("Compost", 14)) {
                    Sleep.sleepUntil(() -> Inventory.contains("Compost"), 4000, 50);
                }
            }

        }

        if (Bank.isOpen() && Bank.contains("Saltpetre")) {
            if (!Inventory.contains("Saltpetre")) {
                if (Bank.withdraw("Saltpetre", 14)) {
                    Sleep.sleepUntil(() -> Inventory.contains("Saltpetre"), 4000, 50);
                    Bank.close();
                    Sleep.sleepUntil(() -> !Bank.isOpen(), 4000, 50);
                }
            }
        }

        if (Bank.isOpen() && !Bank.contains("Compost") || !Bank.contains("Saltpetre")) {
            ScriptManager.getScriptManager().stop();
            Logger.log("Stopping script - out of supplies");
        }
        return 600;
    }
}
