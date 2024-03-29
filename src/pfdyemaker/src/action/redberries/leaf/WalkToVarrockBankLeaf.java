package pfdyemaker.src.action.redberries.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.action.util.QuickMethods;
import pfdyemaker.src.data.DyeMakerConfig;

public class WalkToVarrockBankLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Redberries") && Inventory.isFull() && !BankLocation.VARROCK_EAST.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.setStatus("Walking to bank");
            Walking.walk(BankLocation.VARROCK_EAST.getArea(1).getCenter());
            QuickMethods.drinkEnergyPotion();
        }
        return 600;
    }
}
