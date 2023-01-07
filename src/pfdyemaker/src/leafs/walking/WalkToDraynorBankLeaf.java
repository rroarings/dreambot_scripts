package pfdyemaker.src.leafs.walking;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

//todo update banking handling
public class WalkToDraynorBankLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.status = "Walking to bank";
            Walking.walk(BankLocation.DRAYNOR.getArea(1).getCenter());
            Sleep.sleepUntil(() -> BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal()), 600);
        }
        return 600;
    }
}
