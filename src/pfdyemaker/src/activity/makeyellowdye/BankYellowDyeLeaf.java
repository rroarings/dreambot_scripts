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
                Sleep.sleepUntil(Bank::isOpen, 2000);
        }

        if (Bank.isOpen()) {
            config.setStatus("Depositing dye");
            Bank.depositAllExcept("Coins", config.getDyeIngredient());
            Sleep.sleepUntil(() -> !Inventory.contains(" dye"), 2000);

        }

        if (Bank.isOpen()) {
            config.setStatus("Withdrawing " + config.getDyeIngredient());
            if (Bank.contains(config.getDyeIngredient())) {
                Bank.withdrawAll(config.getDyeIngredient());
                Sleep.sleepUntil(() -> Inventory.contains(config.getDyeIngredient()), 2000);
            } else if (!Bank.contains(config.getDyeIngredient())) {
                Logger.log("manager -> out of " + config.getDyeIngredient() + " - stopping script");
                ScriptManager.getScriptManager().stop();

            }
        }



        return 1000;
    }
}
