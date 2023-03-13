package pfdyemaker.src.activity.collectonions;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankOnionsLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            config.setStatus("Opening bank");
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 2000);
        }

        if (Bank.isOpen()) {
            config.setStatus("Depositing onions");
            Bank.depositAllItems();
            Logger.log("deposited onions");
            Sleep.sleepUntil(Inventory::isEmpty, 2000);
        }
        return 1000;
    }
}
