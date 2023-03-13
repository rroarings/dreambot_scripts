package pfdriftnets.src.location.fossilisland;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.utilities.Sleep;
import pfdriftnets.src.data.DriftNetsConfig;
import pfdriftnets.src.framework.Leaf;

public class FossilIslandBankingLeaf extends Leaf {

    DriftNetsConfig config = DriftNetsConfig.getDriftNetsConfig();

    @Override
    public boolean isValid() {
        return Inventory.isEmpty() || Inventory.contains("Drift net" ) && !Inventory.contains("Jute fibre");
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            DriftNetsConfig.status = "Open bank";
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 2000);
        }

        if (Inventory.contains("Drift net")) {
            if (Bank.isOpen()) {
                DriftNetsConfig.status = "Deposit items";
                Bank.depositAllItems();
                Sleep.sleepUntil(Inventory::isEmpty, 1000);
            }
        }

        if (Bank.isOpen() && Bank.contains("Jute fibre")) {
            DriftNetsConfig.status = "Withdraw Jute";
            Bank.withdrawAll("Jute fibre");
            Sleep.sleepUntil(() -> Inventory.contains("Jute fibre"), 2000);
        }


        return 900;
    }
}
