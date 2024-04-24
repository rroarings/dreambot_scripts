package pfdyemaker.src.action.navigation;

import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

import org.dreambot.api.script.frameworks.treebranch.Leaf;

public class WalkToDraynorBankLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            if (Walking.walk(BankLocation.DRAYNOR.getCenter().getRandomized())) {
                DyeMakerConfig.dyeConfig().setStatus("Walking to Draynor bank");
                Sleep.sleepUntil(() -> BankLocation.DRAYNOR.getArea(3).contains(Players.getLocal()), 5000, 600);
            }
        }
        return 600;
    }
}
