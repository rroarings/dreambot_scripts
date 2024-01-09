package pfdyemaker.src.action.redberries.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.action.util.QuickMethods;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankRedberriesLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return BankLocation.VARROCK_EAST.getArea(1).contains(Players.getLocal()) && Inventory.contains("Redberries");
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            config.setStatus("Opening bank");
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 2000);
        }

        if (Bank.isOpen()) {
            config.setStatus("Deposit inventory");
            Bank.depositAllItems();
            Sleep.sleepUntil(Inventory::isEmpty, 2000);
            QuickMethods.withdrawEnergyPotions();
            QuickMethods.drinkEnergyPotion();

        }
        return 1000;
    }


}
