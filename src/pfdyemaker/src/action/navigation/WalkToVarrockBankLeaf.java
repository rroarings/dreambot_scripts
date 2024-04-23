package pfdyemaker.src.action.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class WalkToVarrockBankLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Redberries") && Inventory.isFull() && !BankLocation.VARROCK_EAST.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            if (Walking.walk(BankLocation.VARROCK_EAST.getCenter().getRandomized())) {
                DyeMakerConfig.dyeConfig().setStatus("Walking to Varrock Easr bank");
                Sleep.sleepUntil(() -> BankLocation.VARROCK_EAST.getArea(3).contains(Players.getLocal()), 5000, 600);
            }
        }
        return 600;
    }
}
