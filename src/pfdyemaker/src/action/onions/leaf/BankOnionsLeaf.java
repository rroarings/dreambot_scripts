package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.combat.Combat;
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
    private final BankLocation draynor = BankLocation.DRAYNOR;
    private final BankLocation lumbridge = BankLocation.LUMBRIDGE;


    @Override
    public boolean isValid() {
        BankLocation bankToUse = null;
        if (Combat.getCombatLevel() <= 15) {
            bankToUse = lumbridge;
        } else {
            bankToUse = draynor;
        }
        // Check if the player is near the bank location
        boolean isNearBank = draynor.getArea(3).contains(Players.getLocal());

        // Check if the bank is open or the player is near the bank
        return Bank.isOpen() || isNearBank;
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
