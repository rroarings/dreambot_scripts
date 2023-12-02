package pfcows.src.action.leaf.traversal;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfcows.src.data.Location;

public class DoWalkFaladorBank extends Leaf {

    @Override
    public boolean isValid() {
        if (!BankLocation.FALADOR_EAST.getArea(1).contains(Players.getLocal())) {
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        if (!BankLocation.FALADOR_EAST.getArea(1).contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                Walking.walk(Location.FALADOR_COWS.getArea().getRandomTile());
                Sleep.sleepUntil(() -> Location.FALADOR_COWS.getArea().contains(Players.getLocal()), 4000, 200);
            }
        }
        return 600;
    }
}
