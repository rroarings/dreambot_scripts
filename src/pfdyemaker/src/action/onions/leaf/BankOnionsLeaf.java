package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankOnionsLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return BankLocation.DRAYNOR.getArea(5).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            DyeMakerConfig.dyeConfig().setStatus("Opening bank");
            if (Bank.open()) {
                Sleep.sleepUntil(Bank::isOpen, 5000, 600);
                Logger.log("(dyes) (bankOnion) opened bank");
            }
        }

        if (Bank.isOpen()) {
            DyeMakerConfig.dyeConfig().setStatus("Depositing onions");
            if (Bank.depositAllItems()) {
                Sleep.sleepUntil(Inventory::isEmpty, 5000, 600);
                Logger.log("(dyes) (bankOnion) deposited onions");
            }
        }
        return 600;
    }
}
