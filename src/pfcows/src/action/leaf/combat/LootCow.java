package pfcows.src.action.leaf.combat;

import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import pfcows.src.data.CowsConfig;

import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class LootCow extends Leaf {

    CowsConfig cowsConfig = CowsConfig.getInstance();

    @Override
    public boolean isValid() {
        GroundItem cowhide = GroundItems.closest(item -> item.getName().equals("Cowhide") && item.distance() <= 5);

        return cowhide != null && cowhide.isOnScreen() && !Players.getLocal().isMoving();
    }

    @Override
    public int onLoop() {
        // Loot cowhides
        GroundItem cowhide = GroundItems.closest(item -> item.getName().equals("Cowhide") && item.distance() <= 5);
        if (cowhide != null && cowhide.interact("Take")) {
            cowsConfig.setStatus("Taking " + cowhide);
            sleepUntil(() -> !cowhide.exists(), 5000);
        }
        return 30;
    }
}
