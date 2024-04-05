package pfdyemaker.src.action.yellowdye.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankYellowDyeLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.dyeConfig();

    @Override
    public boolean isValid() {
        return BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            config.setStatus("Opening bank");
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 2000, 800);
        }

        if (Bank.isOpen()) {
            config.setStatus("Depositing dye");
            Bank.depositAllExcept(item -> item.getName().equals("Coins") && item.isValid());
            Sleep.sleepUntil(() -> Inventory.onlyContains(item -> item.getName().equals("Coins") && item.isValid()), 2000, 800);
        }

        if (Bank.isOpen() && Bank.contains(config.getDyeIngredient()) && !Inventory.contains(config.getDyeIngredient())) {
            if (Bank.contains(config.getDyeIngredient()) && Bank.count(config.getDyeIngredient()) >= 2) {
                config.setStatus("Withdrawing " + config.getDyeIngredient());
                Bank.withdrawAll(config.getDyeIngredient());
                Sleep.sleepUntil(() -> Inventory.contains(config.getDyeIngredient()), 4000, 800);
            } else if (!Bank.contains(config.getDyeIngredient()) || Bank.count(config.getDyeIngredient()) <= 1) {
                Logger.log("script manager -> out of " + config.getDyeIngredient() + ". - stopping script");
                ScriptManager.getScriptManager().stop();
            }
        }

        if (!Inventory.contains(config.getDyeIngredient())) {
            config.setStatus("Logging out");
            Logger.log("script manager -> stopping script");
            Logger.log("stop reason -> Out of dye ingredient: " + config.getDyeIngredient());
            ScriptManager.getScriptManager().stop();
        }
        return 1000;
    }

    private int calculateMinGoldToWithdraw() {
        // Get the amount of woad leaves in the inventory
        int onionCount = Inventory.count(item -> item.getName().equals(DyeMakerConfig.dyeConfig().getDyeIngredient()));

        // Define the cost of making a blue dye (2 woad leaves and 5 coins)
        int onionsPerDye = 2;
        int coinsPerDye = 5;

        // Calculate the number of dyes that can be made with the available woad leaves
        int dyesCanBeMade = onionCount / onionsPerDye;

        // Calculate the total gold required to make the dyes

        // Return the total gold required
        return dyesCanBeMade * coinsPerDye;
    }

}
