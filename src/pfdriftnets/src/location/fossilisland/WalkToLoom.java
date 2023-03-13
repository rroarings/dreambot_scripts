package pfdriftnets.src.location.fossilisland;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import pfdriftnets.src.data.DriftNetsConfig;
import pfdriftnets.src.framework.Leaf;

public class WalkToLoom extends Leaf {

    DriftNetsConfig config = DriftNetsConfig.getDriftNetsConfig();
    private final Tile loomTile = new Tile(3730, 3821, 0);

    @Override
    public boolean isValid() {
        return Inventory.contains("Jute fibre") && Inventory.count("Jute fibre") >= 2 && !config.getFOSSIL_LOOM().contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (Walking.shouldWalk()) {
            DriftNetsConfig.setStatus("Walking to loom");
            Walking.walk(loomTile);
            Sleep.sleepUntil(() -> loomTile.distance(Players.getLocal()) > 15, 2000, 600);
        }
        return 1000;
    }
}
