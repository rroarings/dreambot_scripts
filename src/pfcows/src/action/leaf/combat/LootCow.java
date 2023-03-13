package pfcows.src.action.leaf.combat;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
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
        GroundItem phatLoot = GroundItems.closest(groundItem -> groundItem.getName().contains("Cowhide") && groundItem.isOnScreen() && groundItem.distance() <= 3);
        if (phatLoot != null && !Inventory.isFull()) {
            if (phatLoot.interact("Take")) {
                cowsConfig.setStatus("Taking " + phatLoot);
                Logger.log("loot interaction - take item");
                Sleep.sleepUntil(() -> !phatLoot.exists(), 4000);
                Logger.log("loot interaction - item picked up or stolen");
            }
            return 800;
        } else Logger.log("loot interaction fail - item gone or inventory full");


        return 300;
    }
}
