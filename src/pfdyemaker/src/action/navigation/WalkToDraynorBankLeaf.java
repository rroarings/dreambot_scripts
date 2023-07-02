package pfdyemaker.src.action.navigation;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

import org.dreambot.api.script.frameworks.treebranch.Leaf;

public class WalkToDraynorBankLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();
    private final Tile bankTile = new Tile(3092, 3245, 0);

    @Override
    public boolean isValid() {
        return !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            config.setStatus("Walking to bank");
            Walking.walk(BankLocation.DRAYNOR.getTile());
            Sleep.sleepUntil(() -> bankTile.distance() <= 5, 600);
        }
        return 600;
    }
}
