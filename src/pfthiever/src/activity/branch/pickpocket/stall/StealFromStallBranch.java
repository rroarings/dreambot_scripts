package pfthiever.src.activity.branch.pickpocket.stall;

import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfthiever.src.data.Thieving;

public class StealFromStallBranch extends Branch {

    private Tile bStallTile = new Tile(2667, 3310, 0);

    @Override
    public boolean isValid() {
        GameObject b_stall = GameObjects.closest(obj -> obj.getName().equals(Thieving.FRUIT_STALL.getName()));

        if (b_stall != null) {
            return true;
        }
        return false;
    }
}
