package pfdyemaker.src.activity.makeyellowdye;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.widgets.message.Message;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class BankYellowDyeLeaf extends Leaf {

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
}
