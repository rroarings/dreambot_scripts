package pfenchanter.src.activity.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfenchanter.src.data.EnchanterConfig;
import pfenchanter.src.framework.Leaf;

public class HandleBankLeaf extends Leaf {

    EnchanterConfig ec = EnchanterConfig.getInstance();


    @Override
    public boolean isValid() {
        if (Inventory.onlyContains("Cosmic rune")) return true;

        return Inventory.contains(item -> item.getName().contains(ec.getEnchantedItem()));
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 1000, 50);
        }

        if (Bank.isOpen() && Inventory.contains(item -> item.getName().contains(ec.getEnchantedItem()))) {
            ec.setStatus("Depositing " + ec.getEnchantedItem());
            if (Bank.depositAllExcept(item -> item.getName().equals("Cosmic rune"))) {
                Sleep.sleepUntil(() -> Inventory.contains(item -> item.getName().contains(ec.getEnchantedItem())), 2000, 50);
            }
        }

        if (Bank.isOpen() && Bank.contains(ec.getItemToEnchant())) {
            ec.setStatus("Withdrawing items");
            if (Bank.withdrawAll(ec.getItemToEnchant())) {
                Sleep.sleepUntil(() -> Inventory.contains(item -> item.getName().contains(ec.getEnchantedItem())), 2000, 50);
                Bank.close();
            }
        }

        if (!Inventory.contains(ec.getItemToEnchant()) && !Bank.contains(ec.getItemToEnchant())) {
            if(Bank.close()) {
                Sleep.sleepUntil(() -> !Bank.isOpen(), 1000, 50);
                Logger.log("Stopping script - out of supplies");
                ScriptManager.getScriptManager().stop();
            }
        }
        return 600;
    }
}
