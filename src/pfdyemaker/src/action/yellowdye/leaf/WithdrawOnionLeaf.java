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
        return Bank.isOpen() && Bank.contains(config.ONION) && !Inventory.contains(config.ONION);
    }

    @Override
    public int onLoop() {
        if (Bank.isOpen()) {
            config.setStatus("Withdrawing " + config.ONION);
            if (Bank.withdrawAll(config.ONION)) {
                Logger.log("(yellowdye) withdrew onion");
                Sleep.sleepUntil(() -> Inventory.contains(config.ONION), 6000, 600);
            }
        }
        return 1000;
    }
}
