package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankOnionsLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();


    @Override
    public boolean isValid() {
        return Inventory.isFull();
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            config.setStatus("Opening bank");
            Logger.log("(onions) opening bank");
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 3000, 600);
        }

        if (Bank.isOpen()) {
            config.setStatus("Depositing inventory");
            Logger.log("(onions) depositing inventory");
            Bank.depositAllItems();
            Sleep.sleepUntil(Inventory::isEmpty, 3000, 600);
        }
        return 1000;
    }
}
