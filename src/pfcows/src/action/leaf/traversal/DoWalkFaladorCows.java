package pfcows.src.action.leaf.traversal;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfcows.src.data.CowsConfig;
import pfcows.src.data.Location;

public class DoWalkFaladorCows extends Leaf {

    @Override
    public boolean isValid() {
        if (!Location.FALADOR_COWS.getArea().contains(Players.getLocal())) {
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        if (!Location.FALADOR_COWS.getArea().contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                CowsConfig.getCowsConfig().setStatus("Walking to Falador cows");
                Walking.walk(Location.FALADOR_COWS.getArea().getRandomTile());
                Sleep.sleepUntil(() -> Location.FALADOR_COWS.getArea().contains(Players.getLocal()), 4000, 200);
            }
        }
        return 600;
    }
}
