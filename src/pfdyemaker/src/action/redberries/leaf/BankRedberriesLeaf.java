package pfdyemaker.src.action.redberries.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankRedberriesLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return BankLocation.VARROCK_EAST.getArea(5).contains(Players.getLocal()) && Inventory.contains("Redberries");
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            DyeMakerConfig.dyeConfig().setStatus("Opening bank");
            if (Bank.open()) {
                Sleep.sleepUntil(Bank::isOpen, 5000, 600);
                Logger.log("(dyes) (bankRedberries) opened bank");
            }
        }

        if (Bank.isOpen()) {
            DyeMakerConfig.dyeConfig().setStatus("Depositing redberries");
            if (Bank.depositAllItems()) {
                Sleep.sleepUntil(Inventory::isEmpty, 5000, 600);
                Logger.log("(dyes) (bankRedberries) deposited redberries");
            }
        }
        return 600;
    }


}
