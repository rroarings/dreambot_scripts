package pfdyemaker.src.action.yellowdye.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.ui.Frame;
import pfdyemaker.src.util.PotionHandler;

public class WithdrawOnionLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Bank.isOpen() && Bank.contains(config.getDyeIngredient()) && !Inventory.contains(config.getDyeIngredient());
    }

    @Override
    public int onLoop() {
        if (Frame.isEnergyPotions()) {
            PotionHandler.handleEnergyPotion();
        }

        config.setStatus("Withdrawing " + config.getDyeIngredient());
        Bank.withdrawAll(config.getDyeIngredient());
        Logger.log("(yellowdye) withdrew onion");
        // Wait until inventory contains the dye ingredient or timeout after 8 seconds
        Sleep.sleepUntil(() -> Inventory.contains(config.getDyeIngredient()), 6000, 600);
        return 1000; // Return sleep time until next loop
    }
}
