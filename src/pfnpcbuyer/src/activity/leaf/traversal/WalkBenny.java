package pfnpcbuyer.src.activity.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfnpcbuyer.src.data.NPCBuyerConfig;

public class WalkBenny extends Leaf {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    @Override
    public boolean isValid() {
        final Area bennyArea = new Area(3222, 3434, 3218, 3429);
        return Inventory.contains("Coins") && Inventory.count("Coins") > 28 && !Inventory.isFull() && !bennyArea.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        final Area bennyArea = new Area(3222, 3434, 3218, 3429);
        if (Walking.shouldWalk()) {
            if (Walking.walk(bennyArea.getCenter())) {
                nbc.setStatus("Walking to Benny");
                Sleep.sleepUntil(() -> bennyArea.contains(Players.getLocal()), 4000, 50);
            }
        }
        return 600;
    }
}
