package pfcows.src.action.branch.traversal;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import pfcows.src.data.Location;

public class WalkFaladorCows extends Branch {

    @Override
    public boolean isValid() {
        if (!Location.FALADOR_COWS.getArea().contains(Players.getLocal())) {
            return true;
        }
        return false;
    }
}
